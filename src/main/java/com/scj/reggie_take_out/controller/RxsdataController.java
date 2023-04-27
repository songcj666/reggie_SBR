package com.scj.reggie_take_out.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.CVector;
import com.scj.reggie_take_out.entity.Rxsdata;

import com.scj.reggie_take_out.service.RxsdataService;
import com.scj.reggie_take_out.service.impl.CVectorServiceImpl;
import com.scj.reggie_take_out.service.impl.RxsdataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rxsdata")
public class RxsdataController {

    //    经度每隔0.00001度，距离相差约1米；
//    纬度每隔0.00001度，距离相差约1.1米;
    private static final double longInterval = 0.00001;
    private static final double latInterval = 0.00001;

    @Autowired
    private RxsdataService rxsdataService;
    @Autowired
    private RxsdataServiceImpl rxsdataServiceImpl;
    @Autowired
    private CVectorServiceImpl cVectorServiceImpl;
    //查询
    @GetMapping("/list")
    public List<Rxsdata> list() {
        return rxsdataServiceImpl.listAllRxsdata();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Rxsdata rxsdata) {
        if (!rxsdataServiceImpl.save(rxsdata)) {
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    @GetMapping("/isDuplicate")
    public Result isDuplicate(String areaName) {
        // 根据天线的名称进行校验
        LambdaQueryWrapper<Rxsdata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxsdata::getName, areaName);
        Rxsdata rxs = rxsdataServiceImpl.getOne(areaQueryWrapper);

        // 如果查询到，返回"区域名称已存在"
        if (rxs != null) {
            return Result.respon(1101, "区域名称已存在", 0L, null);
        }
        return Result.success(null, 0L);
    }

    // 计算多接收机的位置
    @GetMapping("/calPointLocation")
    public Result calPointLocation(String areaName) {
        LambdaQueryWrapper<Rxsdata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxsdata::getName, areaName);
        Rxsdata rxs = rxsdataServiceImpl.getOne(areaQueryWrapper);

        // 如果没有该区域
        if (rxs == null) {
            return Result.respon(1102, "区域名称不存在", 0L, null);
        }

        ArrayList<ArrayList<Double>> listTwo = new ArrayList<ArrayList<Double>>();
        int count = 0;
        /*for (double i = rxs.getLeftDownPointX(); i <= rxs.getLeftDownPointX() + rxs.getLength(); i = i + rxs.getIntervals()) {
            for (double j = rxs.getLeftDownPointY(); j <= rxs.getLeftDownPointY() + rxs.getWidth(); j = j + rxs.getIntervals()) {
                for (double k = rxs.getLeftDownPointZ();k <= rxs.getLeftDownPointZ() + rxs.getHeight(); k = k + rxs.getIntervals()) {
                    ArrayList<Double> listOne = new ArrayList<Double>();
//                System.out.println(i);
//                System.out.println(j);
                    count = count + 1;
                    listOne.add(i);
                    listOne.add(j);
                    listOne.add(k);
                    listTwo.add(listOne);
                    System.out.println(listTwo);
                }
            }
        }
        return Result.success(listTwo, Long.valueOf(count));*/


        for (double h = 0; h <= rxs.getHeight(); h = h + rxs.getIntervals()) {
            for (double i = rxs.getLeftUpLat(); i <= rxs.getRightDownLat(); i = i + longInterval * rxs.getIntervals()) {
                for (double j = rxs.getLeftUpLong(); j <= rxs.getRightDownLong(); j = j + latInterval * (rxs.getIntervals() / 1.1)) {
                    count++;
                    ArrayList<Double> listOne = new ArrayList<>();
//                    CVector p = new CVector(j, i, h); // 将每一个点的经纬高位置用结构体存储
//                    p.setRxsName(rxs.getName());
//                    if (!cVectorServiceImpl.save(p)) {
//                        Result.respon(1103, "多接收机位置计算失败", 0L, null);
//                    }
                    listOne.add(j);
                    listOne.add(i);
                    listOne.add(h);
                    listTwo.add(listOne);
                }
            }
        }
//        File file1 = FileUtil.writeLines(listTwo, "D:\\a.txt", "UTF-8");
//        System.out.println(file1);

        return Result.success(listTwo, (long) count);
    }


    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Rxsdata rxsdata) {
        return rxsdataService.saveOrUpdate(rxsdata);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return rxsdataServiceImpl.removeRxsdataById(id);
    }
}