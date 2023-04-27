package com.scj.reggie_take_out.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.RxsModelData;
import com.scj.reggie_take_out.entity.Rxsdata;
import com.scj.reggie_take_out.mapper.RxsModelDataMapper;
import com.scj.reggie_take_out.mapper.RxsdataMapper;
import com.scj.reggie_take_out.service.RxsModelDataService;
import com.scj.reggie_take_out.service.RxsdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RxsModelDataServiceImpl extends ServiceImpl<RxsModelDataMapper, RxsModelData> implements RxsModelDataService {
    @Autowired
    private RxsModelDataMapper rxsmodeldataMapper;

    //新增
    public boolean save(RxsModelData rxsmodeldata) {
        return rxsmodeldataMapper.save(rxsmodeldata);
    }

    //查询
    public List<RxsModelData> listAllRxsModelData() {
        return rxsmodeldataMapper.listAllRxsModelData();
    }

    public RxsModelData queryTwoPointPos(String rxsName) {
        return rxsmodeldataMapper.queryTwoPointPos(rxsName);
    }
    //删除
    public boolean removeRxsModelDataById(Integer id) {
        return rxsmodeldataMapper.removeRxsModelDataById(id);
    }

}