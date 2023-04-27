package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.entity.RxsModelData;
import com.scj.reggie_take_out.service.RxscircledataService;
import com.scj.reggie_take_out.entity.Rxscircledata;
import com.scj.reggie_take_out.mapper.RxscircledataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RxscircledataServiceImpl extends ServiceImpl<RxscircledataMapper, Rxscircledata> implements RxscircledataService {
    @Autowired
    private RxscircledataMapper rxscircledataMapper;

    //新增
    public boolean save(Rxscircledata rxscircledata) {
        return rxscircledataMapper.save(rxscircledata);
    }

    //查询
    public List<Rxscircledata> listAllRxscircledata() {
        return rxscircledataMapper.listAllRxscircledata();
    }
   //多表查询
    public List<Antennas> listAnteByName(String antenna) {
        return rxscircledataMapper.listAnteByName(antenna);
    }

    public Rxscircledata queryTwoPointPos(String rxsName) {
        return rxscircledataMapper.queryTwoPointPos(rxsName);
    }
    //删除
    public boolean removeRxscircledataById(Integer id) {
        return rxscircledataMapper.removeRxscircledataById(id);
    }

}
