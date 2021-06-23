package com.nxy006.project.practice.base.io.basic;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;

public class FileIOPractice {
    protected static final Logger logger = LogManager.getLogger();

    private static final String FILE_PATH = "io/file.txt";
    private static final String FILE_COPY_NAME = "fileCopy.txt";
    private static final String SEPARATOR = "/";

    public static void main(String[] args) {
        try {
            URL url = FileIOPractice.class.getClassLoader().getResource(FILE_PATH);
            if (url == null) {
                logger.error("File not exist: " + FILE_PATH);
                return;
            }

            // 文件内容读取
            File originFile = new File(url.getFile());

            printContent(originFile);

            // 复制文件创建
            File copyFile = new File(originFile.getParent() + SEPARATOR + FILE_COPY_NAME);
            if (!copyFile.exists() && !copyFile.createNewFile()) {
                logger.error("File create failed: " + copyFile.getAbsolutePath());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printContent(File originFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(originFile));
            String s;
            while((s = bufferedReader.readLine()) != null) {
                logger.info("File Content: " + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
