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
import java.io.File;
import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class CreateTrainDataMapping
{
    private Vector _$6983;
    private Vector _$15906;
    private Vector _$15907;
    private ConnectDB _$1846;
    private CRC _$15893;
    private String _$15910;
    private StringBuffer _$15888;
    private File _$15880;
    private File _$15814;
    private StringBuffer _$15908;
    private StringBuffer _$15909;
    private int _$1626;
    
    public CreateTrainDataMapping() {
        this._$6983 = new Vector();
        this._$15906 = new Vector();
        this._$1846 = new ConnectDB();
        this._$15908 = new StringBuffer();
        this._$15910 = new String();
        this._$15888 = new StringBuffer();
        this._$15880 = new File("D:\\NewTrainData");
        this._$15814 = new File("D:\\NewTrainData\\TrainDataMapping.txt");
        this._$3120();
        this._$15911();
    }
    
    private String _$15898() {
        int $1626 = 1;
        final String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (this._$15814.exists() && format.equals(this._$15910)) {
            $1626 = this._$1626;
            ++$1626;
        }
        final String fillLeft = StringHandler.fillLeft(Integer.toString($1626), "0", 2);
        final StringBuffer sb = new StringBuffer("ver:" + format);
        sb.append(fillLeft);
        return sb.toString();
    }
    
    private void _$15911() {
        try {
            this._$15906 = this._$1846.getData("SELECT * FROM Train_Category");
            for (int i = 0; i < this._$15906.size(); ++i) {
                (this._$15907 = new Vector()).addElement(((Vector<Object>)this._$15906.get(i)).get(0).toString());
                this._$15907.addElement(((Vector<Object>)this._$15906.get(i)).get(1).toString());
                this._$6983.addElement(this._$15907);
            }
            for (int j = 0; j < this._$6983.size(); ++j) {
                this._$15909 = new StringBuffer(this._$6983.get(j).toString().replaceAll(" ", ""));
                this._$15909 = this._$15909.delete(0, 1);
                this._$15909 = this._$15909.delete(this._$15909.length() - 1, this._$15909.length());
                this._$15908.append(this._$15909).append(",\n");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$3120() {
        try {
            if (this._$15814.exists()) {
                final FileReader fileReader = new FileReader(this._$15814);
                for (int i = 0; i < 14; ++i) {
                    this._$15888.append((char)fileReader.read());
                }
                this._$15910 = this._$15888.substring(4, this._$15888.length() - 2);
                this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
                fileReader.close();
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(final String[] array) {
        try {
            InitParameters.start();
            new CreateTrainDataMapping().saveTrainDataMapping();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveTrainDataMapping() {
        try {
            if (!this._$15880.exists()) {
                this._$15880.createNewFile();
            }
            if (!this._$15814.exists()) {
                this._$15814.createNewFile();
            }
            this._$15908.insert(0, this._$15898() + "\n");
            final int length = this._$15908.length();
            final char[] array = new char[length + 2];
            for (int i = 0; i < length; ++i) {
                array[i] = this._$15908.charAt(i);
            }
            this._$15893 = new CRC(array, array.length - 2);
            this._$15908.append("crc:").append(this._$15893.getCRC());
            final FileWriter fileWriter = new FileWriter(this._$15814);
            fileWriter.write(this._$15908.toString());
            fileWriter.close();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
