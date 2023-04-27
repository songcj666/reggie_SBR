package com.scj.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scj.reggie_take_out.entity.Txdata;
import com.scj.reggie_take_out.mapper.TxdataMapper;
import com.scj.reggie_take_out.service.TxdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyb
 * @since 2023-01-01
 */
@Service
public class TxdataServiceImpl extends ServiceImpl<TxdataMapper, Txdata> implements TxdataService {
    @Autowired
    private TxdataMapper txdataMapper;

    //修改
    public int updateLocation(String flag, double longitude, double latitude){
        return txdataMapper.updateLocation(flag, longitude, latitude);
    }

    //新增
    public boolean save(Txdata txdata) {
        return txdataMapper.save(txdata);
    }

    //查询
    public List<Txdata> listAllTxdata() {
        return txdataMapper.listAllTxdata();
    }

    //查询所有发射机名称
    public List<String> listAllTxname(){
        return txdataMapper.listAllTxname();
    }

    //删除
    public boolean removeTxdataById(Integer id) {
        return txdataMapper.removeTxdataById(id);
    }

}
