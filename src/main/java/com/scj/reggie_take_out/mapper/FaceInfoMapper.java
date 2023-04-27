package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.secne_Info.FaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FaceInfoMapper extends BaseMapper<FaceInfo> {

    List<FaceInfo> getFaceInfo();

}
