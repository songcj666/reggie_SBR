package com.scj.reggie_take_out.entity.raytracer.objects;

import javax.vecmath.Vector3d;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edge {

    // 组成边的两个顶点
    public Vector3d v1;
    public Vector3d v2;

    // 边所在的面
    public Face f1;
    public Face f2;
    public Edge() {}

    // 不包含面信息的构造函数
    public Edge(Vector3d v1, Vector3d v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    // 包含面信息的构造函数，此时构造的边处于平面中，所以只考虑一个面
    public Edge(Vector3d v1, Vector3d v2, Face f1) {
        this.v1 = v1;
        this.v2 = v2;
        this.f1 = f1;
    }

    // 包含面信息的构造函数，此时构造的边处于立体几何中，考虑两个面
    public Edge(Vector3d v1, Vector3d v2, Face f1, Face f2) {
        this.v1 = v1;
        this.v2 = v2;
        this.f1 = f1;
        this.f2 = f2;
    }

    // 用于判断重复的边，例如 点a--->点b 与 点b--->点a 虽然方向不同，但在处理是被认为是重复的边。
    // 如果是重复的边：返回true
    // 如果不是重复的边：返回false
    public static boolean checkEdge(Edge edge1, Edge edge2) {
        if (edge1 == null || edge2 == null) {
            return  false;
        }

        if (edge1.v1.x == edge2.v1.x && edge1.v1.y == edge2.v1.y && edge1.v1.z == edge2.v1.z &&
                edge1.v2.x == edge2.v2.x && edge1.v2.y == edge2.v2.y && edge1.v2.z == edge2.v2.z)  {
            return true;
        } else if (edge1.v1.x == edge2.v2.x && edge1.v1.y == edge2.v2.y && edge1.v1.z == edge2.v2.z &&
                edge1.v2.x == edge2.v1.x && edge1.v2.y == edge2.v1.y && edge1.v2.z == edge2.v1.z) {
            return true;
        }
        return false;
    }

    // 将处于同一条直线上的线段进行合并，得到新的顶点组成的棱边信息
    public static Edge removeDuplicateVertices(List<Edge> edges) {
        HashMap<Vector3d, Integer> vertexMap = new HashMap<>();
        for (Edge edge : edges) {
            vertexMap.put(edge.v1, vertexMap.getOrDefault(edge.v1, 0) + 1);
            vertexMap.put(edge.v2, vertexMap.getOrDefault(edge.v2, 0) + 1);
        }

        Edge newEdge = new Edge();
        for (Map.Entry<Vector3d, Integer> entry : vertexMap.entrySet()) {
            if (entry.getValue() == 1) {
                // 说明该点是边的一个顶点
                if (newEdge.v1 == null) {
                    newEdge.v1 = entry.getKey();
                } else {
                    newEdge.v2 = entry.getKey();
                }
            }
        }
        newEdge.f1 = edges.get(0).f1;
        return newEdge;
    }

    @Override
    public String toString() {
        return "Edge: " + "(" + v1.x + "," + v1.y + "," + v1.z + ") ---> (" + v2.x + "," + v2.y + "," + v2.z + ")";
    }
}
