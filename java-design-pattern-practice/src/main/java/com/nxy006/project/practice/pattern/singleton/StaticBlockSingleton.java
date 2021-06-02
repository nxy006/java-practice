package com.nxy006.project.practice.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

// 静态代码块单例实现，实际是饿汉式的另一种写法
public class StaticBlockSingleton {
    private static StaticBlockSingleton instance;
    private AtomicLong id = new AtomicLong(0);

    // 相比饿汉式，使用代码块支持异常捕获
    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 私有构造方法，避免被从外部创建
    private StaticBlockSingleton() {
    }


    // 对象已被创建，直接返回，特点同饿汉式
    public static StaticBlockSingleton getInstance() {
        return instance;
    }

    // 获取单例中的数据，本例是生成唯一自增 ID
    public Long getId() {
        return id.incrementAndGet();
    }

    // 测试：正确情况下应该打印不重复的 ID 值（AtomicLong 是线程安全的，如果打印了 'Id: 100' 就说明成功了
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    Long id = StaticBlockSingleton.getInstance().getId();
                    System.out.println("Id: " + id);
                }
            }.start();
        }
    }
}
