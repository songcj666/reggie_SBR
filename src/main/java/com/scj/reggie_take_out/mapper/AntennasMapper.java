package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.Antennas;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AntennasMapper extends BaseMapper<Antennas>  {
    //修改
    int updateLocation(Long id, double frequency, String waveform);

    //新增指定天线信息
    boolean hdipSave(Antennas antennas);

    boolean omniSave(Antennas antennas);

    boolean smonoSave(Antennas antennas);
    //全查询
    List<Antennas> listAllAntennas();
    //查询指定天线信息
    List<Antennas> hdipList(String antenna);

    List<Antennas> omniList(String antenna);

    List<Antennas> smonoList(String antenna);
    //删除
    boolean removeAntennasById(Integer id);

}
