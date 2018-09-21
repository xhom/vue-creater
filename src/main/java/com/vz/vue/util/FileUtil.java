package com.vz.vue.util;

import com.vz.vue.common.CONST;
import java.io.*;

public class FileUtil {

    //文件读取
    public static String readFile(String path){
        StringBuilder content = new StringBuilder();
        try{
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis,"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                content.append(line).append("\n");
                line = br.readLine();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    //文件写入
    public static boolean writeFile(String path, String content){
        try{
            //创建输出目录
            File filePath = new File(CONST.OUTPUT_PATH);
            if(!filePath.exists()) {
                filePath.mkdirs();
            }

            File file = new File(path);
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            BufferedWriter out = new BufferedWriter(osw);
            out.write(content);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
       return true;
    }
}
