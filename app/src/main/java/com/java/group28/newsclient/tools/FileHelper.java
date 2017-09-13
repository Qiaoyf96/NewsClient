package com.java.group28.newsclient.tools;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHelper {

    private Context mContext;

    public FileHelper() {
    }

    public FileHelper(Context mContext) {
        super();
        this.mContext = mContext;
    }

    /*
    * 这里定义的是一个文件保存的方法，写入到文件中，所以是输出流
    * */
    public void save(String filename, Object o) throws Exception {
        //这里我们使用私有模式,创建出来的文件只能被本应用访问,还会覆盖原文件哦
        FileOutputStream output = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(output);
        out.writeObject(o);
        out.close();
        output.close();         //关闭输出流
    }


    /*
    * 这里定义的是文件读取的方法
    * */
    public Object read(String filename) throws IOException, ClassNotFoundException {
        //打开文件输入流
        FileInputStream input = mContext.openFileInput(filename);
        ObjectInputStream in = new ObjectInputStream(input);
        Object o = in.readObject();
        in.close();
        input.close();
        return o;
    }

    public void delete(String filename) throws IOException {
        File f = new File(filename);
        if (f.exists()) f.delete();
    }

}
