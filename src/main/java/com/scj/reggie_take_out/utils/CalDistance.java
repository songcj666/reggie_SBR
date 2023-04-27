package com.scj.reggie_take_out.utils;

import com.scj.reggie_take_out.entity.CVector;

import javax.vecmath.Vector3d;
import java.text.DecimalFormat;

public class CalDistance {

    /** 长半径a=6378137 */
    private static final double a = 6378137;
    /** 短半径b=6356752.314245 */
    private static final double b = 6356752.314245;
    /** 扁率f=1/298.257223563 */
    private static final double f = 1 / 298.257223563;
    /**
     * 根据两点的笛卡尔坐标，计算欧几里得距离
     * @param p1 点1的经纬度
     * @param p2 点2的经纬度
     * @return 距离
     */
    public static double getEuclideanDistance(CVector p1, CVector p2) {
        CVector q1 = WGS84ToWorld.wgs84ToWorld(p1);
        CVector q2 = WGS84ToWorld.wgs84ToWorld(p2);

        double x1 = q1.getVX();
        double y1 = q1.getVY();
        double z1 = q1.getVZ();
        double x2 = q2.getVX();
        double y2 = q2.getVY();
        double z2 = q2.getVZ();
        double result = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
        DecimalFormat format = new DecimalFormat("0.0000");
        double distance = Double.parseDouble(format.format(result));
        return distance;
    }

    /**
     * 根据两点经纬度坐标，由半正矢公式计算直线距离
     * <p>
     * S = 2arcsin√sin²(a/2)+cos(lat1)*cos(lat2)*sin²(b/2)￣*6378.137
     * <p>
     * 1. lng1 lat1 表示A点经纬度，lng2 lat2 表示B点经纬度；<br>
     * 2. a=lat1 – lat2 为两点纬度之差  b=lng1 -lng2 为两点经度之差；<br>
     * 3. 6378.137为地球赤道半径，单位为千米；
     *
     * @param p1 点1的经纬度
     * @param p2 点2的经纬度
     * @return 距离，单位米(M)
     */
    public static double getDistanceFrom2LngLat(CVector p1, CVector p2) {
        double lng1 = p1.getVX();
        double lat1 = p1.getVY();
        double h1 = p1.getVZ();
        double lng2 = p2.getVX();
        double lat2 = p2.getVY();
        double h2 = p2.getVZ();
        //将角度转化为弧度
        double radLng1 = DegreeToRad.torad(lng1);
        double radLat1 = DegreeToRad.torad(lat1);
        double radLng2 = DegreeToRad.torad(lng2);
        double radLat2 = DegreeToRad.torad(lat2);

        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;

        double dis = 2 * Math.asin(Math.sqrt(Math.sin(a / 2) * Math.sin(a / 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(b / 2) * Math.sin(b / 2))) * 6378137;
        dis = Math.sqrt(Math.pow(dis, 2) + Math.pow(h1-h2, 2));
        DecimalFormat format = new DecimalFormat("0.0000");
        dis = Double.parseDouble(format.format(dis));
        return dis;
    }

    /**
     * 根据采用椭球体算法，比较著名的算法有Vincenty方案算法提供的经纬度计算两点间距离
     *
     * @param p1 点1的经纬度
     * @param p2 点2的经纬度
     * @return 两点间距离
     */
    public static double getVincentyDistance(CVector p1, CVector p2)
    {
        double lng1 = p1.getVX();
        double lat1 = p1.getVY();
        double h1 = p1.getVZ();
        double lng2 = p2.getVX();
        double lat2 = p2.getVY();
        double h2 = p2.getVZ();
        double L = DegreeToRad.torad(lng1 - lng2);
        double U1 = Math.atan((1 - f) * Math.tan(DegreeToRad.torad(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(DegreeToRad.torad(lat2)));
        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1),
                sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
        double lambda = L, lambdaP = Math.PI;
        double cosSqAlpha = 0L, sinSigma = 0L, cos2SigmaM = 0L, cosSigma = 0L, sigma = 0L;
        int circleCount = 40;
        //迭代循环
        while (Math.abs(lambda - lambdaP) > 1e-12 && --circleCount > 0) {
            double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) +
                    (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0) {
                return 0;  // co-incident points
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            double alpha = Math.asin(cosU1 * cosU2 * sinLambda / sinSigma);
            cosSqAlpha = Math.cos(alpha) * Math.cos(alpha);
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * Math.sin(alpha) *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        }
        if (circleCount == 0) {
            return -1;  // formula failed to converge
        }
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
                B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

        double result = b * A * (sigma - deltaSigma);
        result = Math.sqrt(Math.pow(result, 2) + Math.pow(h1-h2, 2));
        DecimalFormat format = new DecimalFormat("0.0000");
        double distance = Double.parseDouble(format.format(result));
        return distance;
    }

    public static double getDisFrom2Point(Vector3d a, Vector3d b, Vector3d p) {
        // 计算向量ab的坐标
        double[] ab = {b.x - a.x, b.y - a.y, b.z - a.z};
        // 计算向量ap的坐标
        double[] ap = {p.x - a.x, p.y - a.y, p.z - a.z};
        // 计算向量 ab × ap 的坐标表示
        double[] xProduct = new double[3];
        xProduct[0] = ab[1] * ap[2] - ab[2] * ap[1];
        xProduct[1] = ab[2] * ap[0] - ab[0] * ap[2];
        xProduct[2] = ab[0] * ap[1] - ab[1] * ap[0];
        // 计算叉积向量的模
        double m = Math.sqrt(xProduct[0] * xProduct[0] + xProduct[1] * xProduct[1] + xProduct[2] * xProduct[2]);
        // 计算向量ab的模
        double l = Math.sqrt(ab[0]*ab[0] + ab[1]*ab[1] + ab[2]*ab[2]);
        DecimalFormat format = new DecimalFormat("0.0000");
        double dis = Double.parseDouble(format.format(m/l));
        return  dis;
    }

//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
////        CVector p1 = new CVector(116.499002221111, 29.528702221111, 0);
////        CVector p2 = new CVector(116.499002221111, 29.528747221111, 0);
////
////        double d = CalDistance.getEuclideanDistance(p1, p2);
////        double dis = CalDistance.getDistanceFrom2LngLat(p1, p2);
////        double distance = CalDistance.getVincentyDistance(p1, p2);
////        System.out.println(d);
////        System.out.println(dis);
////        System.out.println(distance);
//
//        Vector3d a = new Vector3d(0, 0, 0);
//        Vector3d b = new Vector3d(1, 1, 0);
//        Vector3d p = new Vector3d(0, 4, 0);
//        System.out.println(CalDistance.getDisFrom2Point(a, b, p));
//
//    }
}

