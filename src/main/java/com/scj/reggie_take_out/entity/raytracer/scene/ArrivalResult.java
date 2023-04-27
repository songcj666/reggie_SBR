package com.scj.reggie_take_out.entity.raytracer.scene;

import javax.vecmath.Vector3d;

public class ArrivalResult {
    public Vector3d position;    //到达接收球的交点的位置坐标--距离起始点最近的交点
    public double distance;    //射线起始点-->接收球交点的距离
    public String description;   //射线到达接收球的情况描述

    public ArrivalResult() {
        this.description = "射线到达接收球！";
        this.position = new Vector3d();
        this.distance = 0;
    }
    public ArrivalResult(String description) {
        this.description = description;
    }
    //若未到达接收球
    public ArrivalResult NoArrival(){
        return new ArrivalResult("射线未到达接收球！");
    }
    //若发射点在接收球的内部
    public ArrivalResult innerBall(){
        return new ArrivalResult("虚拟发射点在接收球的内部，即射线一定能到达接收球！");
    }

    @Override
    public String toString() {
        return "ArrivalResult{" +
                "description=" + description +
                ", position =" + position +
                ", distance=" + distance +
                '}';
    }
}
