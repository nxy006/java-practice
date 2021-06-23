package com.nxy006.project.practice.base.io.model.bio;

import com.nxy006.project.practice.base.io.model.common.CommonConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * I/O 模型实践：BIO（Blocking I/O）阻塞式 IO：单连接，无限条消息发送接收
 */
public class BioMultipleThreadServerPractice {
    protected static final Logger logger = LogManager.getLogger();

    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 10, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    public static void main(String[] args) throws IOException {
        logger.debug("[1] BIO Server is Starting");

        // 1. 启动服务器端口
        ServerSocket serverSocket = new ServerSocket(CommonConfig.SERVER_PORT);
        logger.debug("[2] BIO Server is Running, Waiting a socket.");

        // 2. 等待客户端连接（阻塞方法）
        Socket socket;
        while((socket = serverSocket.accept()) != null) {
            // 3. 使用输入流获取客户端数据
            threadPoolExecutor.execute(new SocketMessagePrintThread(socket));
        }
    }

    private static final class SocketMessagePrintThread extends Thread {
        private Socket socket;

        public SocketMessagePrintThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            try {
                inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line ;
                while((line = bufferedReader.readLine()) != null){
                    logger.info("Receive message: " + line);
                }
                logger.info("Receive message finish");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
