package com.nxy006.project.practice.pattern.singleton;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

// 静态内部类单例实现，Bill Pugh 提出
public class InnerStaticHelperSingleton {
    private AtomicLong id = new AtomicLong(0);

    // 静态内部类
    private static class InnerStaticHelper {
        private static InnerStaticHelperSingleton instance = new InnerStaticHelperSingleton();
    }

    // 私有构造方法，避免被从外部创建
    private InnerStaticHelperSingleton() {
    }

    // 首次使用 InnerStaticHelper 内部类时，才会创建 instance 对象，JVM 保证对象的唯一性，兼顾懒加载和效率
    public static InnerStaticHelperSingleton getInstance() {
        return InnerStaticHelper.instance;
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
                    Long id = InnerStaticHelperSingleton.getInstance().getId();
                    System.out.println("Id: " + id);
                }
            }.start();
        }
    }
}
