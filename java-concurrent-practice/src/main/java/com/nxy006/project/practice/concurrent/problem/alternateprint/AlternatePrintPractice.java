package com.nxy006.project.practice.concurrent.problem.alternateprint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 用 Java 的 wait + notify 机制实现：
 *     启动两个线程, 一个输出 1,3,5,7,...,99, 另一个输出 2,4,6,8,...,100 最后 STDOUT 中按序输出 1,2,3,4,5,...,100
 */
public class AlternatePrintPractice {
    private static final Logger logger = LogManager.getLogger();
    private static final Object lock = new Object();
    private static int counter = 1;

    public static void main(String[] args) {
        // 启动打印线程
        AlternatePrintThread oddPrintThread = new AlternatePrintThread(0);
        oddPrintThread.start();
        AlternatePrintThread evenPrintThread = new AlternatePrintThread(1);
        evenPrintThread.start();

        // 等待所有子线程结束后再退出主线程
        try {
            oddPrintThread.join();
            evenPrintThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class AlternatePrintThread extends Thread {
        private int mod;

        public AlternatePrintThread(int mod) {
            this.mod = mod;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (counter % 2 != mod) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (counter > 100) {
                        return ;
                    }

                    logger.info(counter++);
                    lock.notifyAll();
                }
            }
        }
    }
}
