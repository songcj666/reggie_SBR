package com.scj.reggie_take_out.utils;

import com.scj.reggie_take_out.entity.raytracer.objects.Cube;
import com.scj.reggie_take_out.entity.raytracer.objects.Edge;
import com.scj.reggie_take_out.entity.raytracer.objects.Face;
import com.scj.reggie_take_out.entity.raytracer.objects.Triangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.Vector3d;

@SuppressWarnings("all")
public class EdgeDetector {


    public static void main(String[] args) {
        // 面的列表，存储每一个面的信息
        List<Face> faces = new ArrayList<>();

        // 正方体的顶点信息
        Vector3d a = new Vector3d(0.0000, 0.0000, 0.0000);
        Vector3d b = new Vector3d(10.0000, 0.0000, 0.0000);
        Vector3d c = new Vector3d(10.0000, 10.0000, 0.0000);
        Vector3d d = new Vector3d(0.0000, 10.0000, 0.0000);
        Vector3d e = new Vector3d(0.0000, 0.0000, 20.0000);
        Vector3d f = new Vector3d(10.0000, 0.0000, 20.0000);
        Vector3d g = new Vector3d(10.0000, 10.0000, 20.0000);
        Vector3d h = new Vector3d(0.0000, 10.0000, 20.0000);

        // 正方体的法向量信息
        Vector3d n0 = new Vector3d(0, 0, -1);
        Vector3d n1 = new Vector3d(0, 0, 1);
        Vector3d n2 = new Vector3d(0, -1, 0);
        Vector3d n3 = new Vector3d(1, 0, 0);
        Vector3d n4 = new Vector3d(0, 1, 0);
        Vector3d n5 = new Vector3d(-1, 0, 0);

        // 构造正方体的六个面
        faces.add(new Face(a, b, c, d, n0));
        faces.add(new Face(e, f, g, h, n1));
        faces.add(new Face(e, a, b, f, n2));
        faces.add(new Face(f, b, c, g, n3));
        faces.add(new Face(g, c, d, h, n4));
        faces.add(new Face(h, d, a, e, n5));

        List<Edge> edges = EdgeDetector.extractTotalEdges(faces);
        for (int i = 0; i < edges.size(); i++) {
            System.out.println(edges.get(i).toString());
            System.out.println(edges.get(i).f1.normal.toString());
            System.out.println(edges.get(i).f2.normal.toString());
            System.out.println();
        }
    }

    // 根据组成正方体的面的列表，提取出所有的棱边
    public static List<Edge> extractTotalEdges(List<Face> faces) {
        List<Edge> totalFaceEdge = new ArrayList<>(); // 存放所有面的所有棱信息,包含重复的棱
        for (int i = 0; i < faces.size(); i++) {
//            System.out.println(faces.get(i).toString());
            // 迭代每一个面，提取面得到组成面的四个棱，放进totalFaceEdge中
            List<Edge> tmp = extractFaceEdges(faces.get(i));
            for (int j = 0; j < tmp.size(); j++) {
                totalFaceEdge.add(tmp.get(j));
            }
        }

        Cube cube = new Cube();
        List<Edge> edges = cube.removeDuplicateEdges(totalFaceEdge);
        return edges;
    }

    // 根据面的信息，提取出面的边缘
    public static List<Edge> extractFaceEdges(Face face) {
        // 键：边       值：该边出现的次数
        Map<Edge, Integer> edgeMap = new HashMap<>();

        // 存储所有的边
        List<Edge> edges = new ArrayList<>();
        List<Edge> soloEdge = new ArrayList<>();
        List<Vector3d> directionVector = new ArrayList<>();
        System.out.println();


        // 给每一个边计算出现的次数
        for (int i = 0; i < face.triangles.size(); i++) {
            Edge edge1 = new Edge(face.triangles.get(i).p1, face.triangles.get(i).p2, face);
            boolean flag1 = false;
            for (Edge edge : edges) {
                // 在列表中找到与edge1相等的边
                if (Edge.checkEdge(edge, edge1)) {
                    edgeMap.put(edge, edgeMap.get(edge)+1); // 改边的数目+1
                    flag1 = true;
                    break;
                }
            }
            // 判断是因为什么原因退出的循环
            if (!flag1) {
                // 没找到相等的边，在列表中和map中添加该边
                edges.add(edge1);
                edgeMap.put(edge1, 1);
            }



            Edge edge2 = new Edge(face.triangles.get(i).p2, face.triangles.get(i).p3, face);
            boolean flag2 = false;
            for (Edge edge : edges) {
                // 在列表中找到与edge2相等的边
                if (Edge.checkEdge(edge, edge2)) {
                    edgeMap.put(edge, edgeMap.get(edge)+1);
                    flag2 = true;
                    break;
                }
            }
            // 判断是因为什么原因退出的循环
            if (!flag2) {
                // 没找到相等的边，在列表中和map中添加该边
                edges.add(edge2);
                edgeMap.put(edge2, 1);
            }


            Edge edge3 = new Edge(face.triangles.get(i).p3, face.triangles.get(i).p1, face);
            boolean flag3 = false;
            for (Edge edge : edges) {
                // 在列表中找到与edge3相等的边
                if (Edge.checkEdge(edge, edge3)) {
                    edgeMap.put(edge, edgeMap.get(edge)+1);
                    flag3 = true;
                    break;
                }
            }
            // 判断是因为什么原因退出的循环
            if (!flag3) {
                // 没找到相等的边，在列表中和map中添加该边
                edges.add(edge3);
                edgeMap.put(edge3, 1);
            }
        }

        for (Map.Entry<Edge, Integer> entry : edgeMap.entrySet()) {
            // 将不被共享的边单独存起来
            if (entry.getValue() == 1) {
                Edge tmp = entry.getKey(); // 取出不被共享的边，计算方向向量
                double deltaX = tmp.v1.x - tmp.v2.x; // 计算 x的差值
                double deltaY = tmp.v1.y - tmp.v2.y; // 计算 y的差值
                double deltaZ = tmp.v1.z - tmp.v2.z; // 计算 z的差值
                Vector3d tmpVector = new Vector3d(deltaX, deltaY, deltaZ);

                // solo中的边与 direction中的方向向量一一对应
                soloEdge.add(tmp);
                directionVector.add(tmpVector);
            }
        }

        // 通过方向向量区分竖直 和 水平的边。水平和竖直是相对的，只是取名的一个叫法。
        ArrayList<Edge> hEdge = new ArrayList<>(); // 用来存储水平的边
        ArrayList<Edge> uEdge = new ArrayList<>(); // 用来存储竖直的边
        Vector3d consult = directionVector.get(0);

        for (int idx = 0; idx < directionVector.size(); idx++) {
            if (directionVector.get(idx).x * consult.x + directionVector.get(idx).y * consult.y + directionVector.get(idx).z * consult.z == 0) {
                // 方向向量积为0， 选取的边与参考的边垂直,将方向向量对应的边 添加进uEdge
                uEdge.add(soloEdge.get(idx));
            } else {
                hEdge.add(soloEdge.get(idx));
            }
        }

        // 从hEdge中恢复 完整的棱边
        // 以hEdge中的第一个边为参考边，计算其他所有边的点到该边的距离
        Edge consultEdge = hEdge.get(0);
        Vector3d A = consultEdge.v1;
        Vector3d B = consultEdge.v2;
        ArrayList<Edge> sameEdge1 = new ArrayList<>(); // 存处于同一条直线上的边
        ArrayList<Edge> diffEdge1 = new ArrayList<>(); // 存不处于同一条直线上的边
        for (Edge edge : hEdge) {
            Vector3d P1 = edge.v1;
            Vector3d P2 = edge.v2;
            if ((CalDistance.getDisFrom2Point(A, B, P1) <= 0.0001) && CalDistance.getDisFrom2Point(A, B, P2) <= 0.0001) {
                // 此时认为边在直线上
                sameEdge1.add(edge);
            } else {
                diffEdge1.add(edge);
            }
        }

        consultEdge = uEdge.get(0);
        A = consultEdge.v1;
        B = consultEdge.v2;
        ArrayList<Edge> sameEdge2 = new ArrayList<>(); // 存处于同一条直线上的边
        ArrayList<Edge> diffEdge2 = new ArrayList<>(); // 存不处于同一条直线上的边
        for (Edge edge : uEdge) {
            Vector3d P1 = edge.v1;
            Vector3d P2 = edge.v2;
            if ((CalDistance.getDisFrom2Point(A, B, P1) <= 0.0001) && CalDistance.getDisFrom2Point(A, B, P2) <= 0.0001) {
                // 此时认为边在直线上
                sameEdge2.add(edge);
            } else {
                diffEdge2.add(edge);
            }
        }
        // 将每一个共线边列表中的点进行去重，只留下端点组成的边
        List<Edge> faceEdges = new ArrayList<>();
        faceEdges.add(Edge.removeDuplicateVertices(sameEdge1));
        faceEdges.add(Edge.removeDuplicateVertices(sameEdge2));
        faceEdges.add(Edge.removeDuplicateVertices(diffEdge1));
        faceEdges.add(Edge.removeDuplicateVertices(diffEdge2));

        return faceEdges;
    }
}
