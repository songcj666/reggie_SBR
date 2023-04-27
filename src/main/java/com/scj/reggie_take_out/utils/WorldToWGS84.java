package com.scj.reggie_take_out.utils;

import com.scj.reggie_take_out.entity.CVector;

public class WorldToWGS84 {
    private static final double WGS_84_RADIUS_EQUATOR = 6378137.0;
    // 立体坐标转经纬度
    public static CVector worldToWGS84(CVector _worldcood) {
        double x = _worldcood.getVX();
        double y = _worldcood.getVY();
        double z = _worldcood.getVZ();
        double tempx;
        if (y >= 0) {
            tempx = Math.acos(x / Math.sqrt(y * y + x * x)) * 180 / Math.PI;
            tempx = tempx < 0 ? tempx + 180 : tempx;
        }
        else
        {
            tempx = Math.acos(x / Math.sqrt(y * y + x * x)) * 180 / Math.PI;
            tempx = tempx < 0 ? -(tempx + 180) : -tempx;
        }
        double sqrt = Math.sqrt(y * y + z * z + x * x);
        double tempy = Math.asin(z / sqrt) * 180 / Math.PI;
        tempy = tempy > 90 ? 90 - tempy : tempy;
        double tempz = sqrt - WGS_84_RADIUS_EQUATOR == 1.0 ? 0: (sqrt - WGS_84_RADIUS_EQUATOR);
        return new CVector(tempx, tempy, tempz);
    }
}
