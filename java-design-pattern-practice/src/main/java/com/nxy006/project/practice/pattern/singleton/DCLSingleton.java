package com.nxy006.project.practice.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

// DCL (double-checked locking) Singleton 双重校验锁单例实现
public class DCLSingleton {
    private volatile static DCLSingleton instance;              // 单例对象，不使用 volatile 关键字可能在低版本 JDK 中因指令重排序导致错误
    private AtomicLong id = new AtomicLong(0);       // 单例中的数据

    // 私有构造方法，避免被从外部创建
    private DCLSingleton() {
    }

    // 双重校验锁（懒加载）
    // 只在对象未初始化时才加锁，对象已创建则不需要加锁，兼顾多线程支持和效率
    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {                 // 只在单例对象未创建化时才加锁
                if (instance == null) {                         // 如果获取锁后发现对象已创建，则不再创建，避免重复创建
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    // 获取单例中的数据，本例是生成唯一自增 ID
    public Long getId() {
        return id.incrementAndGet();
    }

    // 测试：正确情况下应该打印不重复的 ID 值（AtomicLong 是线程安全的，如果打印了 'Id: 100' 就说明成功了）
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    Long id = DCLSingleton.getInstance().getId();
                    System.out.println("Id: " + id);
                }
            }.start();
        }
    }
}
