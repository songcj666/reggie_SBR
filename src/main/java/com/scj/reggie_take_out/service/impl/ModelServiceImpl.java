package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.Model;
import com.scj.reggie_take_out.mapper.ModelMapper;
import com.scj.reggie_take_out.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    public int updateLocation(String flag, double longitude, double latitude){
        return modelMapper.updateLocation(flag, longitude, latitude);
    }

    public boolean save(Model model) {
        return modelMapper.save(model);
    }

    public List<Model> listAllModel() {
        return modelMapper.listAllModel();
    }

    public boolean removeModelById(int id) {
        return modelMapper.removeModelById(id);
    }
}
