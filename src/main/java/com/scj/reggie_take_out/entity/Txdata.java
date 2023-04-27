package com.scj.reggie_take_out.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyb
 * @since 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Txdata对象", description="")
public class Txdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键属性")
    private Long id;

    @ApiModelProperty(value = "发射机名称")
    private String name;

    @ApiModelProperty(value = "天线源选择")
    private String antenna;

    @ApiModelProperty(value = "输入功率，单位为dBm")
    private Double power;

    @ApiModelProperty(value = "发射机坐标（经度），小数点后四位，单位为度")
    private Double longitude;

    @ApiModelProperty(value = "发射机坐标（纬度），小数点后四位，单位为度")
    private Double latitude;

    @ApiModelProperty(value = "发射机坐标（高度），小数点后两位，单位为m")
    private Double height;

    @ApiModelProperty(value = "发射机尺寸，小数点后一位，单位为m")
    private Double size;

    @ApiModelProperty(value = "发射机唯一标识")
    private String flag;

    @ApiModelProperty(value = "图层")
    private int layer;
}
