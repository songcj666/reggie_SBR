package com.scj.reggie_take_out.entity;

import lombok.Data;

/**
 天线数据库映射
 */
@Data
public class Antennas {
    private int id;
    private String waveform;
    private Double frequency;
    private String name;
    private Double maxGain;
    private String polar;
    private Double hpbw;
    private Double fnbw;
    private Double threshold;
    private Double loss;
    private Double vswr;
    private String antenna; // 天线的类型，半波偶极子天线、短单极子天线
}
