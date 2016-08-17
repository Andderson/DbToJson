package com.yanglin.test;

import java.io.File;
import java.io.IOException;

/**
 * @desc 类功能描述：
 * @author devuser
 * @createTime 2016年8月15日 上午11:15:34
 *
 * @version V2.0
 */
public class FilePathExample {
    
    public static void main(String[] args) {
        test3();
    }
    public static void test3() {
        // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "D:" + separator + "myDir1" + separator + "myDir2";
        // 以下这句的效果等同于上面两句，windows下正斜杠/和反斜杠都是可以的
        // linux下只认正斜杠，为了保证跨平台性，不建议使用反斜杠（在java程序中是转义字符，用\来表示反斜杠）
        // String directory = "myDir1/myDir2";
        String fileName = "myFile.txt";
        // 在内存中创建一个文件对象，注意：此时还没有在硬盘对应目录下创建实实在在的文件
        File f = new File(directory, fileName);
        if (f.exists()) {
            // 文件已经存在，输出文件的相关信息
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
            // 先创建文件所在的目录
            f.getParentFile().mkdirs();
            try {
                // 创建新文件
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("创建新文件时出现了错误。。。");
                e.printStackTrace();
            }
        }
    }
}
