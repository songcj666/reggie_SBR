package com.scj.reggie_take_out.entity.raytracer.objects;

import com.scj.reggie_take_out.entity.raytracer.scene.Intersection;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.Ray3;
import com.scj.reggie_take_out.service.RayTracingService;
import com.scj.reggie_take_out.service.impl.RayTracingServiceImpl;
import com.scj.reggie_take_out.utils.STLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.vecmath.Vector3d;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TriangleSet extends SceneObject {
    public static TriangleSet triangleSet;
    private final Set<Triangle> triangles =new HashSet<>();

    //获取文件的三角面信息
    public STLFile getSTLFile(String path) {
        if (STLUtils.isBinary(path)) {
            STLFile sf = STLUtils.parseBinary(path);
            return sf;
        } else {
            STLFile sf = STLUtils.parseASCII(path);
            assert sf != null;
            return sf;
        }
    }

    //返回三角面集合存在交点point的三角面法向量
    public Vector3d getNormalAt(Vector3d point) {
        for (Triangle t : triangles) {
            if (t.pointBelongs(point)) {
                return t.getNormalAt(point);
            }
        }
        return null;
    }

    //返回距离射线发射点的最近交点
    @Override
    public Intersection intersect(Ray3 ray) {
        double currentDistance, nearestDistance = Double.MAX_VALUE;
        Intersection nearestIntersection = null;

        for (Triangle t : triangles) {
            Intersection currentIntersection = t.intersect(ray);
            if (currentIntersection == null) {
                continue;
            }

            Vector3d aux = new Vector3d(currentIntersection.point);  //todo:断点打到这里得到的是（0,0,0）
            aux.sub(ray.position);
            currentDistance = aux.length(); //交点到源点的距离

            //找到集合中距离源点最近的交点
            if (nearestIntersection == null
                    || currentDistance < nearestDistance) {
                nearestIntersection = currentIntersection;
                nearestDistance = currentDistance;
            }
        }

        return nearestIntersection; //最终返回与三角面集合判交后的交点信息
    }

    //在hashset中加入三角形
    public void setTriangle(Vector3d p1, Vector3d p2, Vector3d p3, Vector3d normal) {
        Triangle t = new Triangle(p1, p2, p3, normal);
        //System.out.println("Adding triangle to TriangleSet: " + t);
        triangles.add(t);
    }

    @Override
    public String toString() {
        return "TriangleSet{" +
                "triangles=" + triangles +
                '}';
    }
}
