package decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;

/**
 * Tool to list and display decoded RU (Recording Unit) file data
 * This tool provides methods to decode and display RU file contents in a readable format.
 * 
 * RU檔案解碼列表工具
 * 此工具提供方法來解碼和顯示RU檔案內容的可讀格式
 * 
 * Usage:
 *   RUFileDecodeListTool tool = new RUFileDecodeListTool();
 *   Vector<String> results = tool.listDecodedData(new File("path/to/ru/file"));
 *   for (String line : results) {
 *       System.out.println(line);
 *   }
 */
public class RUFileDecodeListTool {
    
    public RUFileDecodeListTool() {
    }
    
    /**
     * Decode and list RU file contents
     * 解碼並列出RU檔案內容
     * 
     * @param ruFile The RU file to decode
     * @return List of decoded packet information
     */
    public Vector<String> listDecodedData(File ruFile) {
        Vector<String> results = new Vector<String>();
        
        if (!ruFile.exists() || !ruFile.isFile()) {
            results.add("錯誤：檔案不存在或不是有效的檔案");
            return results;
        }
        
        results.add("========================================");
        results.add("RU檔案解碼內容");
        results.add("檔案名稱：" + ruFile.getName());
        results.add("檔案路徑：" + ruFile.getAbsolutePath());
        results.add("檔案大小：" + ruFile.length() + " bytes");
        results.add("解碼時間：" + new Date());
        results.add("========================================");
        
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ruFile);
            int packetCount = 0;
            
            // Read file header if present
            results.add("\n【檔案標頭資訊】");
            byte[] headerBuffer = new byte[16];
            int headerRead = fis.read(headerBuffer);
            if (headerRead > 0) {
                results.add("檔案標頭：" + toHexString(headerBuffer));
            }
            
            // Process packets
            while (fis.available() > 16) {
                // Read packet header (16 bytes: timestamp, location, speed, etc.)
                byte[] packetHeader = new byte[16];
                int read = fis.read(packetHeader);
                
                if (read < 16) {
                    break;
                }
                
                // Get packet number (first byte)
                int packetNo = getUnsignedByte(packetHeader[0]);
                
                // Get data length (byte 15)
                int dataLength = getUnsignedByte(packetHeader[15]);
                
                // Read packet body
                byte[] packetBody = new byte[dataLength];
                if (dataLength > 0) {
                    int bodyRead = fis.read(packetBody);
                    if (bodyRead < dataLength) {
                        results.add("警告：封包 #" + (packetCount + 1) + " 資料不完整");
                        break;
                    }
                }
                
                packetCount++;
                
                // Display packet information
                results.add("\n=== 封包 #" + packetCount + " ===");
                results.add("封包編號：" + packetNo + " (" + getPacketTypeName(packetNo) + ")");
                
                // Decode timestamp (bytes 1-6: YY MM DD HH MM SS)
                if (packetHeader.length >= 7) {
                    int year = 2000 + getUnsignedByte(packetHeader[1]);
                    int month = getUnsignedByte(packetHeader[2]);
                    int day = getUnsignedByte(packetHeader[3]);
                    int hour = getUnsignedByte(packetHeader[4]);
                    int minute = getUnsignedByte(packetHeader[5]);
                    int second = getUnsignedByte(packetHeader[6]);
                    results.add("時間戳記：" + year + "/" + String.format("%02d", month) + "/" + 
                               String.format("%02d", day) + " " + String.format("%02d", hour) + ":" + 
                               String.format("%02d", minute) + ":" + String.format("%02d", second));
                }
                
                // Decode location (bytes 7-10, 4 bytes)
                if (packetHeader.length >= 11) {
                    int location = bytesToInt(packetHeader, 7, 4);
                    if (location >= 1000000000) {
                        location -= 1000000000;
                    }
                    results.add("位置：" + location + " cm (" + (location / 100.0) + " m)");
                }
                
                // Decode speed (bytes 13-14, 2 bytes)
                if (packetHeader.length >= 15) {
                    int speed = bytesToInt(packetHeader, 13, 2);
                    results.add("速度：" + speed + " cm/s (" + (speed / 27.778) + " km/h)");
                }
                
                results.add("資料長度：" + dataLength + " bytes");
                
                // Display packet body data in hex (first 32 bytes)
                if (dataLength > 0) {
                    int displayLen = Math.min(dataLength, 32);
                    byte[] displayData = new byte[displayLen];
                    System.arraycopy(packetBody, 0, displayData, 0, displayLen);
                    results.add("封包內容（前" + displayLen + "位元組）：" + toHexString(displayData));
                    if (dataLength > 32) {
                        results.add("  ... (" + (dataLength - 32) + " 位元組未顯示)");
                    }
                }
                
                // Special handling for specific packet types
                switch (packetNo) {
                    case 1:
                    case 4:
                        results.add("→ ATP/MMI 通訊封包");
                        break;
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                        results.add("→ BTM 電報封包（Balise資訊）");
                        break;
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                        results.add("→ VDX 資料封包");
                        break;
                    case 201:
                        results.add("→ ATP 下載資料（距離、速度、時間）");
                        break;
                    case 211:
                        results.add("→ 週期性速度距離資料");
                        break;
                }
            }
            
            results.add("\n========================================");
            results.add("解碼完成");
            results.add("總共封包數：" + packetCount);
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
     * Get packet type name in Chinese based on packet number
     * 根據封包編號取得封包類型名稱（中文）
     * 
     * @param packetNo Packet number
     * @return Packet type name
     */
    private String getPacketTypeName(int packetNo) {
        switch (packetNo) {
            case 1: return "ATP_MMI_CH1";
            case 2: return "STATUS_ATP";
            case 3: return "STATUS_MMI";
            case 4: return "ATP_MMI_CH2";
            case 21: return "VDX_IN_STATUS_1";
            case 22: return "VDX_OUT_1";
            case 23: return "VDX_OUT_2";
            case 24: return "VDX_OUT_3";
            case 31: return "DX_IN_STATUS_1";
            case 32: return "DX_STATUS_1";
            case 33: return "DX_OUT_STATUS_1";
            case 41: return "BTM_COMMAND_1";
            case 42: return "BTM_STATUS_1";
            case 43: return "BTM_TGM_1";
            case 44: return "BTM_TGM_2";
            case 45: return "BTM_TGM_3";
            case 46: return "BTM_TGM_4";
            case 47: return "BTM_TGM_5";
            case 51: return "SDU1";
            case 52: return "SDU2";
            case 61: return "ODO_CONFIG_1";
            case 62: return "ODO_MESSAGE_1";
            case 63: return "ODO_MESSAGE_2";
            case 64: return "ODO_BTM_STATUS_1";
            case 71: return "PM_LOG_TGM";
            case 72: return "PM_APP_LOG_TGM";
            case 81: return "MMI_STATUS_REPORT_CHANGED";
            case 91: return "PRS_INFO";
            case 201: return "ATP_DOWN_DIS_SPEED_TIME";
            case 211: return "PERIODIC_SPEED_DISTANCE";
            case 216: return "BUTTON_EVENT";
            case 221: return "STATUS_COUNTER_BOARD";
            case 222: return "STATUS_USB";
            case 223: return "STATUS_PRS";
            case 224: return "STATUS_SPEEDMETER";
            case 225: return "STATUS_DATA_DOWNLOAD";
            case 227: return "STATUS_MVB";
            case 228: return "STATUS_GPP";
            default: return "未知封包類型";
        }
    }
    
    /**
     * Print decoded data to console
     * 列印解碼後的資料到控制台
     * 
     * @param ruFile The RU file to decode
     */
    public void printDecodedData(File ruFile) {
        Vector<String> results = listDecodedData(ruFile);
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
                sb.append("\n                  ");
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
        System.out.println("RU檔案解碼列表工具");
        System.out.println("=================");
        System.out.println();
        
        if (args.length < 1) {
            System.out.println("使用方式：java RUFileDecodeListTool <RU檔案路徑>");
            System.out.println("範例：java RUFileDecodeListTool /path/to/ru/file");
            System.out.println();
            System.out.println("說明：");
            System.out.println("  此工具可解碼並顯示RU（Recording Unit）檔案的內容");
            System.out.println("  包括：");
            System.out.println("  - 封包編號和類型");
            System.out.println("  - 時間戳記");
            System.out.println("  - 位置和速度資訊");
            System.out.println("  - 原始封包資料（十六進位）");
            return;
        }
        
        File ruFile = new File(args[0]);
        RUFileDecodeListTool tool = new RUFileDecodeListTool();
        tool.printDecodedData(ruFile);
    }
}
