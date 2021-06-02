package com.nxy006.project.practice.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

// 饿汉式单例实现
public class EagerSingleton {
    private static EagerSingleton instance = new EagerSingleton();              // 单例对象，类初始化即创建
    private AtomicLong id = new AtomicLong(0);                       // 单例中的数据

    // 私有构造方法，避免被从外部创建
    private EagerSingleton() {
    }

    // 饿汉式，对象已在加载时被创建，不需要加锁，效率最高。缺点是即便该单例还未使用，也会被实例化，导致占用更多内存
    public EagerSingleton getInstance() {
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
