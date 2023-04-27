package com.scj.reggie_take_out.entity.raytracer.objects;

import com.scj.reggie_take_out.entity.raytracer.scene.Intersection;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.Ray3;

import javax.vecmath.Vector3d;

public class Triangle extends SceneObject {
    public Vector3d p1, p2, p3; // 三角形的三个顶点
    private Vector3d normal; // 三角形的法向量（根据顶点算的） -->TODO：得到三角面信息及其法向量
    private Vector3d n; // 判断点是否在三角形内的参考单位法向量
    private Vector3d p2mp1, p3mp2, p1mp3; // 三角形的三条边向量 -->假设向外

    public Triangle(Vector3d p1, Vector3d p2, Vector3d p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Triangle(Vector3d p1, Vector3d p2, Vector3d p3, Vector3d normal) {
        this.p1 = new Vector3d(p1);
        this.p2 = new Vector3d(p2);
        this.p3 = new Vector3d(p3);
        this.normal = new Vector3d(normal);
        this.normal.normalize();

        setTriangleAttrs();
    }

    private void setTriangleAttrs() {
        Vector3d v1 = new Vector3d(p2);
        v1.sub(p1);
        Vector3d v2 = new Vector3d(p3);
        v2.sub(p1);
        n = new Vector3d();
        n.cross(v1, v2);
        n.normalize();  //判断点是否在三角形内的参考单位法向量
        p2mp1 = new Vector3d(p2);
        p2mp1.sub(p1); //p1 --> p2

        p3mp2 = new Vector3d(p3);
        p3mp2.sub(p2); //p2 --> p3

        p1mp3 = new Vector3d(p1);
        p1mp3.sub(p3);  //p3 --> p1
    }

    public Vector3d getNormalAt(Vector3d point) {
        return new Vector3d(normal);
    }

    //判断交点是否在三角形内
    public boolean pointBelongs(Vector3d point) {
        //判断边p1p2
        Vector3d aux = new Vector3d(point);
        aux.sub(p1);  //p1-->交点
        aux.cross(p2mp1, aux);  //求p1p2与p1交点的叉积
        if (aux.dot(n) < 0) {
            return false;
        }
        //判断边p2p3
        aux = new Vector3d(point);
        aux.sub(p2);  //p2-->交点
        aux.cross(p3mp2, aux);  //求p2p3与p2交点的叉积
        if (aux.dot(n) < 0) {
            return false;
        }
        //判断边p3p1
        aux = new Vector3d(point);
        aux.sub(p3);  //p3-->交点
        aux.cross(p1mp3, aux);  //求p3p1与p3交点的叉积
        if (aux.dot(n) < 0) {
            return false;
        }

        return true;
    }

    public Intersection intersect(Ray3 ray) {
        Vector3d normal = new Vector3d(this.normal);
        //背面采集判断
        if (normal.dot(ray.rayVec) < 0) {
            Vector3d a = new Vector3d(p1);
            a.sub(ray.position);
            double t =  a.dot(normal) / normal.dot(ray.rayVec);  //记录射线与交点在射线方向上的距离
            if (t < 0) {
                return null;  //与面没有交点
            }
            Vector3d pointOfIntersection = new Vector3d(ray.rayVec);
            pointOfIntersection.scale(t);
            pointOfIntersection.add(ray.position); //得到与三角面的交点
            //判断交点是否在三角形内
            if (pointBelongs(pointOfIntersection)) {
                Intersection i = new Intersection(this,pointOfIntersection,normal,t);//创建一个新的碰撞对象
                return i;
            } else {
                return null;  //与面没有交点
            }
        }

        return null;  //与面没有交点

    }
    public String toString() {
        return "Triangle(p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ", normal="
                + normal + ")";
    }

}
