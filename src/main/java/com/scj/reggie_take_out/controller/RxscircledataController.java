package com.scj.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.entity.Rxscircledata;
import com.scj.reggie_take_out.entity.Rxsdata;
import com.scj.reggie_take_out.service.RxscircledataService;
import com.scj.reggie_take_out.service.impl.RxscircledataServiceImpl;
import com.scj.reggie_take_out.utils.CheckBall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rxscircledata")
public class RxscircledataController {

    //    经度每隔0.00001度，距离相差约1米；
//    纬度每隔0.00001度，距离相差约1.1米;
    private static final double longInterval = 0.00001;
    private static final double latInterval = 0.00001;

    @Autowired
    private RxscircledataService rxscircledataService;
    @Autowired
    private RxscircledataServiceImpl rxscircledataServiceImpl;

    //查询
    @GetMapping("/list")
    public List<Rxscircledata> list() {
        return rxscircledataServiceImpl.listAllRxscircledata();
    }

    /**
     * 根据具体的天线类型查询接收机上天线信息
     *
     * @param antenna:天线类型
     * @return：返回具体模型信息
     */
    //多表查询
    @GetMapping("/listhdipant")
    public List<Antennas> listHdipAnt(String antenna) {
        return rxscircledataServiceImpl.listAnteByName(antenna);
    }

    @GetMapping("/listomniant")
    public List<Antennas> listOmniAnt(String antenna) {
        return rxscircledataServiceImpl.listAnteByName(antenna);
    }

    @GetMapping("/listsmonoant")
    public List<Antennas> listSmonoAnt(String antenna) {
        return rxscircledataServiceImpl.listAnteByName(antenna);
    }


    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Rxscircledata rxscircledata) {
        if (!rxscircledataServiceImpl.save(rxscircledata)) {
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    @GetMapping("/isDuplicate")
    public Result isDuplicate(String areaName) {
        // 根据天线的名称进行校验
        LambdaQueryWrapper<Rxscircledata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxscircledata::getName, areaName);
        Rxscircledata rxsc = rxscircledataServiceImpl.getOne(areaQueryWrapper);

        // 如果查询到，返回"区域名称已存在"
        if (rxsc != null) {
            return Result.respon(1101, "区域名称已存在", 0L, null);
        }
        return Result.success(null, 0L);
    }

    /**
     * 根据多接收机名称判断是否存在，如果不存在布置多接收机并返回每个点经纬高度信息
     * @param areaName
     * @return
     */
    // 计算椭圆形多接收机的位置
    @GetMapping("/calcirclePointLocation")
    public Result calcirclePointLocation(String areaName) {
        LambdaQueryWrapper<Rxscircledata> areaQueryWrapper = new LambdaQueryWrapper<>();
        areaQueryWrapper.eq(Rxscircledata::getName, areaName);
        Rxscircledata rxsc = rxscircledataServiceImpl.getOne(areaQueryWrapper);

        // 如果没有该区域
        if (rxsc == null) {
            return Result.respon(1102, "区域名称不存在", 0L, null);
        }

        ArrayList<ArrayList<Double>> listTwo = new ArrayList<ArrayList<Double>>();
        int count = 0;
        //用弧度算,n等分圆
        //把圆心设在原点，再加入高程，判断是否在球里面，然后再转换成经纬度坐标
//        double bi = 60;
//        double radians = (Math.PI / 180) * Math.round(360 / bi);
//        for (double r = 0; r <= rxsc.getCircleRadius(); r = r + rxsc.getIntervals()) {
//            for (int i = 0; i < bi; i++) {
//                double x = rxsc.getCenterPointLong() + r * Math.sin(radians * i) * longInterval;
//                double y = rxsc.getCenterPointLat() + r * Math.cos(radians * i) / 1.1 * latInterval;
//                count++;
//
//                ArrayList<Double> listOne = new ArrayList<>();
//                listOne.add(x);
//                listOne.add(y);
//                listTwo.add(listOne);
//            }
//        }
//        return Result.success(listTwo, (long) count);
//球
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
                        count++;

                        ArrayList<Double> listOne = new ArrayList<>();
                        listOne.add(x);
                        listOne.add(y);
                        listOne.add(h);
                        listTwo.add(listOne);
//                        File file1= FileUtil.writeLines(listTwo,"D:\\a.txt","UTF-8");
//                        System.out.println(file1);
                    }
                }
            }
        }
        return Result.success(listTwo, (long) count);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Rxscircledata rxscircledata) {
        return rxscircledataService.saveOrUpdate(rxscircledata);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return rxscircledataServiceImpl.removeRxscircledataById(id);
    }
}



