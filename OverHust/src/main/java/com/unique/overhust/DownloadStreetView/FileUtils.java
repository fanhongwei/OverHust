package com.unique.overhust.DownloadStreetView;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by fhw on 2/11/14.
 */
public class FileUtils {
    private String SDPATH = null;

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        //获得当前外部存储设备SD卡的目录

        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }

    //创建文件
    public File creatFile(String fileName) throws IOException{
        File file=new File(SDPATH+fileName);
        file.createNewFile();
        return file;
    }

    //创建目录
    public File creatDir(String fileName) throws IOException{
        File dir=new File(SDPATH+fileName);
        dir.mkdir();
        return dir;
    }

    //判断文件是否存在
    public boolean isExist(String fileName){
        File file=new File(SDPATH+fileName);
        return file.exists();
    }

    public File writeToSDPATHFromInput(String path,String fileName,InputStream inputStream){
        File file=null;
        OutputStream outputStream=null;
        try{
            creatDir(path);
            file=creatFile(path+fileName);
            outputStream=new FileOutputStream(file);
            byte buffer[] =new byte[1024];
            //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
            while(inputStream.read(buffer)!=-1){
                outputStream.write(buffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }
}
