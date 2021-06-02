package com.nxy006.project.practice.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

// 懒汉式单例实现
public class LazySingleton {
    private static LazySingleton instance;                      // 单例对象
    private AtomicLong id = new AtomicLong(0);       // 单例中的数据

    // 私有构造方法，避免被从外部创建
    private LazySingleton(){
    }

    // 懒汉式，直到需要该对象时才创建，每次调用都必须加锁，效率较低
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // 获取单例中的数据，本例是生成唯一自增 ID
    public Long getId() {
        return id.incrementAndGet();
    }

    // 测试：正确情况下应该打印不重复的 ID 值（AtomicLong 是线程安全的，如果打印了 'Id: 100' 就说明成功了）
    // 如果去掉 synchronized 关键字，则多次执行，可能看到两次 'Id: 1' 日志，即 Id 生成重复，不符合要求
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    Long id = LazySingleton.getInstance().getId();
                    System.out.println("Id: " + id);
                }
            }.start();
        }
    }
}
