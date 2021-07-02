package com.nxy006.project.practice.concurrent.basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的不同方法练习
 * <ol>
 *     <li>实现 Runnable</li>
 *     <li>实现 Callable</li>
 *     <li>继承 Thread</li>
 * </ol>
 */
public class ThreadCreatePractice {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main Thread: " + Thread.currentThread().getName());

        // 1. 实现 Runnable 接口方式
        new Thread(new MyRunnable()).start();

        // 2. 继承 Thread 方式
        new MyThread().start();

        // 3. 实现 Callable 方式（支持返回值）
        FutureTask<Integer> ft = new FutureTask<>(new MyCallable());
        new Thread(ft).start();
        System.out.println("FutureTask, Return: " + ft.get());


        // < 实现接口 VS 继承 Thread >
        //
        // 实现接口更好一些，因为：
        //   1. Java 不支持多重继承，因此继承了 Thread 类就无法继承其它类，但是可以实现多个接口
        //   2. 类可能只要求可执行就行，继承整个 Thread 类开销过大
    }


    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("MyRunnable is RUNNING, Thread: " + Thread.currentThread().getName());
        }
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread is RUNNING,   Thread: " + Thread.currentThread().getName());

        }
    }


    private static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("MyCallable is RUNNING, Thread: " + Thread.currentThread().getName());
            return 2333;
        }
    }
}
