package com.scj.reggie_take_out.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RxdataMapper extends BaseMapper<Rxdata> {

    //修改
    int updateLocation(String flag, double longitude, double latitude);

    Rxdata getRxPosition(String rxName);

    //新增
    boolean save(Rxdata rxdata);

    //查询
    List<Rxdata> listAllRxdata();

    //多表查询
    List<Antennas> listAnteByName(String antenna);

    //删除
    boolean removeRxdataById(Integer id);
}
