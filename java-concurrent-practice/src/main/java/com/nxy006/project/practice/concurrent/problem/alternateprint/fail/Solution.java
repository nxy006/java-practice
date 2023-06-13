package com.nxy006.project.practice.concurrent.problem.alternateprint.fail;

import java.util.Arrays;
import java.util.concurrent.*;

public class Solution {
    public static class Singleton {
        private static volatile Singleton singleton;
        private Singleton() {
        }

        public static Singleton getInstance() {
            if (singleton == null) {
                synchronized (Solution.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }
}
