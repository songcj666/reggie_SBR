package com.scj.reggie_take_out.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.CVector;
import com.scj.reggie_take_out.entity.RxsModelData;
import com.scj.reggie_take_out.entity.Rxscircledata;
import com.scj.reggie_take_out.entity.Rxsdata;
import com.scj.reggie_take_out.service.RxsModelDataService;
import com.scj.reggie_take_out.service.impl.CVectorServiceImpl;
import com.scj.reggie_take_out.service.impl.RxsModelDataServiceImpl;
import com.scj.reggie_take_out.service.RxscircledataService;
import com.scj.reggie_take_out.service.impl.RxscircledataServiceImpl;
import com.scj.reggie_take_out.utils.CheckBall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rxsmodeldata")
public class RxsModelDataController {

    //    经度每隔0.00001度，距离相差约1米；
//    纬度每隔0.00001度，距离相差约1.1米;
    private static final double longInterval = 0.00001;
    private static final double latInterval = 0.00001;

    @Autowired
    private RxsModelDataService rxsmodeldataService;
    @Autowired
    private RxsModelDataServiceImpl rxsmodeldataServiceImpl;
    @Autowired
    private CVectorServiceImpl cVectorServiceImpl;
    @Autowired
    private RxscircledataService rxscircledataService;
    @Autowired
    private RxscircledataServiceImpl rxscircledataServiceImpl;
    //查询
    @GetMapping("/list")
    public List<RxsModelData> list() {
        return rxsmodeldataServiceImpl.listAllRxsModelData();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody RxsModelData rxsmodeldata) {
        if (!rxsmodeldataServiceImpl.save(rxsmodeldata)) {
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    @GetMapping("/isDuplicate")
    public Result isDuplicate(String areaName) {
        // 根据区域的名称进行校验
        LambdaQueryWrapper<RxsModelData> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(RxsModelData::getName, areaName);
        RxsModelData rxs = rxsmodeldataServiceImpl.getOne(areaQueryWrapper);

        // 如果查询到，返回"区域名称已存在"
        if (rxs != null) {
            return Result.respon(1101, "区域名称已存在", 0L, null);
        }
        return Result.success(null, 0L);
    }

    // 计算多接收机的位置
    @GetMapping("/calPointLocation")
    public Result calPointLocation(String areaName) {
        LambdaQueryWrapper<RxsModelData> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(RxsModelData::getName, areaName);
        RxsModelData rxs = rxsmodeldataServiceImpl.getOne(areaQueryWrapper);

        // 如果没有该区域
        if (rxs == null) {
            return Result.respon(1102, "区域名称不存在", 0L, null);
        }

        ArrayList<ArrayList<Double>> listTwo = new ArrayList<ArrayList<Double>>();
        int count = 0;

        for (double h = 0; h <= rxs.getHeight(); h = h + rxs.getIntervals()) {
            for (double i = rxs.getLeftUpLat(); i >= rxs.getRightDownLat(); i = i - longInterval * rxs.getIntervals()) {
                for (double j = rxs.getLeftUpLong(); j <= rxs.getRightDownLong(); j = j + latInterval * (rxs.getIntervals() / 1.1)) {
                    count++;
                    ArrayList<Double> listOne = new ArrayList<>();
                    CVector p = new CVector(j, i, h); // 将每一个点的经纬高位置用结构体存储
                    p.setRxsName(rxs.getName());
                    if (!cVectorServiceImpl.save(p)) {
                        Result.respon(1103, "多接收机位置计算失败", 0L, null);
                    }
                    listOne.add(j);
                    listOne.add(i);
                    listOne.add(h);
                    listTwo.add(listOne);
                }
            }
        }
        return Result.success(listTwo, (long) count);
    }

    /*@GetMapping("/isCircleDuplicate")
    public Result isCircleDuplicate(String areaName) {
        // 根据区域的名称进行校验
        LambdaQueryWrapper<Rxscircledata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxscircledata::getName, areaName);
        Rxscircledata rxsc = rxscircledataServiceImpl.getOne(areaQueryWrapper);

        // 如果查询到，返回"区域名称已存在"
        if (rxsc != null) {
            return Result.respon(1101, "区域名称已存在", 0L, null);
        }
        return Result.success(null, 0L);
    }

    // 计算圆形多接收机的位置
    @GetMapping("/calCirclePointLocation")
    public Result calCirclePointLocation(String areaName) {
        LambdaQueryWrapper<Rxscircledata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxscircledata::getName, areaName);
        Rxscircledata rxsc = rxscircledataServiceImpl.getOne(areaQueryWrapper);

        // 如果没有该区域
        if (rxsc == null) {
            return Result.respon(1102, "区域名称不存在", 0L, null);
        }

        ArrayList<ArrayList<Double>> listTwo1 = new ArrayList<ArrayList<Double>>();
        int count1 = 0;

        int bi = 30;//切分数量
        double radians = (Math.PI / 180) * Math.round(360 / bi);
        for (double r = 0; r < rxsc.getCircleRadius(); r = r + rxsc.getIntervals()) {
            for (double h = 0; h < rxsc.getCircleRadius(); h = h + rxsc.getIntervals()) {
                for (int i = 0; i < bi; i++) {
                    double x1 = r * Math.sin(radians * i);
                    double y1 = r * Math.cos(radians * i);

                    if (CheckBall.isBall(x1, y1, h, 0, 0, rxsc.getCircleRadius())) {
                        double x = rxsc.getCenterPointLong() + x1 * longInterval;
                        double y = rxsc.getCenterPointLat() + y1 / 1.1 * latInterval;
                        count1++;
                        ArrayList<Double> listOne = new ArrayList<>();
                        CVector p = new CVector(x, y, h); // 用同一个结构体存储可以吗？
                        p.setRxsName(rxsc.getName());
                        if (!cVectorServiceImpl.save(p)) {
                            Result.respon(1103, "多接收机位置计算失败", 0L, null);
                        }
                        ArrayList<Double> listOne1 = new ArrayList<>();
                        listOne1.add(x);
                        listOne1.add(y);
                        listOne1.add(h);
                        listTwo1.add(listOne1);
//                        File file1= FileUtil.writeLines(listTwo,"D:\\a.txt","UTF-8");
//                        System.out.println(file1);
                    }
                }
            }
        }
        return Result.success(listTwo1, (long) count1);
    }
*/
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody RxsModelData rxsmodeldata) {
        return rxsmodeldataService.saveOrUpdate(rxsmodeldata);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return rxsmodeldataServiceImpl.removeRxsModelDataById(id);
    }
}