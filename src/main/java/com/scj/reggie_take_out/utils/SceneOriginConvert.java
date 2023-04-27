package com.scj.reggie_take_out.utils;

import java.util.ArrayList;
import java.util.List;

public class SceneOriginConvert {

    // Pink:  longitude: 116.499002221111, latitude: 29.528702221111

    public static List<Double> LonLatToOrigin(double lon, double lat, double h) {
        List<Double> doubles = new ArrayList<>();
        doubles.add((lon - 116.499002221111) / 0.00001 + 5);
        doubles.add(((lat - 29.528702221111) / 0.00001) * 1.1 + 5);
        doubles.add(h);
        return doubles;
    }

    public static List<Double> OriginToLonLat(double x, double y, double h) {
        List<Double> doubles = new ArrayList<>();
        doubles.add(((x-5) * 0.0000103) + 116.499002221111);
//        doubles.add(((y-5) * 0.000009) + 29.528702221111);
        doubles.add(((y-5) * 0.00001) + 29.528702221111);
        doubles.add(h);
        return doubles;
    }
}
