package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.mapper.AntennasMapper;

import com.scj.reggie_take_out.service.AntennasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntennasServiceImpl extends ServiceImpl<AntennasMapper, Antennas> implements AntennasService {

    @Autowired
    private AntennasMapper antennasMapper;

    public AntennasServiceImpl(AntennasMapper antennasMapper) {
        this.antennasMapper = antennasMapper;
    }

    @Override
    public List<Antennas> hdipList(String antenna) {
        return antennasMapper.hdipList(antenna);
    }

    @Override
    public List<Antennas> smonoList(String antenna) {
        return antennasMapper.smonoList(antenna);
    }

    @Override
    public List<Antennas> omniList(String antenna) {
        return antennasMapper.omniList(antenna);
    }

    @Override
    public boolean hdipSave(Antennas antennas) {
        return antennasMapper.hdipSave(antennas);
    }

    @Override
    public boolean smonoSave(Antennas antennas) {
        return antennasMapper.smonoSave(antennas);
    }

    @Override
    public boolean omniSave(Antennas antennas) {
        return antennasMapper.omniSave(antennas);
    }
}
