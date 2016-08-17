package com.yanglin.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @desc 类功能描述：
 * @author devuser
 * @createTime 2016年8月15日 上午10:56:18
 *
 * @version V2.0
 */
public class AppendToFileExample {
    public static void main(String[] args) {
        try {
            String data = " This content will append to the end of the file";

            File file = new File("E:/devzone/workspace/new/DbToJson/json/3.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     
}
