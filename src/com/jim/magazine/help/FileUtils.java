package com.jim.magazine.help;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;

public class FileUtils {
    /**
     * 删除某个目录
     * 
     * @param srcDir 目录地址
     * @throws IOException
     */
    public static void deleteDir(String srcDir) throws IOException{
        File file = new File(srcDir);
        if (!file.exists())
            return;

        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                deleteDir(srcDir + "/" + files[i].getName());
            deleteFile(srcDir + "/" + files[i].getName());
        }
        deleteFile(srcDir);
    }
    
    /**
     * 删除某个文件目录
     * 
     * @param file 需要删除的文件
     */
    public static void deleteFile(File file){
        if(file != null && file.exists())
            file.delete();
    }
    
    /**
     * @param path 文件存储的路径
     */
    public static void deleteFile(String path){
        File file = new File(path);
        if(file != null && file.exists()){
            file.delete();
        }
    }
    
    /**
     * 创建某个文件目录 
     * 
     * @param path 目录地址
     * @return
     */
    public static File makeDir(String path){
        File file = new File(path);
        if(!file.exists())
            file.mkdirs();
        return file;
    }
    
    /**
     * 获取当前目录信息
     * 
     * @param path 根目录地址
     * @return
     */
    public static List<String> getDirFile(String path){
        File file = makeDir(path);
        File[] files = file.listFiles();
        List<String> list = new ArrayList<String>();
        
        if(files != null && files.length != 0){
            for(int i=0;i<files.length;i++){
                list.add(files[i].getName());
            }
        }
        return list;
    }
    
    /**
     * 写数据到本地SD卡文件
     * 
     * @param path 文件的目录
     * @param name 存储文件名
     * @param data 数据源
     * @return
     */
    public static void dateWriteToFile(String path,String name,List<String> dataList){
        String dir = Environment.getExternalStorageDirectory() + "/" + path;
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        File tofile = new File(dir,name);
        try {
            FileWriter fw=new FileWriter(tofile);
            BufferedWriter buffw=new BufferedWriter(fw);
            PrintWriter pw=new PrintWriter(buffw);
            
            for(int i=0;i<dataList.size();i++){
                pw.println(dataList.get(i) + "\n");
            }
            pw.close();
            buffw.close();
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * 写数据到本地SD卡文件
     * 
     * @param path
     * @param name
     * @param date
     */
    public static void dataWriteToFile(String path,String name,String data){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        File tofile = new File(path,name);
        try {
            FileWriter fw=new FileWriter(tofile);
            BufferedWriter buffw=new BufferedWriter(fw);
            PrintWriter pw=new PrintWriter(buffw);
            
            pw.println(data);
            pw.close();
            buffw.close();
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * 判断文件是否存在
     * 
     * @param path
     * @return
     */
    public static boolean fileIsExist(String path){
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }
    
    /**
     * 读取txt文件中的内容
     */
    public static String readSDCardTxt(String path){
        String res = ""; 
        try { 
            FileInputStream fin = new FileInputStream(path); 
            int length = fin.available(); 
            byte[] buffer = new byte[length]; 
            fin.read(buffer); 
            res = EncodingUtils.getString(buffer, "UTF-8"); 
            fin.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return res; 
    }
}