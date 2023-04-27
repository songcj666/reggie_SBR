package com.scj.reggie_take_out.entity.raytracer.scene;

import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.Ray3;
import com.scj.reggie_take_out.entity.raytracer.scene.ArrivalResult;

import javax.vecmath.Vector3d;

//接收球的定义类
public class CollectBall {
    public Vector3d center;
    public double radius;

    public CollectBall(Vector3d center, double delta, Vector3d transmitter) {
        this.center = center;
        this.radius = properRadius(delta, transmitter); //delta指的是发射射线的角度间距（弧度制）
    }
    public CollectBall(Vector3d center,  double radius) {
        this.center = center;
        this.radius = radius; //delta指的是发射射线的角度间距（弧度制）
    }
    //获取合适的接收球半径
    public double properRadius(double delta, Vector3d transmitter) {
        Vector3d aux = new Vector3d(this.center);
        aux.sub(transmitter); //发射源-->接收点的向量
        double d = aux.length();  //发射点到接收点的距离
        return delta * d / 2;
    }

    //判断射线是否到达接收球
    public ArrivalResult intersect(Ray3 ray) {
        ArrivalResult arrivalResult = new ArrivalResult();

        Vector3d L = new Vector3d(this.center);
        L.sub(ray.position); //射线起始点->接收球中心点的向量
        double toc = L.dot(ray.rayVec); //射线起始点->接收球中心点在射线方向的投影长度
        double l = L.length(); //射线->接收球中心的长度
        double d = Math.sqrt(Math.pow(l, 2) - Math.pow(toc, 2)); //接收球中心点到射线的垂直距离
        //如果距离小于半径 则到达接收球
        if (radius - d > 0.000001) {
            double tpc = Math.sqrt(Math.pow(radius, 2) - Math.pow(d, 2)); //交点->接收球中心点在射线方向上的长度
            double t1 = toc - tpc; //射线起始点->第一个交点在射线方向上的长度
            double t2 = toc + tpc; //射线起始点->第二个交点在射线方向上的长度
            //确定发射点距离接收球最近的交点
            //情况1：射线方向与接收球不在同一侧
            if (t1 < 0 && t2 < 0) { //与0相减的绝对值在某个精度范围类判断
                return arrivalResult.NoArrival();  //两个都为负数，则说明射线与接收球无交点
            } else if (t1 < 0 || t2 < 0) {
                //情况2： 发射点在接收球内
                return arrivalResult.innerBall();
            } else {
                //情况3： 发射点在接收球外
                Vector3d r = new Vector3d(ray.rayVec);
                r.scale(Math.min(t1, t2));
                r.add(ray.position);
                arrivalResult.position = r; //最近交点的位置
                arrivalResult.distance = Math.min(t1, t2);
                return arrivalResult;
            }
        } else {
            //射线未达到接收球（d=radius 默认是没有到达接收球）
            return arrivalResult.NoArrival();
        }
    }

    @Override
    public String toString() {
        return "CollectBall{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}

