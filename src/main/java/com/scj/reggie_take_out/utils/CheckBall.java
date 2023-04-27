package com.scj.reggie_take_out.utils;

public class CheckBall {
    /**
     *
     * @param x 需要判定的x坐标
     * @param y 需要判定的y坐标
     * @param h 需要判定的z坐标
     * @param a 圆心x坐标
     * @param b 圆心y坐标
     * @param r 半径
     * @return true：在圆内部，false：不在圆内
     */
    public static boolean isBall(double x, double y,double h, double a, double b,double r) {
        // 点（x,y,h) 在圆内
        return Math.pow((x - a), 2)  + Math.pow((y - b), 2) + Math.pow(h, 2)<= Math.pow(r , 2) ;
    }

}
