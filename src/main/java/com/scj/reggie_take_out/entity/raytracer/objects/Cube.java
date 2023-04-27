package com.scj.reggie_take_out.entity.raytracer.objects;

import com.scj.reggie_take_out.utils.EdgeDetector;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

public class Cube {
    private List<Edge> cubeEdges; // 组成正方体的边列表
    private List<Vector3d> cubeVertexs; // 组出正方体的顶点列表
    private List<Face> cubeFaces; // 组成正方体的面列表

    // 去除正方体的重复棱
    public List<Edge> removeDuplicateEdges(List<Edge> edges) {
        List<Edge> newEdges = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            Edge tmp = edges.get(i);
            for (int j = 0; j < edges.size(); j++) { // 确保相同位置的元素不被访问到
                if (i == j) {
                    continue;
                }
                if (tmp.checkEdge(tmp, edges.get(j))) {
                    // 说明找到了共享的边，构建新的结构存在
                    newEdges.add(new Edge(tmp.v1, tmp.v2, tmp.f1, edges.get(j).f1));
                    edges.remove(j);
                }
            }
        }

        return newEdges;
    }
}
