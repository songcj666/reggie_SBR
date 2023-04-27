package com.scj.reggie_take_out.entity.raytracer.scene;

import com.scj.reggie_take_out.entity.raytracer.objects.SceneObject;

import javax.vecmath.Vector3d;

public class Intersection {
    public SceneObject Object;//相交的物体
    public double distance;   //发射点-->交点的距离
    public Vector3d point; //交点的位置坐标
    public Vector3d normal;   //交点的法向量

    public Intersection() {
        Object = null;
        point = new Vector3d();
        normal = new Vector3d();
    }

    public Intersection(SceneObject Object, Vector3d point, Vector3d normal, double distance) {
        this.Object = Object;
        this.point = new Vector3d(point);
        this.normal = new Vector3d(normal);
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "Object=" + Object +
                ", distance =" + distance +
                ", point=" + point +
                ", normal=" + normal +
                '}';
    }

}
