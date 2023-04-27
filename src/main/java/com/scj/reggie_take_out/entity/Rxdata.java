package com.scj.reggie_take_out.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RxData", description="")
public class Rxdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键属性")
    private Long id;

    @ApiModelProperty(value = "接收机名称")
    private String name;

    @ApiModelProperty(value = "天线选择")
    private String antenna;

    @ApiModelProperty(value = "收集面半径，小数点后两位，单位为米")
    private Double radius;

    @ApiModelProperty(value = "接收机坐标（经度），小数点后五位，单位为度")
    private Double longitude;

    @ApiModelProperty(value = "接收机坐标（纬度），小数点后五位，单位为度")
    private Double latitude;

    @ApiModelProperty(value = "接收机坐标（高度），小数点后两位，单位为m")
    private Double height;

    @ApiModelProperty(value = "接收机尺寸，小数点后两位，单位为m")
    private Double size;

    @ApiModelProperty(value = "单接收机唯一标识")
    private String flag;

    @ApiModelProperty(value = "图层")
    private int layer;

}

