package com.scj.reggie_take_out.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.Rxsdata;
import com.scj.reggie_take_out.mapper.RxsdataMapper;
import com.scj.reggie_take_out.service.RxsdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RxsdataServiceImpl extends ServiceImpl<RxsdataMapper, Rxsdata> implements RxsdataService {
    @Autowired
    private RxsdataMapper rxsdataMapper;

    //新增
    public boolean save(Rxsdata rxsdata) {
        return rxsdataMapper.save(rxsdata);
    }

    //查询
    public List<Rxsdata> listAllRxsdata() {
        return rxsdataMapper.listAllRxsdata();
    }

//    public Rxsdata queryTwoPointPos(String rxsName) {
//        return rxsdataMapper.queryTwoPointPos(rxsName);
//    }
    //删除
    public boolean removeRxsdataById(Integer id) {
        return rxsdataMapper.removeRxsdataById(id);
    }

}