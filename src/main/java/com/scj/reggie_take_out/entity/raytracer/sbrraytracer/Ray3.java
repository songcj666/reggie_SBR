package com.scj.reggie_take_out.entity.raytracer.sbrraytracer;

import javax.vecmath.Vector3d;

public class Ray3 {
    public Vector3d position;//发射点的位置
    public double theta; //仰角 [0,pi]
    public double fai; //方位角 [0,2*pi]
    public Vector3d rayVec; //射线的方向单位向量

    public Ray3() {
    }

    //通过仰角和方位角确定射线
    public Ray3(Vector3d position, double theta, double fai) {
        this.position = new Vector3d(position);
        this.theta = theta;
        this.fai = fai;
        double x = Math.sin(this.theta) * Math.cos(this.fai);
        double y = Math.sin(this.theta) * Math.sin(this.fai);
        double z = Math.cos(this.theta);
        this.rayVec = new Vector3d(x, y, z);
    }

    //通过方向向量确定射线
    public Ray3(Vector3d position, Vector3d direction) {
        direction.normalize();
        this.position = position;
        this.rayVec = direction;
    }

    public String toString() {
        return "position: " + position  + " direction: " + rayVec;
    }
}
