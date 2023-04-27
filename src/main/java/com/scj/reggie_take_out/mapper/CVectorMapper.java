package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.CVector;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CVectorMapper extends BaseMapper<CVector> {

    List<CVector> heightNotZero(String rxsName);
}
