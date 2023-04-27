package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.CVector;
import com.scj.reggie_take_out.entity.Model;
import com.scj.reggie_take_out.mapper.CVectorMapper;
import com.scj.reggie_take_out.mapper.ModelMapper;
import com.scj.reggie_take_out.service.CVectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVectorServiceImpl extends ServiceImpl<CVectorMapper, CVector> implements CVectorService {

    @Autowired
    private CVectorMapper cVectorMapper;

    public List<CVector> heightNotZero(String rxsName){
        return cVectorMapper.heightNotZero(rxsName);
    }
}
