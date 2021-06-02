package com.nxy006.project.practice.aurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.DebugUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListenerPractice {
    protected static final Logger logger = LogManager.getLogger();

    static {
        // 如果不需要打印连接问题相关日志，这里填写 true
        // System.setProperty(DebugUtils.PROPERTY_DONT_LOG_CONNECTION_ISSUES, Boolean.TRUE.toString());
    }

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new RetryNTimes(0, 0))
                .connectionTimeoutMs(1000)
                .sessionTimeoutMs(1000)
                .build();
        client.start();

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                logger.info("Connection state is changed, current state: " + newState);
            }
        });



        try {
            logger.info("client started");
            Thread.sleep(10000);

            logger.info("lock try acquire");
            InterProcessMutex lock = new InterProcessMutex(client, "/test-path");
            lock.acquire();
            logger.info("lock acquire success");

            // 业务逻辑
            try {
                logger.info("working started");
                Thread.sleep(30000);
                logger.info("working finished");
            } catch (Exception e) {
                e.printStackTrace();
            }

            lock.release();
        } catch (Exception e) {
            logger.error("lock acquire failed", e);
        }

        client.close();
    }
}
