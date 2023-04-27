package com.scj.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Rxdata;
import com.scj.reggie_take_out.entity.Txdata;
import com.scj.reggie_take_out.entity.raytracer.objects.Edge;
import com.scj.reggie_take_out.entity.raytracer.objects.Face;
import com.scj.reggie_take_out.entity.raytracer.objects.Triangle;
import com.scj.reggie_take_out.entity.raytracer.objects.TriangleSet;
import com.scj.reggie_take_out.entity.secne_Info.FaceInfo;
import com.scj.reggie_take_out.service.impl.RayTracingServiceImpl;
import com.scj.reggie_take_out.service.impl.RxdataServiceImpl;
import com.scj.reggie_take_out.service.impl.TxdataServiceImpl;
import com.scj.reggie_take_out.utils.EdgeDetector;
import com.scj.reggie_take_out.utils.SceneOriginConvert;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.vecmath.Vector3d;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class RayTracingController {

    private static final Double SCENE_TX_LONG = 116.498952221111;
    private static final Double SCENE_TX_LAT = 29.528656771111;

    @Autowired
    private RayTracingServiceImpl raytracingServiceImpl;

    @GetMapping("/getposition")
    public Result getTxRxPosition(String txName,String rxName){
        //从数据库中获取对应发射机的信息
        Rxdata rxPosition = raytracingServiceImpl.getRxPosition(rxName);
        Txdata txPosition = raytracingServiceImpl.getTxPosition(txName);

        if (rxPosition == null || txPosition == null) {
            return Result.respon(1102, "发射机或接收机名称不存在!", 0L, null);
        }

        // 创建两个临时列表存放发射机和接收机的经纬度
        List<List<Double>> res = new ArrayList<>();
        List<Double> tmp1 = new ArrayList<>();
        List<Double> tmp2 = new ArrayList<>();
        tmp1.add(txPosition.getLongitude());
        tmp1.add(txPosition.getLatitude());
        tmp1.add(txPosition.getHeight());
        tmp2.add(rxPosition.getLongitude());
        tmp2.add(rxPosition.getLatitude());
        tmp2.add(rxPosition.getHeight());
        // 将发射机和接收机的经纬度信息返回给前端
        res.add(tmp1);
        res.add(tmp2);
        return Result.success(res, 2L);
    }

    @GetMapping("/raytracing")
    public Result calculateRayTracer(String txName,String rxName){
        //从获取发射机、接收机的信息
        Txdata txPosition = raytracingServiceImpl.getTxPosition(txName);
        Rxdata rxPosition = raytracingServiceImpl.getRxPosition(rxName);

        // 将发射机和接收机的经纬度 转换到 场景的原点坐标系下
        List<Double> t = SceneOriginConvert.LonLatToOrigin(txPosition.getLongitude(), txPosition.getLatitude(), txPosition.getHeight());
        List<Double> r = SceneOriginConvert.LonLatToOrigin(rxPosition.getLongitude(), rxPosition.getLatitude(), rxPosition.getHeight());
        DecimalFormat format = new DecimalFormat("0.0000"); // 将转换得到的坐标值四舍五入到小数点后4位
        double t0 = Double.parseDouble(format.format(t.get(0)));
        double t1 = Double.parseDouble(format.format(t.get(1)));
        double t2 = Double.parseDouble(format.format(t.get(2)));
        double r0 = Double.parseDouble(format.format(r.get(0)));
        double r1 = Double.parseDouble(format.format(r.get(1)));
        double r2 = Double.parseDouble(format.format(r.get(2)));
        Vector3d transmitter = new Vector3d(t0, t1, t2); //发射源的位置
        Vector3d receiver = new Vector3d(r0, r1, r2); //接收源的位置

        List<List<Double>> vertices2= raytracingServiceImpl.getVertexInfo();
        List<List<Double>> normalInfo = raytracingServiceImpl.getNormalInfo();
        List<Vector3d> intersections = raytracingServiceImpl.rayTracer(vertices2,normalInfo,transmitter,receiver);
        //TODO 返回给前端的信息

        List<List<Double>> res = new ArrayList<>();
        if(intersections == null) {
            //返回给前端 表示发射点到接收点不存在一次反射
            return Result.respon(200, "没有相交的点", null, 0L);
        }
        //返回给前端数据：交点的二维数组
        for (Vector3d v : intersections) {
            res.add(SceneOriginConvert.OriginToLonLat(v.x, v.y, v.z));
        }
        return Result.success(res, (long)res.size());
    }

    @GetMapping("/test")
    public Result calculateRaoshe(){
        List<List<Double>> vertices2= raytracingServiceImpl.getVertexInfo();
        List<List<Double>> normalInfo = raytracingServiceImpl.getNormalInfo();

        // 根据三角面的顶点和法向量信息，构造出了由6个面组成的正方体
        List<Face> faces = raytracingServiceImpl.constructFaces(vertices2, normalInfo);
        // 调用 边提取器 获得正方体的棱边信息
        List<Edge> edges = EdgeDetector.extractTotalEdges(faces);

        // 将棱边信息打印
        for (int i = 0; i < edges.size(); i++) {
            System.out.printf("这是第 %d 条边： %s\n", i, edges.get(i).toString());
            System.out.println(" 对应的两个面的法向量分别是:");
            System.out.println(edges.get(i).f1.normal);
            System.out.println(edges.get(i).f2.normal);
        }
        return null;
    }

//    @GetMapping("/getVertexInfo")
//    public List<List<Double>> getVertexInfo() {
//        return raytracingServiceImpl.getVertexInfo();
//    }

//    @GetMapping("/getNormalInfo")
//    public List<List<Double>> getNormalInfo() {
//
//        return raytracingServiceImpl.getNormalInfo();
//    }

}

