package com.scj.reggie_take_out.utils;

public class CheckOval {
    /**
     *
     * @param x 需要判定的点的x坐标
     * @param y 需要判定的点的y坐标
     * @param a 椭圆的长半轴
     * @param b 椭圆的短半轴
     * @return true : 在椭圆内部，； false ： 不在椭圆内部
     */
    public static boolean isOval(double x, double y, double a, double b) {
        // 点（x, y) 在椭圆内
        return (Math.pow((x - a), 2) / Math.pow(a, 2)) + (Math.pow((y - b), 2) / Math.pow(b, 2)) <= 1;
    }

}
