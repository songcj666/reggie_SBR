package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Rxdata;
import com.scj.reggie_take_out.entity.Txdata;
import com.scj.reggie_take_out.entity.raytracer.objects.Edge;
import com.scj.reggie_take_out.entity.raytracer.objects.Face;
import com.scj.reggie_take_out.entity.raytracer.objects.Triangle;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.Ray3;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.RayTracer;
import com.scj.reggie_take_out.entity.raytracer.sbrraytracer.SphereRayEmitter;
import com.scj.reggie_take_out.entity.raytracer.scene.ArrivalResult;
import com.scj.reggie_take_out.entity.raytracer.scene.CollectBall;
import com.scj.reggie_take_out.entity.raytracer.scene.Intersection;
import com.scj.reggie_take_out.entity.secne_Info.FaceInfo;
import com.scj.reggie_take_out.entity.secne_Info.NormalInfo;
import com.scj.reggie_take_out.mapper.FaceInfoMapper;
import com.scj.reggie_take_out.mapper.NormalInfoMapper;
import com.scj.reggie_take_out.mapper.RxdataMapper;
import com.scj.reggie_take_out.mapper.TxdataMapper;
import com.scj.reggie_take_out.service.RayTracingService;
import com.scj.reggie_take_out.utils.EdgeDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.vecmath.Vector3d;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
public class RayTracingServiceImpl {

    @Autowired
    private FaceInfoMapper faceInfoMapper;
    @Autowired
    private NormalInfoMapper normalInfoMapper;

    @Autowired
    private TxdataMapper txdataMapper;

    @Autowired
    private RxdataMapper rxdataMapper;

    //返回存在一次反射的交点的坐标（二维数组形式）
    public List<Vector3d> rayTracer( List<List<Double>> vertices2, List<List<Double>> normalInfo ,Vector3d transmitter, Vector3d receiver) {
        //modelServiceImpl.updateLocation(model.getFlag(), model.getLongitude(), model.getLatitude());
        int N = 500;  //发射天线的数目
        Ray3[] ray3 = new Ray3[N]; //全向发射射线数组
        SphereRayEmitter emitter = new SphereRayEmitter(transmitter, N); //发射N条射线
//        Intersection[] intersectResult = new Intersection[N]; //判断每个射线与面相交结果的数组
        Ray3[] reflectRay1 = new Ray3[N]; //射线与面一次反射后的反射射线数组

        double delta = emitter.AngularInterval(); //相邻射线的角度间隔
        CollectBall collectBall = new CollectBall(receiver, 20); //接收球的设置 -->设置半径测试
//        CollectBall collectBall = new CollectBall(receiver, delta,transmitter); //接收球的设置 -->射线间隔*收发机之间的距离/2
        System.out.println("collectBall"+collectBall);
        ArrivalResult[] arrivalResult1 = new ArrivalResult[N]; //判断一次反射后的射线是否到达接收球的数组
        System.out.println("rays"+emitter.rayDirections());
        RayTracer rayTracer1 = new RayTracer();  //一次反射类
        List<Vector3d> intersections = new ArrayList<Vector3d>();
        //测试一条射线
//        Ray3 ray1 = new Ray3(transmitter,Math.PI/4,Math.PI*3/2);
//        Ray3 reflect1 = new Ray3(); //射线与面一次反射后的反射射线数组
//        reflect1 = rayTracer1.traceRay(vertices2, normalInfo, ray1, 2); //一次反射
//        System.out.println("交点坐标="+rayTracer1.intersections);

        for (int i = 0; i < N; i++) {
            ray3[i] = new Ray3(transmitter, emitter.rayDirections().get(i));
            //一次反射的情况
            reflectRay1[i] = rayTracer1.traceRay(vertices2, normalInfo, ray3[i], 2); //一次反射
            if (reflectRay1[i] != null) {   //存在反射时 判断反射射线是否到达接收球
                System.out.println("射线" + i + "与三角面相交并得到经一次反射的反射射线：" + reflectRay1[i]);
                arrivalResult1[i] = collectBall.intersect(reflectRay1[i]);
                if (arrivalResult1[i].description == "射线到达接收球！") {
                    System.out.println( "交点坐标"+rayTracer1.intersections);
                    intersections.add(rayTracer1.intersections);
                }
                System.out.println("射线" + i + "经过一次反射后是否到达接收球：" + arrivalResult1[i]);
            }
        }
        if(intersections != null){
            return intersections;
        }

        return null; //未到达接收球 返回null
    }

    public List<Face> constructFaces(List<List<Double>> vertices2, List<List<Double>> normalInfo) {
        // 每两个vertices2的元素组成一个平面
        List<Triangle> triangles = new ArrayList<>();
        List<Vector3d> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < normalInfo.size(); i++) {
            List<Double> v1 = vertices2.get(3 * i);
            Vector3d v1d = new Vector3d(v1.get(0), v1.get(1), v1.get(2));
            List<Double> v2 = vertices2.get(3 * i + 1);
            Vector3d v2d = new Vector3d(v2.get(0), v2.get(1), v2.get(2));
            List<Double> v3 = vertices2.get(3 * i + 2);
            Vector3d v3d = new Vector3d(v3.get(0), v3.get(1), v3.get(2));

            triangles.add(new Triangle(v1d, v2d, v3d));

            List<Double> n1 = normalInfo.get(i);
            normals.add(new Vector3d(n1.get(0), n1.get(1), n1.get(2)));
        }

//        System.out.println("三角面的个数" + triangles.size());


        for (int j = 0; j < triangles.size(); j = j+2) {
            List<Triangle> tmp = new ArrayList<>();
//            System.out.println(triangles.get(j).toString());
//            System.out.println(triangles.get(j+1).toString());
            tmp.add(triangles.get(j));
            tmp.add(triangles.get(j+1));

            Face face = new Face(tmp, normals.get(j));
            faces.add(face);
        }
//        System.out.println(faces.get(0).triangles.get(0).p1);
        return faces;
    }

    //从数据库中获取对应接收机机的信息
    public Rxdata getRxPosition(String rxName) {
        Rxdata rxPosition = rxdataMapper.getRxPosition(rxName);
        if (rxPosition == null) {
            return null;
        }
        return rxPosition;
    }

    //从数据库中获取对应发射机的信息
    public Txdata getTxPosition(String txName) {
        Txdata txPosition = txdataMapper.getTxPosition(txName);
        if (txPosition == null) {
            return null;
        }
        return txPosition;
    }

    public List<List<Double>> getVertexInfo() {
        List<FaceInfo> faceInfo = faceInfoMapper.getFaceInfo();
        List<List<Double>> vertexList = new ArrayList<>();
        for (FaceInfo f : faceInfo) {
            List<Double> l1 = new ArrayList<>();
            List<Double> l2 = new ArrayList<>();
            List<Double> l3 = new ArrayList<>();
            l1.add(f.getV1X());
            l1.add(f.getV1Y());
            l1.add(f.getV1Z());
            l2.add(f.getV2X());
            l2.add(f.getV2Y());
            l2.add(f.getV2Z());
            l3.add(f.getV3X());
            l3.add(f.getV3Y());
            l3.add(f.getV3Z());
            vertexList.add(l1);
            vertexList.add(l2);
            vertexList.add(l3);
        }
        return vertexList;
    }


    public List<List<Double>> getNormalInfo() {
        List<NormalInfo> normalInfo = normalInfoMapper.getNormalInfo();
        List<List<Double>> normalList = new ArrayList<>();
        for (NormalInfo n : normalInfo) {
            List<Double> l1 = new ArrayList<>();
            l1.add(n.getN1());
            l1.add(n.getN2());
            l1.add(n.getN3());
            normalList.add(l1);
        }
        return normalList;
    }
}