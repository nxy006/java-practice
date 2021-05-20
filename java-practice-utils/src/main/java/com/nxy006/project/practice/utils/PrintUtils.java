package com.nxy006.project.practice.utils;

import java.util.Set;

public class PrintUtils {
    public static <T> String toString(Set<T> set) {
        StringBuilder sb = new StringBuilder();
        for(T ele : set) {
            sb.append(ele.toString());
            sb.append(", ");
        }

        // output
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
