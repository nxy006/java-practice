package com.nxy006.project.practice.base.io.model.bio;

import com.nxy006.project.practice.base.io.model.common.CommonConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * I/O 模型实践：BIO（Blocking I/O）阻塞式 IO：单连接，无限条消息发送接收
 */
public class BioSimpleServerPractice {
    protected static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
        logger.debug("[1] BIO Server is Starting");

        // 1. 启动服务器端口
        ServerSocket serverSocket = new ServerSocket(CommonConfig.SERVER_PORT);
        logger.debug("[2] BIO Server is Running, Waiting a socket.");

        // 2. 等待客户端连接（阻塞）
        Socket socket = serverSocket.accept();
        logger.debug("[3] BIO Server is accept a socket, waiting message.");

        // 3. 使用输入流获取客户端数据（本例中使用字符流，逐行接收）
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line ;
        while((line = bufferedReader.readLine()) != null){
            logger.info("Receive message: " + line);
        }
    }
}
