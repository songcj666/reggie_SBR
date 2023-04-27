package com.scj.reggie_take_out.entity.raytracer.sbrraytracer;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

//Icosahedron subdivision算法
public class SphereRayEmitter {
    private final Vector3d position0; // 球心坐标-->发射点的坐标
    private final int numRays; // 射线数目
    private final List<Vector3d> directions; // 方向向量列表
    public SphereRayEmitter(Vector3d position0, int numRays) {
        this.position0 = position0;
        this.numRays = numRays;
        this.directions = new ArrayList<>(numRays);
        generateDirections();
    }
    private void generateDirections() {
        //Icosahedron subdivision算法
        double inc = Math.PI * (3 - Math.sqrt(5)); // 均匀分布角度
        double offset = 2.0 / numRays;
        for (int i = 0; i < numRays; i++) {
            double y = i * offset - 1 + (offset / 2);
            double r = Math.sqrt(1 - y * y);
            double phi = i * inc;
            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;
            directions.add(new Vector3d(x,y,z)); //单位向量
        }
    }
   public  List<Vector3d> rayDirections(){
        return this.directions;
   }
   //得到相邻射线的角度间隔
   public double AngularInterval(){
        return 2 * Math.asin(1 / Math.sqrt(numRays));
   }

}
