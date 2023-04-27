package com.scj.reggie_take_out.entity.raytracer.sbrraytracer;


import com.scj.reggie_take_out.entity.raytracer.scene.Intersection;
import com.scj.reggie_take_out.entity.raytracer.objects.TriangleSet;
import com.scj.reggie_take_out.service.impl.RayTracingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;


//追踪反射射线
public class RayTracer {
    private static final int MAX_DEPTH = 2; //追踪的最大深度
//    public  List<Vector3d> intersections;  //存放交点的信息
    public  Vector3d  intersections;  //存放一次反射的交点的信息

    public RayTracer() {
//        this.intersections = new ArrayList<Vector3d>();
    }

    //一次反射得到反射射线 -->形参中删除了SceneObject intersectedObject
    public static Ray3 reflectRay(Ray3 ray, Intersection intersection) {
        Vector3d direction = new Vector3d(intersection.normal);
        double aux = direction.dot(ray.rayVec) * -1;
        direction.scale(2 * aux);
        direction.add(ray.rayVec);
        return new Ray3(intersection.point, direction);
    }

    //递归得到经过n次反射后的反射射线
    public  Ray3 traceRay( List<List<Double>> vertices2, List<List<Double>> normalInfo, Ray3 ray,int depth){
        if(depth > MAX_DEPTH){
            return ray;
        }
        //创建场景中的物体 -->目前是每次追踪 都针对场景中所有的三角面 TODO：减少三角面的追踪
//        RayTracingServiceImpl rayTracingService = new RayTracingServiceImpl();

        TriangleSet triangles = new TriangleSet();
        for (int i = 0; i < normalInfo.size(); i++) {
            List<Double> v1 = vertices2.get(3 * i);
            Vector3d v3d1 = new Vector3d(v1.get(0), v1.get(1), v1.get(2));
            List<Double> v2 = vertices2.get(3 * i + 1);
            Vector3d v3d2 = new Vector3d(v2.get(0), v2.get(1), v2.get(2));
            List<Double> v3 = vertices2.get(3 * i + 2);
            Vector3d v3d3 = new Vector3d(v3.get(0), v3.get(1), v3.get(2));
            List<Double> n1 = normalInfo.get(i);
            Vector3d n3d = new Vector3d(n1.get(0), n1.get(1), n1.get(2));
            triangles.setTriangle(v3d1, v3d2, v3d3, n3d);
        }
//        System.out.println(triangles.toString());
        //检查射线与场景中物体的交点信息
        Intersection intersect = triangles.intersect(ray);
        if(intersect == null){
//            intersections.add(null);
            return null;
        }
        this.intersections=intersect.point;
        //得到反射射线
        Ray3 reflectRay = reflectRay(ray, intersect);

        return traceRay(vertices2, normalInfo, reflectRay,depth+1); //递归追踪
    }

    @Override
    public String toString() {
        return "intersections: " + intersections ;
    }
}
