package com.scj.reggie_take_out.utils;

public class DegreeToRad {
    public static double torad(double deg) {
        return deg/180* Math.acos(-1);
    }
}
