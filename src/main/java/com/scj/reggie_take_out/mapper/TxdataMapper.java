package com.scj.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scj.reggie_take_out.entity.Txdata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyb
 * @since 2023-01-01
 */
@Mapper
public interface TxdataMapper extends BaseMapper<Txdata> {

    //修改
    int updateLocation(String flag, double longitude, double latitude);

    Txdata getTxPosition(String txName);

    //新增
    boolean save(Txdata txdata);

    //查询
    List<Txdata> listAllTxdata();

    //查询所有发射机名称
    List<String> listAllTxname();

    //删除
    boolean removeTxdataById(Integer id);
}
