package com.scj.reggie_take_out.entity.raytracer.objects;

import java.util.Arrays;

public class STLFile {
    int max;// 第一个三角片的z高度
    int facetNum;// 三角片数量
    double alpha;
    boolean hasColors = false;
    double[] vertices;// 点 length=[faces * 3 * 3]  在坐标轴中的单位为mm，一共是2m*2m
    double[] normals;// 三角面片法向量的3个分量值数据length=[faces * 3 * 3]
    double[] colors = null;// 点[r,g,b]

    public STLFile(int max, int facetNum, double alpha, boolean hasColors, double[] vertices, double[] normals,
                   double[] colors) {
        this.max = max;
        this.facetNum = facetNum;
        this.alpha = alpha;
        this.hasColors = hasColors;
        this.vertices = vertices;
        this.normals = normals;
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "STLFile{" + "\n" +
                "   max=" + max + ", \n" +
                "   facetNum=" + facetNum + ", \n" +
                "   alpha=" + alpha + ", \n" +
                "   hasColors=" + hasColors + ", \n" +
                "   vertices=" + Arrays.toString(vertices) + ", \n" +
                "   normals=" + Arrays.toString(normals) + ", \n" +
                '}';
    }

    public int getMax() {
        return max;
    }

    public int getFacetNum() {
        return facetNum;
    }

    public double getAlpha() {
        return alpha;
    }

    public boolean isHasColors() {
        return hasColors;
    }

    public double[] getVertices() {
        return vertices;
    }

    public double[] getNormals() {
        return normals;
    }

    public double[] getColors() {
        return colors;
    }
}
