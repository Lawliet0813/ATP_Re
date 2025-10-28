package decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;

/**
 * Tool to list and display decoded MMI (Man-Machine Interface) file data
 * This tool provides methods to decode and display MMI file contents in a readable format.
 * 
 * MMI檔案解碼列表工具
 * 此工具提供方法來解碼和顯示MMI檔案內容的可讀格式
 * 
 * Usage:
 *   MMIFileDecodeListTool tool = new MMIFileDecodeListTool();
 *   Vector<String> results = tool.listDecodedData(new File("path/to/mmi/file"));
 *   for (String line : results) {
 *       System.out.println(line);
 *   }
 */
public class MMIFileDecodeListTool {
    
    public MMIFileDecodeListTool() {
    }
    
    /**
     * Decode and list MMI file contents
     * 解碼並列出MMI檔案內容
     * 
     * @param mmiFile The MMI file to decode
     * @return List of decoded packet information
     */
    public Vector<String> listDecodedData(File mmiFile) {
        Vector<String> results = new Vector<String>();
        
        if (!mmiFile.exists() || !mmiFile.isFile()) {
            results.add("錯誤：檔案不存在或不是有效的檔案");
            return results;
        }
        
        results.add("========================================");
        results.add("MMI檔案解碼內容");
        results.add("檔案名稱：" + mmiFile.getName());
        results.add("檔案路徑：" + mmiFile.getAbsolutePath());
        results.add("檔案大小：" + mmiFile.length() + " bytes");
        results.add("解碼時間：" + new Date());
        results.add("========================================");
        
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mmiFile);
            int packetCount = 0;
            int[] packetTypeCounts = new int[256]; // Count each packet type
            
            while (fis.available() > 0) {
                // Read packet number first (1 byte)
                int packetNo = fis.read();
                if (packetNo < 0) {
                    break;
                }
                
                packetTypeCounts[packetNo]++;
                
                // Estimate packet size based on packet number
                // MMI packets vary in size, typical ranges: 20-100 bytes
                int estimatedSize = getEstimatedPacketSize(packetNo);
                
                // Read packet data
                byte[] packetData = new byte[estimatedSize];
                packetData[0] = (byte) packetNo;
                
                int read = fis.read(packetData, 1, estimatedSize - 1);
                if (read < 0) {
                    break;
                }
                
                // Adjust packet size based on actual read
                if (read < estimatedSize - 1) {
                    byte[] adjustedData = new byte[read + 1];
                    System.arraycopy(packetData, 0, adjustedData, 0, read + 1);
                    packetData = adjustedData;
                }
                
                packetCount++;
                
                // Display packet information
                results.add("\n=== 封包 #" + packetCount + " ===");
                results.add("封包編號：" + packetNo + " (0x" + String.format("%02X", packetNo) + ")");
                results.add("封包類型：" + getPacketTypeName(packetNo));
                results.add("資料長度：" + packetData.length + " bytes");
                
                // Decode based on packet type
                decodePacketContent(packetNo, packetData, results);
                
                // Display hex dump of packet data (first 48 bytes)
                int displayLen = Math.min(packetData.length, 48);
                byte[] displayData = new byte[displayLen];
                System.arraycopy(packetData, 0, displayData, 0, displayLen);
                results.add("封包內容（前" + displayLen + "位元組）：");
                results.add("  " + toHexString(displayData));
                if (packetData.length > 48) {
                    results.add("  ... (" + (packetData.length - 48) + " 位元組未顯示)");
                }
            }
            
            results.add("\n========================================");
            results.add("解碼完成");
            results.add("總共封包數：" + packetCount);
            results.add("\n封包類型統計：");
            for (int i = 0; i < packetTypeCounts.length; i++) {
                if (packetTypeCounts[i] > 0) {
                    results.add(String.format("  [%3d] %s: %d 次", 
                        i, getPacketTypeName(i), packetTypeCounts[i]));
                }
            }
            results.add("========================================");
            
        } catch (IOException e) {
            results.add("讀取檔案錯誤：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // Ignore close errors
                }
            }
        }
        
        return results;
    }
    
    /**
     * Decode packet content based on packet type
     * 根據封包類型解碼封包內容
     * 
     * @param packetNo Packet number
     * @param packetData Packet data bytes
     * @param results Results list to append to
     */
    private void decodePacketContent(int packetNo, byte[] packetData, Vector<String> results) {
        try {
            switch (packetNo) {
                case 0: // MMI_START_ATP
                    results.add("→ ATP啟動請求");
                    break;
                    
                case 1: // MMI_DYNAMIC
                    results.add("→ 動態資料（速度、加速度、位置、煞車目標）");
                    if (packetData.length >= 15) {
                        int speed = bytesToInt(packetData, 3, 2);
                        results.add("  速度：" + speed + " cm/s (" + String.format("%.1f", speed / 27.778) + " km/h)");
                        int accel = bytesToInt(packetData, 5, 2);
                        results.add("  加速度：" + accel + " cm/s²");
                        int location = bytesToInt(packetData, 7, 4);
                        if (location >= 1000000000) location -= 1000000000;
                        results.add("  位置：" + location + " cm (" + (location / 100.0) + " m)");
                    }
                    break;
                    
                case 2: // MMI_STATUS
                    results.add("→ 狀態資料");
                    break;
                    
                case 4: // MMI_TRACK_DESCRIPTION
                    results.add("→ 軌道描述資訊");
                    break;
                    
                case 6: // MMI_CURRENT_TRAIN_DATA
                    results.add("→ 目前列車資料");
                    break;
                    
                case 8: // MMI_DRIVER_MESSAGE
                    results.add("→ 司機員訊息");
                    if (packetData.length >= 4) {
                        int messageId = getUnsignedByte(packetData[3]);
                        results.add("  訊息ID：" + messageId);
                    }
                    break;
                    
                case 9: // MMI_FAILURE_REPORT_ATP
                    results.add("→ ATP故障報告");
                    break;
                    
                case 100: // MMI_START_MMI
                    results.add("→ MMI啟動請求");
                    break;
                    
                case 101: // MMI_DRIVER_REQUEST
                    results.add("→ 司機員請求");
                    break;
                    
                case 102: // MMI_STATUS_REPORT
                    results.add("→ 狀態報告");
                    break;
                    
                case 104: // MMI_NEW_DRIVER_DATA
                    results.add("→ 新司機員資料");
                    break;
                    
                case 107: // MMI_NEW_TRAIN_DATA
                    results.add("→ 新列車資料");
                    break;
                    
                case 113: // MMI_FAILURE_REPORT_MMI
                    results.add("→ MMI故障報告");
                    break;
                    
                default:
                    results.add("→ 標準MMI封包");
                    break;
            }
        } catch (Exception e) {
            results.add("  解碼內容時發生錯誤：" + e.getMessage());
        }
    }
    
    /**
     * Get estimated packet size based on packet number
     * 根據封包編號取得估計的封包大小
     * 
     * @param packetNo Packet number
     * @return Estimated size in bytes
     */
    private int getEstimatedPacketSize(int packetNo) {
        // These are estimated sizes - actual sizes may vary
        switch (packetNo) {
            case 0: return 20;  // MMI_START_ATP
            case 1: return 64;  // MMI_DYNAMIC
            case 2: return 32;  // MMI_STATUS
            case 3: return 24;  // MMI_SET_TIME_ATP
            case 4: return 128; // MMI_TRACK_DESCRIPTION
            case 6: return 48;  // MMI_CURRENT_TRAIN_DATA
            case 8: return 32;  // MMI_DRIVER_MESSAGE
            case 9: return 40;  // MMI_FAILURE_REPORT_ATP
            case 100: return 20; // MMI_START_MMI
            case 101: return 24; // MMI_DRIVER_REQUEST
            case 102: return 32; // MMI_STATUS_REPORT
            case 104: return 40; // MMI_NEW_DRIVER_DATA
            case 107: return 48; // MMI_NEW_TRAIN_DATA
            case 113: return 40; // MMI_FAILURE_REPORT_MMI
            default: return 64;  // Default size
        }
    }
    
    /**
     * Get packet type name from packet number
     * @param packetNo The packet number
     * @return Packet type name in Chinese
     */
    private String getPacketTypeName(int packetNo) {
        switch (packetNo) {
            case 0: return "MMI_START_ATP（啟動ATP）";
            case 1: return "MMI_DYNAMIC（動態資料）";
            case 2: return "MMI_STATUS（狀態）";
            case 3: return "MMI_SET_TIME_ATP（設定時間）";
            case 4: return "MMI_TRACK_DESCRIPTION（軌道描述）";
            case 5: return "MMI_GEO_POSITION（地理位置）";
            case 6: return "MMI_CURRENT_TRAIN_DATA（目前列車資料）";
            case 7: return "MMI_FORCED_DRIVER_REQUEST（強制司機員請求）";
            case 8: return "MMI_DRIVER_MESSAGE（司機員訊息）";
            case 9: return "MMI_FAILURE_REPORT_ATP（ATP故障報告）";
            case 10: return "MMI_ECHOED_TRAIN_DATA（回應列車資料）";
            case 11: return "MMI_CURRENT_SR_RULES（目前SR規則）";
            case 12: return "MMI_ECHOED_SR_RULES（回應SR規則）";
            case 14: return "MMI_CURRENT_DRIVER_DATA（目前司機員資料）";
            case 15: return "MMI_TEST_REQUEST（測試請求）";
            case 16: return "MMI_SELECT_STM_REQUEST（選擇STM請求）";
            case 17: return "MMI_STM_DATA_TO_CAB_1（STM資料至駕駛室1）";
            case 18: return "MMI_STM_DATA_TO_CAB_2（STM資料至駕駛室2）";
            case 19: return "MMI_RU_DATA（RU資料）";
            case 100: return "MMI_START_MMI（啟動MMI）";
            case 101: return "MMI_DRIVER_REQUEST（司機員請求）";
            case 102: return "MMI_STATUS_REPORT（狀態報告）";
            case 103: return "MMI_CONFIRMED_SR_RULES（確認SR規則）";
            case 104: return "MMI_NEW_DRIVER_DATA（新司機員資料）";
            case 106: return "MMI_NEW_SR_RULES（新SR規則）";
            case 107: return "MMI_NEW_TRAIN_DATA（新列車資料）";
            case 109: return "MMI_SET_TIME_MMI（設定時間MMI）";
            case 110: return "MMI_CONFIRMED_TRAIN_DATA（確認列車資料）";
            case 111: return "MMI_DRIVER_MESSAGE_ACK（司機員訊息確認）";
            case 112: return "MMI_NEW_RBC_DATA（新RBC資料）";
            case 113: return "MMI_FAILURE_REPORT_MMI（MMI故障報告）";
            case 114: return "MMI_TEST_RESULT（測試結果）";
            case 115: return "MMI_FAILURE_REPORT_ACK（故障報告確認）";
            case 116: return "MMI_SELECT_STM（選擇STM）";
            case 117: return "MMI_STM_DATA_FROM_CAB_1（來自駕駛室1的STM資料）";
            case 118: return "MMI_STM_DATA_FROM_CAB_2（來自駕駛室2的STM資料）";
            case 119: return "MMI_RU_DATA_FROM_CAB_1（來自駕駛室1的RU資料）";
            case 120: return "MMI_RU_DATA_FROM_CAB_2（來自駕駛室2的RU資料）";
            default: return "未知封包類型（" + packetNo + "）";
        }
    }
    
    /**
     * Decode and list all MMI files in a directory
     * 解碼並列出目錄中所有MMI檔案
     * 
     * @param directory The directory containing MMI files
     * @return List of decoded information from all files
     */
    public Vector<String> listDecodedDataFromDirectory(File directory) {
        Vector<String> results = new Vector<String>();
        
        if (!directory.exists() || !directory.isDirectory()) {
            results.add("錯誤：目錄不存在或不是有效的目錄");
            return results;
        }
        
        // Find all MMI files in directory
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            results.add("警告：目錄中沒有檔案");
            return results;
        }
        
        // Filter for MMI files (you can adjust the filter logic based on naming convention)
        Vector<File> mmiFiles = new Vector<File>();
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName().toLowerCase();
                // Accept files that might be MMI files
                // Adjust the filter as needed based on your file naming conventions
                if (name.contains("mmi") || name.endsWith(".mmi") || 
                    name.endsWith(".dat") || name.endsWith(".bin")) {
                    mmiFiles.add(file);
                }
            }
        }
        
        results.add("========================================");
        results.add("目錄中的MMI檔案解碼");
        results.add("目錄路徑：" + directory.getAbsolutePath());
        results.add("找到檔案總數：" + files.length);
        results.add("MMI檔案數：" + mmiFiles.size());
        results.add("解碼時間：" + new Date());
        results.add("========================================\n");
        
        if (mmiFiles.size() < 2) {
            results.add("警告：目錄中少於2個MMI檔案（要求：測試區選一個資料夾，內含2個檔案以上）");
            results.add("      目前只找到 " + mmiFiles.size() + " 個檔案");
        } else {
            results.add("✓ 符合要求：找到 " + mmiFiles.size() + " 個MMI檔案（>=2個檔案）");
        }
        
        // Decode each MMI file
        for (int i = 0; i < mmiFiles.size(); i++) {
            File mmiFile = mmiFiles.get(i);
            results.add("\n");
            results.add("╔════════════════════════════════════════");
            results.add("║ MMI檔案 #" + (i + 1) + " / " + mmiFiles.size());
            results.add("╚════════════════════════════════════════");
            
            Vector<String> fileResults = listDecodedData(mmiFile);
            results.addAll(fileResults);
        }
        
        results.add("\n");
        results.add("╔════════════════════════════════════════");
        results.add("║ 目錄解碼總結");
        results.add("╚════════════════════════════════════════");
        results.add("總共處理MMI檔案數：" + mmiFiles.size());
        results.add("完成時間：" + new Date());
        
        return results;
    }
    
    /**
     * Print decoded data to console
     * 列印解碼後的資料到控制台
     * 
     * @param mmiFile The MMI file to decode
     */
    public void printDecodedData(File mmiFile) {
        Vector<String> results = listDecodedData(mmiFile);
        for (String line : results) {
            System.out.println(line);
        }
    }
    
    /**
     * Print decoded data from directory to console
     * 列印目錄中解碼後的資料到控制台
     * 
     * @param directory The directory containing MMI files
     */
    public void printDecodedDataFromDirectory(File directory) {
        Vector<String> results = listDecodedDataFromDirectory(directory);
        for (String line : results) {
            System.out.println(line);
        }
    }
    
    /**
     * Helper method to convert unsigned byte to int
     * 輔助方法：將無符號位元組轉換為整數
     * 
     * @param b The byte to convert
     * @return The unsigned int value
     */
    private static int getUnsignedByte(byte b) {
        return b & 0xFF;
    }
    
    /**
     * Helper method to convert multiple bytes to int
     * 輔助方法：將多個位元組轉換為整數
     * 
     * @param bytes The byte array
     * @param offset The starting offset
     * @param length The number of bytes to convert (1-4)
     * @return The integer value
     */
    private static int bytesToInt(byte[] bytes, int offset, int length) {
        int result = 0;
        for (int i = 0; i < length && (offset + i) < bytes.length; i++) {
            result = (result << 8) | (bytes[offset + i] & 0xFF);
        }
        return result;
    }
    
    /**
     * Helper method to convert byte array to hex string
     * 輔助方法：將位元組陣列轉換為十六進位字串
     * 
     * @param bytes The byte array to convert
     * @return The hex string representation
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0 && i % 16 == 0) {
                sb.append("\n    ");
            }
            sb.append(String.format("%02X ", bytes[i] & 0xFF));
        }
        return sb.toString().trim();
    }
    
    /**
     * Main method for testing
     * 主要方法用於測試
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("MMI檔案解碼列表工具");
        System.out.println("==================");
        System.out.println();
        
        if (args.length < 1) {
            System.out.println("使用方式：java MMIFileDecodeListTool <MMI檔案路徑或目錄>");
            System.out.println("範例1（單一檔案）：java MMIFileDecodeListTool /path/to/mmi/file");
            System.out.println("範例2（目錄）：java MMIFileDecodeListTool /path/to/mmi/directory");
            System.out.println();
            System.out.println("說明：");
            System.out.println("  此工具可解碼並顯示MMI（Man-Machine Interface）檔案的內容");
            System.out.println("  包括：");
            System.out.println("  - 封包編號和類型");
            System.out.println("  - MMI訊息內容");
            System.out.println("  - 速度、位置等動態資料");
            System.out.println("  - 原始封包資料（十六進位）");
            System.out.println("  - 封包類型統計");
            System.out.println();
            System.out.println("  目錄模式：");
            System.out.println("  - 可處理目錄中的多個MMI檔案");
            System.out.println("  - 符合issue要求：測試區選一個資料夾，內含2個檔案以上");
            return;
        }
        
        File input = new File(args[0]);
        MMIFileDecodeListTool tool = new MMIFileDecodeListTool();
        
        if (input.isDirectory()) {
            System.out.println("偵測到目錄模式");
            System.out.println();
            tool.printDecodedDataFromDirectory(input);
        } else {
            System.out.println("偵測到單一檔案模式");
            System.out.println();
            tool.printDecodedData(input);
        }
    }
}
