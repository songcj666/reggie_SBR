package com.scj.reggie_take_out.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class STLParser {
    public static void main(String[] args) {
        try {
            File file = new File("example.stl");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("facet normal")) {
                    // 解析法向量信息
                } else if (line.startsWith("vertex")) {
                    // 解析顶点信息
                } else if (line.startsWith("endfacet")) {
                    // 一个三角形解析完成
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


