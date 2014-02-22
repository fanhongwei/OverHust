package com.unique.overhust.DownloadStreetView;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by fhw on 2/11/14.
 */
public class UnZip {
    private final int BUFFER = 1024;

    public UnZip(String fileName, String toPath) throws ZipException, IOException {

        File file = new File(Environment.getExternalStorageDirectory() + "/street");
        if (file.exists()) {
            clear(file);
        }


        ZipFile zfile = new ZipFile(fileName);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[BUFFER];
        while (zList.hasMoreElements())

        {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                String dirstr = toPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(toPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int count = 0;
            while ((count = is.read(buf, 0, BUFFER)) != -1) {
                os.write(buf, 0, count);
            }
            is.close();
            os.close();
        }

        zfile.close();
    }

    /**
     * 40     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * 41     * @param baseDir 指定根目录
     * 42     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * 43     * @return java.io.File 实际的文件
     * 44
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            Log.d("upZipFile", "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d("upZipFile", "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            Log.d("upZipFile", "2ret = " + ret);
            return ret;
        }
        return ret;
    }

    public void clear(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    clear(f);
                }
            }
            file.delete();
        }
    }
}
