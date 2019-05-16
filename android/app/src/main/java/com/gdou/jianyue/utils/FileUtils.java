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


    public static File saveMusicFile(InputStream inputStream,String musicname,String artist){
        File file;
        String fileName = artist + " - " + musicname+".mp3";
        String path = Environment.getExternalStorageDirectory() + "/JanueMusic"+"/music/";
        Log.d("FileUtils","music-path: "+path);
        file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        File fileDown = new File(path+fileName);
        try {

            OutputStream os = new FileOutputStream(fileDown);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return fileDown;
    }

    public static boolean isFileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static boolean isMusicFileExists(String musicname){
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/"+musicname+".mp3";
        return isFileExists(path);
    }

    public static boolean isLrcFileExists(String musicName){
        String path = JanueMusicApplication.getContext().getExternalCacheDir().getPath()+"/"+musicName;
        return isFileExists(path);

    }

    public static File getLrcFile(String musicName){
        String path = JanueMusicApplication.getContext().getExternalCacheDir().getPath()+"/"+musicName+".lrc";
        return new File(path);
    }

    public static String getRelativeMusicDir() {
        String dir = "JanueMusic/Music/";
        return mkdirs(dir);
    }
    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }
}
