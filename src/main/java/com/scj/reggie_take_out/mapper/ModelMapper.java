package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper extends BaseMapper<Model> {
    //根据模型flag信息，修改经纬度信息
    int updateLocation(String flag, double longitude, double latitude);
//新增
    boolean save(Model model);
//查询
    List<Model> listAllModel();
//删除
    boolean removeModelById(int id);
}
