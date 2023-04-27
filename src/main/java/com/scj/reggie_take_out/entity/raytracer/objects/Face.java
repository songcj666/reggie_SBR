package com.scj.reggie_take_out.entity.raytracer.objects;


import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

public class Face {

    public Vector3d v1;
    public Vector3d v2;
    public Vector3d v3;

    public Vector3d v4;

    public List<Triangle> triangles; // 组成面的三角面信息

    public Vector3d normal; // 面的法向量信息

    private List<Edge> edges; // 面的棱边信息

    public Face(List<Triangle> triangles, Vector3d normal) {
        this.triangles = triangles;
        this.normal = normal;
    }

    public Face(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, Vector3d normal) {
        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(v1, v2, v3));
        triangles.add(new Triangle(v1, v3, v4));
        this.triangles = triangles;
        this.normal = normal;
    }

//    @Override
//    public String toString() {
//        return "面的三个顶点为 = " + "{" + triangles.get(0) + v1.getY() + v1.getZ() + "}" +
//                "法向量为 = " + normal.getX() + normal.getY() + normal.getZ();
//    }
}

