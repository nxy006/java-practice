package com.nxy006.project.practice.concurrent.problem.alternateprint.fail;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransferDateUtil{
    private static final long DAY_TIME = 24*60*60*1000, TRANSFER_START_TIME = (9*60+30)*60*1000, TRANSFER_END_TIME = 15*60*60*1000;

    private final long[] transferDateArr;		// 有序存储交易日数据（值为日期时间戳），便于后续计算 T+n 交易日

    // 传入交易日列表，排序后存入交易日数组
    public TransferDateUtil(List<Date> transferDates) {
        if (transferDates == null || transferDates.size() == 0) {
            throw new RuntimeException("Invalid Param: transferDates must not null and contiain element!");
        }

        Collections.sort(transferDates);
        this.transferDateArr = new long[transferDates.size()];

        for(int i = 0; i < transferDates.size(); i++) {
            if (transferDates.get(i) == null) {
                throw new RuntimeException("Invalid Param: transferDates must not contiain null element!");
            }
            transferDateArr[i] = transferDates.get(i).getTime() / DAY_TIME;
        }
    }

    // 计算 currentDate 的 T+n 交易日，返回 null 表示在交易日历范围内不能找到符合条件的交易日
    // 查询复杂度：log(n)，n 为交易日数量
    public Date nextTransferDate(Date currentDate, int n) {
        if (n < 0) {
            throw new RuntimeException("Invalid Param: n must gte 0!");
        }

        long currentTime = currentDate.getTime();
        if (TRANSFER_END_TIME < currentDate.getTime() % DAY_TIME) {				// 如果超过了结束时间，那么相当于从第二天开始计算 T+n 交易日
            currentTime += DAY_TIME;
        }

        int index = indexOfT0Date(currentTime / DAY_TIME);					// 二分查找寻找 T+0 交易日的索引
        if (index == -1 || index + n > transferDateArr.length - 1) {			// 给定交易日历范围内不能找到符合条件的交易日
            return null;
        }
        return new Date(transferDateArr[index + n] * DAY_TIME);
    }

    // 传入天数（时间戳 / DAY_TIME），找到当前日期的 T+0 交易日索引（即数组内第一个大于等于 days 的索引）
    private int indexOfT0Date(long days) {
        // 如果传入的日期已经超过交易日历最大范围，返回 -1，即找不到
        if (days > transferDateArr[transferDateArr.length - 1]) {
            return -1;
        }

        int l = 0, r = transferDateArr.length - 1;
        while(l < r) {
            int mid = l + (r-l)/2;
            if (transferDateArr[mid] == days) {
                return mid;
            } else if (transferDateArr[mid] < days) {
                l = mid+1;
            } else {
                r = mid;
            }
        }
        return l;
    }
}


