package com.example.utility;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 */
@Component
public class FileUtil {

    //文件路径+名称
    private static String filenameTemp;

    public static boolean createFile(String path, String fileName, String filecontent) {
        boolean bool = false;
        //文件路径+名称+文件类型
        filenameTemp = path + fileName;
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is " + filenameTemp);
                //创建文件成功后，写入内容到文件里
                boolean b = writeFileContent(filenameTemp, filecontent);
                System.out.println("写入文件返回结果" + b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        boolean bool = false;
        //新写入的行，换行
        String filein = newstr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            //文件路径(包括文件名称)
            File file = new File(filepath);
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    public static String readTxt(String filePath) {
        String lineTxt = "";
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);

                while ((lineTxt = br.readLine()) != null) {
                    return lineTxt;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");

            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        return lineTxt;
    }

    /**
     * 删除文件
     *
     * @param path
     * @param fileName
     */
    public static boolean delFile(String path, String fileName) {
        Boolean bool = false;
        filenameTemp = path + fileName;
        File file = new File(filenameTemp);
        try {
            if (file.exists()) {
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }

    /**
     * TODO: 判断文件是否存在
     *
     * @author 李兵
     * @date 2019/7/16 17:03
     */
    public static Boolean isFile(String filePath) {
        return new File(filePath).isFile();
    }

    /**
     * TODO: 是否存在文件夹，若是不存在则创建
     *
     * @author 李兵
     * @date 2019/7/16 17:03
     */
    public void isCreateFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
            System.out.println("创建文件夹");
        } else {
            System.out.println("文件夹已存在");
        }
    }

    /**
     * TODO: 根据系统返回文件夹路径
     *
     * @author 李兵
     * @date 2019/7/16 17:04
     */
    public String diskPath() {
        String os = System.getProperty("os.name");
        //如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
            return "C:/map/zxwjj/";
        } else { //linux 和mac
            return "/opt/insight/nginx/html/image/";
        }
    }
}
