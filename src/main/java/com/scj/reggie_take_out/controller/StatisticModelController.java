package com.scj.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.*;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.service.impl.*;
import com.scj.reggie_take_out.utils.CalDistance;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
 * 统计模型类
 * */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/statisticmodel")
public class StatisticModelController {
    @Autowired
    private TxdataServiceImpl txdataServiceImpl;
    @Autowired
    private AntennasServiceImpl antennasServiceImpl;
    @Autowired
    private RxsModelDataServiceImpl rxsmodeldataServiceImpl;
    @Autowired
    private RxscircledataServiceImpl rxscircledataServiceImpl;
    @Autowired
    private CVectorServiceImpl cVectorServiceImpl;

//    public static void main (String[] args){
//        double[] OkumuraHata = OkumuraHata(30, 1000, 50, 5, 200);
//        System.out.println("奥村-哈达模型的路径损耗为：" + OkumuraHata[0]);
//        System.out.println("奥村-哈达模型的接收功率为：" + OkumuraHata[1]);
//        System.out.println("奥村-哈达模型的接收点处的功率密度为：" + OkumuraHata[2]);
//        System.out.println("奥村-哈达模型的电场强度为：" + OkumuraHata[3]);
//        System.out.println("奥村-哈达模型的磁场强度为：" + OkumuraHata[4]);
//        System.out.println("奥村-哈达模型的计算结果为：" + Arrays.toString(OkumuraHata));
//        double[] Egli = Egli(30, 1000, 50, 5, 200, 10);
//        System.out.println("Egli模型的计算结果为：" + Arrays.toString(Egli));
//        double[] ITU1 = ITU(30, 1000, 200, "Los");
//        System.out.println("ITU1模型的计算结果为：" + Arrays.toString(ITU1));
//        double[] ITU2 = ITU(30, 1000, 200, "NLos");
//        System.out.println("ITU2模型的计算结果为：" + Arrays.toString(ITU2));
//    }

    //Okumura-Hata模型
    @GetMapping("/okumurahata")
    public Result OkumuraHata(String rxsName){

        double totalLoss = 0;
        double totalPrW = 0;
        double totalPd = 0;
        double totalE = 0;
        double totalH = 0;
        //获取所有发射机名称
        List<String> txNames = txdataServiceImpl.listAllTxname();
        List<CVector> list = cVectorServiceImpl.heightNotZero(rxsName);
        RxsModelData rxsmodeldata = rxsmodeldataServiceImpl.queryTwoPointPos(rxsName);
//        Rxscircledata rxscircledata = rxscircledataServiceImpl.queryTwoPointPos(rxsName);

        List<Double> lossList = new ArrayList<>();
        List<Double> PrWList = new ArrayList<>();
        List<Double> PdList = new ArrayList<>();
        List<Double> EList = new ArrayList<>();
        List<Double> HList = new ArrayList<>();
        List<Double> distanceList = new ArrayList<>();      //暂时没有用到

        for (CVector cVector : list) {
            for (String txName : txNames) {
                LambdaQueryWrapper<Txdata> txQuery = new LambdaQueryWrapper();
                txQuery.eq(Txdata::getName, txName);
                Txdata txdata = txdataServiceImpl.getOne(txQuery);
                double Pt = txdata.getPower();
                double ht = txdata.getHeight();
                CVector txPosition = new CVector(txdata.getLongitude(), txdata.getLatitude(), ht);

                //获取当前发射机对应天线的相关信息
                LambdaQueryWrapper<Antennas> anteQuery = new LambdaQueryWrapper();
                anteQuery.eq(Antennas::getName, txdata.getAntenna());
                Antennas ante = antennasServiceImpl.getOne(anteQuery);
                double frequency = ante.getFrequency();
                double distance = CalDistance.getEuclideanDistance(cVector, txPosition);

                //计算路径损耗
                double loss = 69.55 + 26.16*Math.log10(frequency) - 13.82*Math.log10(ht) + (44.9-6.55*Math.log10(ht))*Math.log10(distance);
                double alfa = 0;
                if (frequency >= 200){
                    alfa = 3.2 * Math.pow(Math.log10(11.75*cVector.getVZ()), 2) - 4.97;
                } else if (frequency < 200) {
                    alfa = 8.29 * Math.pow(Math.log10(1.54*cVector.getVZ()), 2) - 1.1;
                }
                loss = loss - alfa;         //以dB为单位的路径损耗

                //计算接收功率
                double Pr = Pt - loss;      //以dBm为单位
                double PrW;
                PrW = Math.pow(10, Pr/10) / 1000;       //以瓦特W为单位的接收功率PrW

                //计算功率密度
                double Pd = PrW / (4 * Math.PI * Math.pow(distance*1000, 2));           //以W/㎡为单位的接收点处的功率密度

                //计算电场强度
                double Z0 = 377;                        //自由空间阻抗值377Ω
                double E = Math.sqrt(Pd * Z0);          //以V/m为单位的电场强度

                //计算磁场强度
                double H = E / Z0;

                //对路径损耗、接收功率、功率密度、电场强度、磁场强度的标量进行叠加
                totalLoss += loss;
                totalPrW += PrW;
                totalPd += Pd;
                totalE += E;
                totalH += H;
            }
            lossList.add(totalLoss);
            PrWList.add(totalPrW);
            PdList.add(totalPd);
            EList.add(totalE);
            HList.add(totalH);
            totalLoss=0;
            totalPrW=0;
            totalPd=0;
            totalE=0;
            totalH=0;
        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("路径损失", lossList);
        resMap.put("接收功率", PrWList);
        resMap.put("功率密度", PdList);
        resMap.put("电场强度", EList);
        resMap.put("磁场强度", HList);
        resMap.put("多接收机WGS84坐标", list);
        resMap.put("多接收机信息", rxsmodeldata);
//        resMap.put("多接收机信息", rxscircledata);
        return Result.success(resMap, (long)resMap.size());
    }

    //Egli模型
    /*@GetMapping("/Egli")
    public Result Egli(String rxsName){

        double totalLoss = 0;
        double totalPrW = 0;
        double totalPd = 0;
        double totalE = 0;
        double totalH = 0;
        //获取所有发射机名称
        List<String> txNames = txdataServiceImpl.listAllTxname();
        List<CVector> list = cVectorServiceImpl.heightNotZero(rxsName);
        RxsModelData rxsmodeldata = rxsmodeldataServiceImpl.queryTwoPointPos(rxsName);

        List<Double> lossList = new ArrayList<>();
        List<Double> PrWList = new ArrayList<>();
        List<Double> PdList = new ArrayList<>();
        List<Double> EList = new ArrayList<>();
        List<Double> HList = new ArrayList<>();
        List<Double> distanceList = new ArrayList<>();      //暂时没有用到

        for (CVector cVector : list) {
            for (String txName : txNames) {
                LambdaQueryWrapper<Txdata> txQuery = new LambdaQueryWrapper();
                txQuery.eq(Txdata::getName, txName);
                Txdata txdata = txdataServiceImpl.getOne(txQuery);
                double Pt = txdata.getPower();
                double ht = txdata.getHeight();
                double hr = rxsmodeldata.getHeight();
                CVector txPosition = new CVector(txdata.getLongitude(), txdata.getLatitude(), ht);

                //获取当前发射机对应天线的相关信息
                LambdaQueryWrapper<Antennas> anteQuery = new LambdaQueryWrapper();
                anteQuery.eq(Antennas::getName, txdata.getAntenna());
                Antennas ante = antennasServiceImpl.getOne(anteQuery);
                double frequency = ante.getFrequency();
                double distance = CalDistance.getEuclideanDistance(cVector, txPosition);

                //计算路径损耗
                double loss;
                loss = 88 + 20*Math.log10(frequency) + 40*Math.log10(distance) - 20*Math.log10(ht*hr);
                double Kh = 0;
                    if (frequency > 25 && frequency < 150){
                        Kh = 1.667 - 0.1094 ;
                    } else if (frequency > 150 && frequency < 162) {
                        Kh = 2.25 - 0.1476 ;
                    } else if (frequency > 450 && frequency < 470) {
                        Kh = 3.75 - 0.246 ;
                    }
                loss = loss - Kh;         //以dB为单位的路径损耗

                //计算接收功率
                double Pr = Pt - loss;      //以dBm为单位
                double PrW;
                PrW = Math.pow(10, Pr/10) / 1000;       //以瓦特W为单位的接收功率PrW

                //计算功率密度
                double Pd = PrW / (4 * Math.PI * Math.pow(distance*1000, 2));           //以W/㎡为单位的接收点处的功率密度

                //计算电场强度
                double Z0 = 377;                        //自由空间阻抗值377Ω
                double E = Math.sqrt(Pd * Z0);          //以V/m为单位的电场强度

                //计算磁场强度
                double H = E / Z0;                      //以A/m为单位的磁场强度

                //对路径损耗、接收功率、功率密度、电场强度、磁场强度的标量进行叠加
                totalLoss += loss;
                totalPrW += PrW;
                totalPd += Pd;
                totalE += E;
                totalH += H;
            }
            lossList.add(totalLoss);
            PrWList.add(totalPrW);
            PdList.add(totalPd);
            EList.add(totalE);
            HList.add(totalH);
            totalLoss=0;
            totalPrW=0;
            totalPd=0;
            totalE=0;
            totalH=0;
        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("路径损失", lossList);
        resMap.put("接收功率", PrWList);
        resMap.put("功率密度", PdList);
        resMap.put("电场强度", EList);
        resMap.put("磁场强度", HList);
        resMap.put("多接收机WGS84坐标", list);
        resMap.put("多接收机信息", rxsmodeldata);
        return Result.success(resMap, (long)resMap.size());
    }
    *//**
 *
 * @param distance 通过发射机和接收机计算
 * @param frequency 通过发射机上的天线频率得到
 * @param ht 发射机高度 天线数据库再加个高度字段，通过发射机和接收机上的天线get得到
 * @param hr 接收机高度
 * @param Pt 发射功率 通过发射机get到
 * @param deltaH 修正因子  是一个范围，以5为分界线，问题：这个参数从前端怎么给我
 *               一个方法是通过entity的get给我，但是不在数据库显示。这个参数的影响很小可以去掉（宁哥说的）
 * @return
 *//*
 *//* public static double[] Egli(double distance, double frequency, double ht, double hr, double Pt, double deltaH){

        //计算路径损耗
        double loss;
        loss = 88 + 20*Math.log10(frequency) + 40*Math.log10(distance) - 20*Math.log10(ht*hr);
        double Kh = 0;
        if(deltaH < 5){
            Kh = 0;
        }else {
            if (frequency > 25 && frequency < 150){
                Kh = 1.667 - 0.1094 * deltaH;
            } else if (frequency > 150 && frequency < 162) {
                Kh = 2.25 - 0.1476 * deltaH;
            } else if (frequency > 450 && frequency < 470) {
                Kh = 3.75 - 0.246 * deltaH;
            }
        }
        loss = loss - Kh;         //以dB为单位的路径损耗

        //计算接收功率
        double Pr = Pt - loss;      //以dBm为单位
        double PrW;
        PrW = Math.pow(10, Pr/10) / 1000;       //以瓦特W为单位的接收功率PrW

        //计算功率密度
        double Pd = PrW / (4 * Math.PI * Math.pow(distance*1000, 2));           //以W/㎡为单位的接收点处的功率密度

        //计算电场强度
        double Z0 = 377;                        //自由空间阻抗值377Ω
        double E = Math.sqrt(Pd * Z0);          //以V/m为单位的电场强度

        //计算磁场强度
        double H = E / Z0;                      //以A/m为单位的磁场强度

        double[] parameter = new double[5];
        parameter[0] = loss;
        parameter[1] = PrW;
        parameter[2] = Pd;
        parameter[3] = E;
        parameter[4] = H;

        return parameter;
    }*//*


    //ITU模型
    @GetMapping("/ITU")
    public Result ITU(String rxsName){

        double totalLoss = 0;
        double totalPrW = 0;
        double totalPd = 0;
        double totalE = 0;
        double totalH = 0;
        //获取所有发射机名称
        List<String> txNames = txdataServiceImpl.listAllTxname();
        List<CVector> list = cVectorServiceImpl.heightNotZero(rxsName);
        RxsModelData rxsmodeldata = rxsmodeldataServiceImpl.queryTwoPointPos(rxsName);

        List<Double> lossList = new ArrayList<>();
        List<Double> PrWList = new ArrayList<>();
        List<Double> PdList = new ArrayList<>();
        List<Double> EList = new ArrayList<>();
        List<Double> HList = new ArrayList<>();
        List<Double> distanceList = new ArrayList<>();      //暂时没有用到

        for (CVector cVector : list) {
            for (String txName : txNames) {
                LambdaQueryWrapper<Txdata> txQuery = new LambdaQueryWrapper();
                txQuery.eq(Txdata::getName, txName);
                Txdata txdata = txdataServiceImpl.getOne(txQuery);
                double Pt = txdata.getPower();
                double ht = txdata.getHeight();

                CVector txPosition = new CVector(txdata.getLongitude(), txdata.getLatitude(), ht);

                //获取当前发射机对应天线的相关信息
                LambdaQueryWrapper<Antennas> anteQuery = new LambdaQueryWrapper();
                anteQuery.eq(Antennas::getName, txdata.getAntenna());
                Antennas ante = antennasServiceImpl.getOne(anteQuery);
                double frequency = ante.getFrequency();
                double distance = CalDistance.getEuclideanDistance(cVector, txPosition);

                //计算路径损耗
//                double aefa = 0;
//                double beta = 0;
//                double gama = 0;
//                if(eyesight == "Los"){
//                    aefa = 2.31;
//                    beta = 24.52;
//                    gama = 2.06;
//                } else if(eyesight == "NLos"){
                double  aefa = 3.79;
                double  beta = 21.01;
                double  gama = 1.34;

                double loss;
                loss = beta + 10*aefa*Math.log10(distance) + 10*gama*Math.log10(frequency);   //以dB为单位的路径损耗

                //计算接收功率
                double Pr = Pt - loss;      //以dBm为单位
                double PrW;
                PrW = Math.pow(10, Pr/10) / 1000;       //以瓦特W为单位的接收功率PrW

                //计算功率密度
                double Pd = PrW / (4 * Math.PI * Math.pow(distance*1000, 2));           //以W/㎡为单位的接收点处的功率密度

                //计算电场强度
                double Z0 = 377;                        //自由空间阻抗值377Ω
                double E = Math.sqrt(Pd * Z0);          //以V/m为单位的电场强度

                //计算磁场强度
                double H = E / Z0;                      //以A/m为单位的磁场强度

                //对路径损耗、接收功率、功率密度、电场强度、磁场强度的标量进行叠加
                totalLoss += loss;
                totalPrW += PrW;
                totalPd += Pd;
                totalE += E;
                totalH += H;
            }
            lossList.add(totalLoss);
            PrWList.add(totalPrW);
            PdList.add(totalPd);
            EList.add(totalE);
            HList.add(totalH);
            totalLoss=0;
            totalPrW=0;
            totalPd=0;
            totalE=0;
            totalH=0;
        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("路径损失", lossList);
        resMap.put("接收功率", PrWList);
        resMap.put("功率密度", PdList);
        resMap.put("电场强度", EList);
        resMap.put("磁场强度", HList);
        resMap.put("多接收机WGS84坐标", list);
        resMap.put("多接收机信息", rxsmodeldata);
        return Result.success(resMap, (long)resMap.size());
    }

    *//**
 *
 * @param distance
 * @param frequency
 * @param Pt
 * @param eyesight 视距的设置怎么从前端传过来（这个也一样，简化计算就行）
 * @return
 *//*
 *//*public static double[] ITU(double distance, double frequency, double Pt, String eyesight){

        //计算路径损耗
        double aefa = 0;
        double beta = 0;
        double gama = 0;
        if(eyesight == "Los"){
            aefa = 2.31;
            beta = 24.52;
            gama = 2.06;
        } else if(eyesight == "NLos"){
            aefa = 3.79;
            beta = 21.01;
            gama = 1.34;
        }

        double loss;
        loss = beta + 10*aefa*Math.log10(distance) + 10*gama*Math.log10(frequency);   //以dB为单位的路径损耗

        //计算接收功率
        double Pr = Pt - loss;      //以dBm为单位
        double PrW;
        PrW = Math.pow(10, Pr/10) / 1000;       //以瓦特W为单位的接收功率PrW

        //计算功率密度
        double Pd = PrW / (4 * Math.PI * Math.pow(distance*1000, 2));           //以W/㎡为单位的接收点处的功率密度

        //计算电场强度
        double Z0 = 377;                        //自由空间阻抗值377Ω
        double E = Math.sqrt(Pd * Z0);          //以V/m为单位的电场强度

        //计算磁场强度
        double H = E / Z0;                      //以A/m为单位的磁场强度

        double[] parameter = new double[5];
        parameter[0] = loss;
        parameter[1] = PrW;
        parameter[2] = Pd;
        parameter[3] = E;
        parameter[4] = H;

        return parameter;
    }
*//*
     */
}
