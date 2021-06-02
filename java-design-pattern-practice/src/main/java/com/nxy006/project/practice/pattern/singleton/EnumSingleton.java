package com.nxy006.project.practice.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

// 枚举单例实现
public enum EnumSingleton {
    INSTANCE;                                                          // JVM 保证只生成一个 INSTANCE 对象

    private AtomicLong id = new AtomicLong(0);              // 单例中的数据

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
                    Long id = EnumSingleton.INSTANCE.getId();
                    System.out.println("Id: " + id);
                }
            }.start();
        }
    }
}
