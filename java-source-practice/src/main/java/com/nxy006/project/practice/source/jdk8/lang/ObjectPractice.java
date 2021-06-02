package com.nxy006.project.practice.source.jdk8.lang;

import com.nxy006.project.practice.utils.PrintUtils;

import java.util.HashSet;
import java.util.Set;

public class ObjectPractice {

    public static void main(String[] args) {
        Set<ObjectExampleOne> set = new HashSet<ObjectExampleOne>();
        set.add(new ObjectExampleOne(1));
        set.add(new ObjectExampleOne(1));
        set.add(new ObjectExampleOne(1));

        // 与预期不符，加入了三个 value 相同的对象，但不能正确去重
        System.out.println("Final Set: " + PrintUtils.toString(set));
    }

    // Example 1: 没有实现 hashcode 和 equals 方法
    private static class ObjectExampleOne {
        private int value;

        ObjectExampleOne(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "ObjectExampleOne{" +
                    "value=" + value +
                    '}';
        }
    }
}

