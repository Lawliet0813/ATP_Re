// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.io.FileWriter;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import java.io.FileReader;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.io.File;
import java.text.NumberFormat;
import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class CreateTrainTypeData
{
    private Vector _$15914;
    private Vector _$15915;
    private Vector _$15916;
    private Vector _$15917;
    private Vector _$15918;
    private Vector _$15919;
    private ConnectDB _$1846;
    private CRC _$15893;
    private NumberFormat _$6855;
    private String _$15910;
    private StringBuffer _$15888;
    private File _$15880;
    private File _$15924;
    private File _$15926;
    private File _$15925;
    private StringBuffer _$15922;
    private StringBuffer _$15923;
    private StringBuffer _$15920;
    private StringBuffer _$15921;
    private int _$1626;
    
    public CreateTrainTypeData() {
        this._$15914 = new Vector();
        this._$15915 = new Vector();
        this._$15916 = new Vector();
        this._$15917 = new Vector();
        this._$15918 = new Vector();
        this._$15919 = new Vector();
        this._$15920 = new StringBuffer();
        this._$15922 = new StringBuffer();
        this._$15923 = new StringBuffer();
        this._$1846 = new ConnectDB();
        this._$15880 = new File("D:\\NewTrainData");
        this._$15924 = new File("D:\\NewTrainData\\train_type.txt");
        this._$15925 = new File("D:\\NewTrainData\\train_type2.txt");
        this._$15926 = new File("D:\\NewTrainData\\train_type3.txt");
        this._$15888 = new StringBuffer();
        this._$15910 = new String();
        this._$3120();
        (this._$6855 = NumberFormat.getInstance()).setMaximumFractionDigits(3);
        this._$6867();
        this._$15927();
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
            final FileOutputStream fileOutputStream = new FileOutputStream(this._$15925);
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
        if (this._$15924.exists() && format.equals(this._$15910)) {
            $1626 = this._$1626;
            ++$1626;
        }
        final String fillLeft = StringHandler.fillLeft(Integer.toString($1626), "0", 2);
        final StringBuffer sb = new StringBuffer("ver:" + format);
        sb.append(fillLeft);
        return sb.toString();
    }
    
    private void _$15927() {
        try {
            this._$15915 = this._$1846.getData("SELECT * FROM Train_Data");
            for (int size = this._$15915.size(), i = 0; i < size; ++i) {
                (this._$15919 = new Vector()).addElement(((Vector<Object>)this._$15915.get(i)).get(0));
                this._$15919.addElement(((Vector<Object>)this._$15915.get(i)).get(1));
                this._$15919.addElement(((Vector<Object>)this._$15915.get(i)).get(2));
                this._$15919.addElement(((Vector<Object>)this._$15915.get(i)).get(3));
                this._$15919.addElement(((Vector<Object>)this._$15915.get(i)).get(4));
                this._$15919.addElement(((Vector<Object>)this._$15915.get(i)).get(5));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(6)));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(7)));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(8)));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(9)));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(10)));
                this._$15919.addElement(this._$6855.format(((Vector<Object>)this._$15915.get(i)).get(11)));
                this._$15917.addElement(this._$15919);
            }
            for (int j = 0; j < this._$15917.size(); ++j) {
                this._$15923 = new StringBuffer(this._$15917.get(j).toString().replaceAll(" ", ""));
                this._$15923 = this._$15923.delete(0, 1);
                this._$15923 = this._$15923.delete(this._$15923.length() - 1, this._$15923.length());
                this._$15922.append(this._$15923).append(",\n");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$6867() {
        try {
            this._$15915 = this._$1846.getData("SELECT Train_Category.Train_Type AS Train_Type , Train_Data.* FROM Train_Data left join Train_Category ON Train_Category.Train_SN = Train_Data.Train_SN ");
            for (int size = this._$15915.size(), i = 0; i < size; ++i) {
                (this._$15916 = new Vector()).addElement(((Vector<Object>)this._$15915.get(i)).get(1));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(0));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(2));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(3));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(4));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(5));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(6));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(7));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(8));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(9));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(10));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(11));
                this._$15916.addElement(((Vector<Object>)this._$15915.get(i)).get(12));
                this._$15914.addElement(this._$15916);
            }
            for (int j = 0; j < this._$15914.size(); ++j) {
                this._$15921 = new StringBuffer(this._$15914.get(j).toString().replaceAll(" ", ""));
                this._$15921 = this._$15921.delete(0, 1);
                this._$15921 = this._$15921.delete(this._$15921.length() - 1, this._$15921.length());
                this._$15920.append(this._$15921).append(",\n");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$3120() {
        if (this._$15924.exists()) {
            try {
                final FileReader fileReader = new FileReader(this._$15924);
                for (int i = 0; i < 14; ++i) {
                    this._$15888.append((char)fileReader.read());
                }
                this._$15910 = this._$15888.substring(4, this._$15888.length() - 2);
                this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
                fileReader.close();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(final String[] array) {
        try {
            InitParameters.start();
            new CreateTrainTypeData().saveTrainTypeData();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveTrainTypeData() {
        try {
            if (!this._$15880.exists()) {
                this._$15880.createNewFile();
            }
            if (!this._$15924.exists()) {
                this._$15924.createNewFile();
            }
            this._$15920.insert(0, this._$15898() + "\n");
            final int length = this._$15920.length();
            final char[] array = new char[length + 2];
            for (int i = 0; i < length; ++i) {
                array[i] = this._$15920.charAt(i);
            }
            this._$15893 = new CRC(array, array.length - 2);
            this._$15920.append("crc:").append(this._$15893.getCRC());
            final FileWriter fileWriter = new FileWriter(this._$15924);
            fileWriter.write(this._$15920.toString());
            fileWriter.close();
            this._$15899(this._$15920.toString());
            this._$15922.insert(0, this._$15898() + "\n");
            final int length2 = this._$15922.length();
            final char[] array2 = new char[length2 + 2];
            for (int j = 0; j < length2; ++j) {
                array2[j] = this._$15922.charAt(j);
            }
            this._$15893 = new CRC(array2, array2.length - 2);
            this._$15922.append("crc:").append(this._$15893.getCRC());
            final FileWriter fileWriter2 = new FileWriter(this._$15926);
            fileWriter2.write(this._$15922.toString());
            fileWriter2.close();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
