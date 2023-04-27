package com.scj.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 模型数据库映射
 */
@Data
public class Model {

    private int id;
    //模型名称
    private String name;
    //材质
    private String material;
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //高度
    private double height;
    //缩放倍数
    private double scale;
    //模型所属图层
    private int layer;
    //模型唯一标识
    private String flag;
}
