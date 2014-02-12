package com.unique.overhust.DownloadStreetView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fhw on 2/11/14.
 */
public class HttpDownloader {
    private URL url = null;

    public String download(String urlStr) {

        StringBuffer stringBuffer = new StringBuffer();
        String line;
        BufferedReader bufferedReader = null;

        try {
            url = new URL(urlStr);    //创建一个url对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();  //得到一个HttpURLConnection对象
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));  //得到IO流,使用IO流读取数据
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    //-1表示下载文件出错，0表示下载文件成功，1表示文件已存在
    public int download(String urlStr, String path, String fileName) {
        InputStream inputStream = null;
        FileUtils fileUtils = new FileUtils();
        if (fileUtils.isExist(path + fileName)) {
            return 1;
        } else {
            try {
                inputStream = getFromUrl(urlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            File file = fileUtils.writeToSDPATHFromInput(path, fileName, inputStream);
            if (file != null) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    //根据url字符串得到输入流
    public InputStream getFromUrl(String urlStr) throws IOException {
        url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream input = httpURLConnection.getInputStream();
        return input;
    }
}
