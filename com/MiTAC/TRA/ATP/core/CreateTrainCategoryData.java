// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.io.FileWriter;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import java.io.Reader;
import java.sql.SQLException;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileReader;
import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.BufferedReader;
import java.util.Vector;

public class CreateTrainCategoryData
{
    private Vector _$15875;
    private Vector _$15876;
    private Vector _$15877;
    private Vector _$15878;
    private Vector _$15879;
    private BufferedReader _$15894;
    private ConnectDB _$1846;
    private CRC _$15893;
    private FileReader _$2483;
    private StringBuffer _$15890;
    private StringBuffer _$15888;
    private String _$15892;
    private String _$15891;
    private File _$15881;
    private File _$15883;
    private File _$15882;
    private File _$15880;
    private StringBuffer _$15886;
    private StringBuffer _$15887;
    private StringBuffer _$15889;
    private StringBuffer _$15884;
    private StringBuffer _$15885;
    private int _$1626;
    
    public CreateTrainCategoryData() {
        this._$15875 = new Vector();
        this._$15876 = new Vector();
        this._$15877 = new Vector();
        this._$15878 = new Vector();
        this._$15879 = new Vector();
        this._$1846 = new ConnectDB();
        this._$15880 = new File("D:\\NewTrainData");
        this._$15881 = new File("D:\\NewTrainData\\Category.txt");
        this._$15882 = new File("D:\\NewTrainData\\Category2.txt");
        this._$15883 = new File("D:\\NewTrainData\\Category3.txt");
        this._$15884 = new StringBuffer();
        this._$15886 = new StringBuffer();
        this._$15887 = new StringBuffer();
        this._$15888 = new StringBuffer();
        this._$15889 = new StringBuffer();
        this._$15890 = new StringBuffer();
        this._$15891 = new String();
        this._$15892 = new String();
        this._$15894 = null;
        this._$2483 = null;
        this._$15895();
        this._$15896();
        this._$6868();
        this._$15897();
    }
    
    private void _$15899(final String s) {
        try {
            final byte[] array = new byte[s.length()];
            final byte[] array2 = new byte[array.length];
            for (int i = 0; i < array.length; ++i) {
                array[i] = (byte)s.charAt(i);
            }
            for (int j = 0; j < array.length; ++j) {
                array2[j] = (byte)~array[j];
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(this._$15882);
            for (int k = 0; k < array2.length; ++k) {
                fileOutputStream.write(array2[k]);
            }
            fileOutputStream.close();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String _$15898() {
        int $1626 = 1;
        final String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (this._$15881.exists() && format.equals(this._$15892)) {
            $1626 = this._$1626;
            ++$1626;
        }
        final String fillLeft = StringHandler.fillLeft(Integer.toString($1626), "0", 2);
        final StringBuffer sb = new StringBuffer("ver:" + format);
        sb.append(fillLeft);
        return sb.toString();
    }
    
    private void _$15897() {
        try {
            for (int size = this._$15876.size(), i = 0; i < size; ++i) {
                (this._$15879 = new Vector()).addElement(((Vector<Object>)this._$15876.get(i)).get(0).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(1).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(2).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(3).toString());
                if (((Vector<Object>)this._$15876.get(i)).get(4).toString().equals("false")) {
                    this._$15879.addElement("0");
                }
                else {
                    this._$15879.addElement("1");
                }
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(5).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(6).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(7).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(8).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(9).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(10).toString());
                this._$15879.addElement(((Vector<Object>)this._$15876.get(i)).get(11).toString());
                this._$15878.addElement(this._$15879);
            }
            for (int j = 0; j < this._$15878.size(); ++j) {
                this._$15887 = new StringBuffer(this._$15878.get(j).toString().replaceAll(" ", ""));
                this._$15887 = this._$15887.delete(0, 1);
                this._$15887 = this._$15887.delete(this._$15887.length() - 1, this._$15887.length());
                this._$15886.append(this._$15887).append(",\n");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$15895() {
        try {
            if (this._$15881.exists()) {
                final FileReader fileReader = new FileReader(this._$15881);
                for (int i = 0; i < 14; ++i) {
                    this._$15888.append((char)fileReader.read());
                }
                this._$15892 = this._$15888.substring(4, this._$15888.length() - 2);
                this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
                fileReader.close();
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$15896() {
        try {
            this._$15876 = this._$1846.getData("SELECT * FROM Train_Type");
        }
        catch (final SQLException ex) {
            ex.printStackTrace();
        }
        catch (final Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    private String _$15902() throws Exception {
        this._$2483 = new FileReader(this._$15881);
        this._$15894 = new BufferedReader(this._$2483);
        String line;
        while ((line = this._$15894.readLine()) != null) {
            this._$15890.append(line);
        }
        this._$15894.close();
        this._$2483.close();
        return this._$15890.toString();
    }
    
    private void _$15903() throws Exception {
        this._$15892 = this._$15902().substring(4, 14).toString();
    }
    
    private String _$15904() {
        this._$15891 = this._$15892.substring(0, 14);
        this._$15891 = this._$15891.substring(this._$15891.length() - 8, this._$15891.length());
        return this._$15891.toString();
    }
    
    private void _$6868() {
        try {
            for (int size = this._$15876.size(), i = 0; i < size; ++i) {
                (this._$15877 = new Vector()).addElement(((Vector<Object>)this._$15876.get(i)).get(0).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(2).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(3).toString());
                if (((Vector<Object>)this._$15876.get(i)).get(4).toString().equals("false")) {
                    this._$15877.addElement("0");
                }
                else {
                    this._$15877.addElement("1");
                }
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(5).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(6).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(7).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(8).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(9).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(10).toString());
                this._$15877.addElement(((Vector<Object>)this._$15876.get(i)).get(11).toString());
                this._$15875.addElement(this._$15877);
            }
            for (int j = 0; j < this._$15875.size(); ++j) {
                this._$15885 = new StringBuffer(this._$15875.get(j).toString().replaceAll(" ", ""));
                this._$15885 = this._$15885.delete(0, 1);
                this._$15885 = this._$15885.delete(this._$15885.length() - 1, this._$15885.length());
                this._$15884.append(this._$15885).append(",\n");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(final String[] array) {
        try {
            InitParameters.start();
            new CreateTrainCategoryData().saveTrainCategoryData();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveTrainCategoryData() {
        try {
            if (!this._$15880.exists()) {
                this._$15880.createNewFile();
            }
            if (!this._$15881.exists()) {
                this._$15881.createNewFile();
            }
            this._$15884.insert(0, this._$15898() + "\n");
            final int length = this._$15884.length();
            final char[] array = new char[length + 2];
            for (int i = 0; i < length; ++i) {
                array[i] = this._$15884.charAt(i);
            }
            this._$15893 = new CRC(array, array.length - 2);
            this._$15884.append("crc:").append(this._$15893.getCRC());
            final FileWriter fileWriter = new FileWriter(this._$15881);
            fileWriter.write(this._$15884.toString());
            fileWriter.close();
            this._$15899(this._$15884.toString());
            this._$15886.insert(0, this._$15898() + "\n");
            final int length2 = this._$15886.length();
            final char[] array2 = new char[length2 + 2];
            for (int j = 0; j < length2; ++j) {
                array2[j] = this._$15886.charAt(j);
            }
            this._$15893 = new CRC(array2, array2.length - 2);
            this._$15886.append("crc:").append(this._$15893.getCRC());
            final FileWriter fileWriter2 = new FileWriter(this._$15883);
            fileWriter2.write(this._$15886.toString());
            fileWriter2.close();
            this._$15902();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
