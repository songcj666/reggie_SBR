package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.entity.RxsModelData;
import com.scj.reggie_take_out.entity.Rxscircledata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RxscircledataMapper extends BaseMapper<Rxscircledata> {
    //修改
    int updateLocation(int flag, double longitude, double latitude);

    //新增
    boolean save(Rxscircledata rxscircledata);

    //查询
    List<Rxscircledata> listAllRxscircledata();

    //多表查询
    List<Antennas> listAnteByName(String antenna);

    Rxscircledata queryTwoPointPos(String rxsName);
    //删除
    boolean removeRxscircledataById(Integer id);
}
