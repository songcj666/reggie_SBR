package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.RxsModelData;
import com.scj.reggie_take_out.entity.Rxsdata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RxsdataMapper extends BaseMapper<Rxsdata> {

    //修改
    int updateLocation(int flag, double longitude, double latitude);

    //新增
    boolean save(Rxsdata rxsdata);

    //查询
    List<Rxsdata> listAllRxsdata();

//    Rxsdata queryTwoPointPos(String rxsName);
    //删除

    boolean removeRxsdataById(Integer id);
}