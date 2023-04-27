package com.scj.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigInteger;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RxsModelData", description="")
@TableName(value = "Rxsmodeldata")
public class RxsModelData implements Serializable {

    private static final long serialVersionUID = 1L;
    //@TableId(type= IdType.ASSIGN_ID)//主键生成策略
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

    @ApiModelProperty(value = "矩形框长度，小数点后两位，单位为米")
    private Double length;

    @ApiModelProperty(value = "矩形框宽度，小数点后两位，单位为米")
    private Double width;

    @ApiModelProperty(value = "多接收机坐标（经度），小数点后四位，单位为度")
    private Double leftUpLong;

    @ApiModelProperty(value = "多接收机坐标（纬度），小数点后四位，单位为度")
    private Double leftUpLat;

    private Double rightDownLong;

    private Double rightDownLat;

    @ApiModelProperty(value = "矩形区域左上角点的笛卡尔x坐标，保留两位小数，单位m")
    private Double leftUpPointX;

    @ApiModelProperty(value = "矩形区域左上角点的笛卡尔y坐标，保留两位小数，单位m")
    private Double leftUpPointY;

    @ApiModelProperty(value = "矩形区域左上角点的笛卡尔z坐标，保留两位小数，单位m")
    private Double leftUpPointZ;


    @ApiModelProperty(value = "多接收机坐标（高度），小数点后两位，单位为m")
    private Double height;

    @ApiModelProperty(value = "多接收机尺寸，小数点后两位，单位为m")
    private Double size;

    @ApiModelProperty(value = "矩形区域的ID，整数")
    private BigInteger flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAntenna() {
        return antenna;
    }

    public void setAntenna(String antenna) {
        this.antenna = antenna;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getIntervals() {
        return intervals;
    }

    public void setIntervals(Double intervals) {
        this.intervals = intervals;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLeftUpLong() {
        return leftUpLong;
    }

    public void setLeftUpLong(Double leftUpLong) {
        this.leftUpLong = leftUpLong;
    }

    public Double getLeftUpLat() {
        return leftUpLat;
    }

    public void setLeftUpLat(Double leftUpLat) {
        this.leftUpLat = leftUpLat;
    }

    public Double getRightDownLong() {
        return rightDownLong;
    }

    public void setRightDownLong(Double rightDownLong) {
        this.rightDownLong = rightDownLong;
    }

    public Double getRightDownLat() {
        return rightDownLat;
    }

    public void setRightDownLat(Double rightDownLat) {
        this.rightDownLat = rightDownLat;
    }

    public Double getLeftUpPointX() {
        return leftUpPointX;
    }

    public void setLeftUpPointX(Double leftUpPointX) {
        this.leftUpPointX = leftUpPointX;
    }

    public Double getLeftUpPointY() {
        return leftUpPointY;
    }

    public void setLeftUpPointY(Double leftUpPointY) {
        this.leftUpPointY = leftUpPointY;
    }

    public Double getLeftUpPointZ() {
        return leftUpPointZ;
    }

    public void setLeftUpPointZ(Double leftUpPointZ) {
        this.leftUpPointZ = leftUpPointZ;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigInteger getFlag() {
        return flag;
    }

    public void setFlag(BigInteger flag) {
        this.flag = flag;
    }
}


