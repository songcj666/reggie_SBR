package com.scj.reggie_take_out.utils;

import com.scj.reggie_take_out.entity.CVector;

public class WGS84ToWorld {
//    public static void main(String[] args) {
//        CVector test = new CVector(116.50044722995334, 29.523262139680185, 20);
////        CVector test2 = new CVector(4966842.334334348, 3142998.974015908, -2476424.6449757097);
//        CVector test3 = new CVector(-2476432.022061037, 4966857.130189245, 3143008.3367767176);
//        System.out.println(Wgs84ToWorld.WGS84ToWorld(test).toString());
//        System.out.println(Wgs84ToWorld.WorldToWGS84(test3).toString());
////        System.out.println(Wgs84ToWorld.WorldToWGS84(test3).toString());
//    }
    //设置地球半径
    private static final double WGS_84_RADIUS_EQUATOR = 6378137.0;
//    private static final double PP= 6356752.3142451793;
    //经纬度转立体坐标
    public static CVector wgs84ToWorld(CVector _wgscood) {
        double x = _wgscood.getVX();
        double y = _wgscood.getVY();
        double z = _wgscood.getVZ();
        if (z == 0) {
            z = 1;
        }
        z += WGS_84_RADIUS_EQUATOR;
//        z += PP;
        double tempz = z * Math.sin(DegreeToRad.torad(y));
        double tempy = z * Math.cos(DegreeToRad.torad(y)) * Math.sin(DegreeToRad.torad(x));
        double tempx = z * Math.cos(DegreeToRad.torad(y)) * Math.cos(DegreeToRad.torad(x));
        return new CVector(tempx, tempy, tempz);
    }
    /*public static CVector lnglatToxyz(double lng,double lat) {
        double x = 0;
        double y = 0;
        double z = 0;
        double R = PP;
        double lng_deg = torad(lng);
        double lat_deg = Math.abs(torad(lat));
        // 计算y坐标
        if (lat >= 0 && lat <= 90) {
            y = R * Math.sin(lat_deg);
        } else if(lat >= -90 && lat < 0) {
            y = -R * Math.sin(lat_deg);
        }

        // 计算x、z坐标
        if (0 < lng && lng <= 90) {
            x = - R * Math.cos(lat_deg) * Math.cos(lng_deg);
            z = R * Math.cos(lat_deg) * Math.sin(lng_deg);
        } else if(90 < lng && lng <= 180) {
            x = R * Math.cos(lat_deg) * Math.cos(torad(180-lng));
            z = R * Math.cos(lat_deg) * Math.sin(torad(180-lng));
        } else if(-90 < lng && lng <= 0) {
            x = -R * Math.cos(lat_deg) * Math.cos(-lng_deg);
            z = -R * Math.cos(lat_deg) * Math.sin(-lng_deg);
        } else if(-180 <= lng && lng <= -90) {
            x = R * Math.cos(lat_deg) * Math.cos(torad(180+lng));
            z = -R * Math.cos(lat_deg) * Math.sin(torad(180+lng));
        }
        return new CVector(x, y, z);
    }*/

}
