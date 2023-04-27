package com.scj.reggie_take_out.entity;

import lombok.Data;

// 与经纬度相关的类
@Data
public class CVector {
    private String rxsName;
    private double VX; // 经度 或者 x坐标
    private double VY; // 纬度 或者 y坐标
    private double VZ; // 高度 或者 z坐标

    public CVector(double VX, double VY, double VZ) {
        this.VX = VX;
        this.VY = VY;
        this.VZ = VZ;
    }

    public CVector() {
    }

    public double getVX() {
        return VX;
    }

    public double getVY() {
        return VY;
    }

    public double getVZ() {
        return VZ;
    }

    public void setRxsName(String rxsName) {
        this.rxsName = rxsName;
    }

    @Override
    public String toString() {
        return "CVector{" +
                "VX=" + VX +
                ", VY=" + VY +
                ", VZ=" + VZ +
                '}';
    }
}
