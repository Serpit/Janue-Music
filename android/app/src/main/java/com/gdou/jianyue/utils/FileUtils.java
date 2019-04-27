package com.gdou.jianyue.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.gdou.jianyue.JanueMusicApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.flutter.app.FlutterApplication;

public class FileUtils {

    public static File saveLrcFile( InputStream ins, String musicName){
        File file;
        String path = JanueMusicApplication.getContext().getExternalCacheDir().getPath()+"/"+musicName+".lrc";
        Log.d("FileUtils","lrc-path: "+path);
        file = new File(path);
        try {

            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return file;
    }
    public static boolean isFileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static boolean isLrcFileExists(String musicName){
        String path = JanueMusicApplication.getContext().getExternalCacheDir().getPath()+"/"+musicName;
        return isFileExists(path);

    }

    public static File getLrcFile(String musicName){
        String path = JanueMusicApplication.getContext().getExternalCacheDir().getPath()+"/"+musicName+".lrc";
        return new File(path);
    }
}
