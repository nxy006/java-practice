package com.nxy006.project.practice.concurrent.problem.alternateprint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 用 Java 的 wait + notify 机制实现：
 *     启动两个线程, 一个输出 1,3,5,7,...,99, 另一个输出 2,4,6,8,...,100 最后 STDOUT 中按序输出 1,2,3,4,5,...,100
 */
public class AlternatePrintPractice2 {
    private static final Logger logger = LogManager.getLogger();
    private static final Object lock = new Object();
    private static int counter;

    public static void main(String[] args) {
        // 支持 n 个线程交替输出 [start, end] 范围的值
        int start = 1, end = 1200000, step = 15;

        // init counter
        counter = start;

        // 逐个启动打印线程
        List<Thread> threadList = new ArrayList<>(step);
        for(int i = 0; i < step; i++) {
            AlternatePrintThread alternatePrintThread = new AlternatePrintThread(end, step, i);
            alternatePrintThread.start();

            threadList.add(alternatePrintThread);
        }

        // 等待所有子线程结束后再退出主线程
        try {
            for(Thread thread : threadList) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class AlternatePrintThread extends Thread {
        private int end, step, mod;

        public AlternatePrintThread(int end, int step, int mod) {
            this.end = end;
            this.step = step;
            this.mod = mod;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (counter % step != mod) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (counter > end) {
                        return ;
                    }

                    logger.info(counter++);
                    lock.notifyAll();
                }
            }
        }
    }
}
