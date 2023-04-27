package com.scj.reggie_take_out.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Rxscircledata", description="")
public class Rxscircledata implements Serializable{

        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "关键属性")
        private Long id;

        @ApiModelProperty(value = "多接收机名称")
        private String name;

        @ApiModelProperty(value = "天线源选择")
        private String antenna;

        @ApiModelProperty(value = "收集面半径，小数点后一位，单位为米")
        private Double radius;

        @ApiModelProperty(value = "接收机间隔宽度，小数点后两位，单位为米")
        private Double intervals;

//        @ApiModelProperty(value = "矩形框长度，小数点后两位，单位为米")
//        private Double length;
//
//        @ApiModelProperty(value = "矩形框宽度，小数点后两位，单位为米")
//        private Double width;

        @ApiModelProperty(value = "多接收机坐标（高度），小数点后两位，单位为m")
        private Double height;

        @ApiModelProperty(value = "多接收机尺寸，小数点后两位，单位为m")
        private Double size;

        @ApiModelProperty(value = "矩形区域的ID，整数")
        private BigInteger flag;

//        @ApiModelProperty(value = "图层")
//        private Integer layer;

        @ApiModelProperty(value = "圆心坐标经度，保留四位小数，单位度")
        private Double centerPointLong;

        @ApiModelProperty(value = "圆心坐标纬度，保留四位小数，单位度")
        private Double centerPointLat;

        @ApiModelProperty(value = "圆心坐标经度，保留四位小数，单位度")
        private Double endPointLong;

        @ApiModelProperty(value = "圆心坐标纬度，保留四位小数，单位度")
        private Double endPointLat;

        @ApiModelProperty(value = "圆心半径，单位m")
        private Double circleRadius;
}
