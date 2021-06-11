package com.nxy006.project.practice.concurrent.problem.alternateprint.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 用 Java 的 wait + notify 机制实现：
 *     启动两个线程, 一个输出 1,3,5,7,...,99, 另一个输出 2,4,6,8,...,100 最后 STDOUT 中按序输出 1,2,3,4,5,...,100
 */
public class AlternatePrintFailPractice {
    private static final Logger logger = LogManager.getLogger();
    private static final Object oddLock = new Object(), evenLock = new Object();

    public static void main(String[] args) {
        new OddPrintThread().start();
        new EvenPrintThread().start();

        // 问题 1：调用 oddLock.notify() 可能在 oddLock.wait() 之前，导致 oddLock.wait() 调用后一直无法被触发，线程无线阻塞
        synchronized (oddLock) {
            oddLock.notify();
        }

        // 问题 2：没有保证主线程在子线程完成后退出，当输出范围较大时可能还没输出完，就因主线程退出而中断输出
    }

    private static class OddPrintThread extends Thread {
        @Override
        public void run() {
            for(int i = 1; i <= 10000; i+=2) {
                // 问题 3：如果两个线程都分别在等待各自的锁，就会造成死锁
                // 详细来说，线程 A 刚通知线程 B 还没开始等待锁，此时线程 B 就已经输出完毕且通知了线程 A，此后线程 A、B 均会进入等待状态
                synchronized (oddLock) {
                    try {
                        oddLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                logger.info(i);
                synchronized (evenLock) {
                    evenLock.notify();
                }
            }
        }
    }

    private static class EvenPrintThread extends Thread {
        @Override
        public void run() {
            for(int i = 2; i <= 10000; i+=2) {
                synchronized (evenLock) {
                    try {
                        evenLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                logger.info(i);
                synchronized (oddLock) {
                    oddLock.notify();
                }
            }
        }
    }
}
