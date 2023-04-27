package com.scj.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scj.reggie_take_out.entity.Antennas;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface AntennasService extends IService<Antennas> {
    List<Antennas> hdipList(String antenna);
    List<Antennas> smonoList(String antenna);
    List<Antennas> omniList(String antenna);

    boolean hdipSave(Antennas antennas);
    boolean smonoSave(Antennas antennas);
    boolean omniSave(Antennas antennas);

}
