package com.nxy006.project.practice.base.io.model.common;

import com.nxy006.project.practice.base.io.model.bio.BioMultipleThreadServerPractice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通用多线程客户端，适用于 BIO/NIO 模式多线程服务端
 *
 * @see BioMultipleThreadServerPractice
 */
public class MultipleThreadClientPractice {
    protected static final Logger logger = LogManager.getLogger();

    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 10, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    public static void main(String[] args) throws IOException {
        logger.debug("Multiple Thread Clint is Starting");

        int threadCnt = 15, messageCnt = 10;
        while(threadCnt-- >= 0) {
            Socket socket = new Socket(CommonConfig.SERVER_HOST, CommonConfig.SERVER_PORT);
            threadPoolExecutor.execute(new SocketMessageSendThread(socket, messageCnt));
        }
    }

    private static final class SocketMessageSendThread extends Thread {
        private Socket socket;
        private int count;

        public SocketMessageSendThread(Socket socket, int count) {
            this.socket = socket;
            this.count = count;
        }// 练习

        @Override
        public void run() {
            logger.debug("Thread is Running, start send socket message to server. thread name: {}", Thread.currentThread().getName());

            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                int cnt = 1;
                while (cnt++ < count) {
                    printStream.println(Thread.currentThread().getName() + ":message_" + cnt);
                    printStream.flush();

                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
