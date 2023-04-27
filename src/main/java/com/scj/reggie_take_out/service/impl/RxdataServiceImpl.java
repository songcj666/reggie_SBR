package com.scj.reggie_take_out.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.*;
import com.scj.reggie_take_out.mapper.RxdataMapper;
import com.scj.reggie_take_out.service.RxdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class RxdataServiceImpl extends ServiceImpl<RxdataMapper, Rxdata> implements RxdataService {
    @Autowired
    private RxdataMapper rxdataMapper;

    //修改
    public int updateLocation(String flag, double longitude, double latitude){
        return rxdataMapper.updateLocation(flag, longitude, latitude);
    }

    //新增
    public boolean save(Rxdata rxdata) {
        return rxdataMapper.save(rxdata);
    }

    //查询
    public List<Rxdata> listAllRxdata() {
        return rxdataMapper.listAllRxdata();
    }

    //多表查询
    public List<Antennas> listAnteByName(String antenna) {
        return rxdataMapper.listAnteByName(antenna);
    }

    //删除
    public boolean removeRxdataById(Integer id) {
        return rxdataMapper.removeRxdataById(id);
    }

}
