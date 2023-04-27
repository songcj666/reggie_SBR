package com.scj.reggie_take_out.entity.raytracer.objects;

import com.scj.reggie_take_out.entity.raytracer.scene.Intersection;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.Ray3;

import javax.vecmath.Vector3d;

public abstract class SceneObject {

    public abstract Intersection intersect(Ray3 ray);  //求交点
    public abstract Vector3d getNormalAt(Vector3d point);  //面的法向量
}
