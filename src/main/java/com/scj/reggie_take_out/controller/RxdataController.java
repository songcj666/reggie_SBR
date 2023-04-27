package com.scj.reggie_take_out.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.*;
import com.scj.reggie_take_out.service.RxdataService;
import com.scj.reggie_take_out.service.impl.RxdataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rxdata")
public class RxdataController {

    @Autowired
    private RxdataService rxdataService;
    @Autowired
    private RxdataServiceImpl rxdataServiceImpl;


    //查询
    @GetMapping("/list")
    public List<Rxdata> list() {
        return rxdataServiceImpl.listAllRxdata();
    }

    /**
     * 根据具体的天线类型查询接收机上天线信息
     * @param antenna:天线类型
     * @return：返回对应天线信息
     */
    //多表查询
    @GetMapping("/listhdipant")
    public List<Antennas> listHdipAnt(String antenna) {
        return rxdataServiceImpl.listAnteByName(antenna);
    }
    @GetMapping("/listomniant")
    public List<Antennas> listOmniAnt(String antenna) {
        return rxdataServiceImpl.listAnteByName(antenna);
    }
    @GetMapping("/listsmonoant")
    public List<Antennas> listSmonoAnt(String antenna) {
        return rxdataServiceImpl.listAnteByName(antenna);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Rxdata rxdata) {
        if (!rxdataServiceImpl.save(rxdata)) {
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    //修改
    @PatchMapping("/mod")
    public Result mod(@RequestBody Rxdata rxdata) {
        rxdataServiceImpl.updateLocation(rxdata.getFlag(), rxdata.getLongitude(), rxdata.getLatitude());
        return Result.success(null, 0L);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Rxdata rxdata) {
        return rxdataService.saveOrUpdate(rxdata);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return rxdataServiceImpl.removeRxdataById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public List<Rxdata> listP(@RequestBody Rxdata rxdata) {
        LambdaQueryWrapper<Rxdata> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Rxdata::getName, rxdata.getName());
        return rxdataService.list(lambdaQueryWrapper);
    }

    /**
     *
     * @param rxName:接收机名称
     * @return；如果接收机已存在，返回1301和接收机名称重名。如果接收机名称不存在，返回成功（可以继续添加此接收机信息）。
     */
    @GetMapping("/isDuplicate")
    public Result isDuplicate(String rxName){
        // 根据天线的名称进行校验
        LambdaQueryWrapper<Rxdata> rxQueryWrapper = new LambdaQueryWrapper<>();
        rxQueryWrapper.eq(Rxdata::getName, rxName);
        Rxdata rxdata = rxdataService.getOne(rxQueryWrapper);

        // 如果查询到，返回"发射机已存在"
        if(rxdata != null) {
            return Result.respon(1301, "接收机名称重名", 0L, null);
        }
        return Result.success(null, 0L);
    }
}


