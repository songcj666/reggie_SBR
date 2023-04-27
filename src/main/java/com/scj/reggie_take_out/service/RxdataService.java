package com.scj.reggie_take_out.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.entity.Rxdata;

import java.util.List;

public interface RxdataService extends IService<Rxdata> {
    List<Antennas> listAnteByName(String antenna);
    List<Rxdata> listAllRxdata();
}
