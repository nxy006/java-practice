package com.nxy006.project.practice.base.generics;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class GenericsPractice {
    public static void main(String[] args) {
        // 使用原始类型（rawType）的例子
        List listA = new ArrayList();
        listA.add(126L);
        listA.add("126");

        Long num1 = (Long) listA.get(0);
        Long num2 = (Long) listA.get(1);        // 编译不报错，运行时报错：java.lang.ClassCastException

        // 使用泛型之后的例子
        List<Long> listB = new ArrayList<>();
        listB.add(126L);
        //listB.add("126");                     // 将在编译时报错

        Long num3 = listB.get(0);
        Long num4 = listB.get(1);               // 获取对象时不需要手动判断和转换和类型
    }
}
