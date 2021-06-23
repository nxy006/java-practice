package com.nxy006.project.practice.base.io.model.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClientPractice {
    protected static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
        logger.debug("[1] BIO Clint is Starting");

        // 1. 建立与服务端的连接
        Socket socket = new Socket(CommonConfig.SERVER_HOST, CommonConfig.SERVER_PORT);
        logger.debug("[2] BIO Client created a socket, waiting send message");

        // 2. 使用输出流向服务端发送消息（本例中使用字符流，逐行发送）
        System.out.println("Please Input Message Content For Server: ");
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String content = scanner.nextLine();

            printStream.println(content);
            printStream.flush();
            logger.info("Send Message: " + content);
        }
    }
}
