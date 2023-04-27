package com.scj.reggie_take_out.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scj.reggie_take_out.common.QueryPageParam;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.entity.Txdata;
import com.scj.reggie_take_out.service.TxdataService;
import com.scj.reggie_take_out.service.impl.TxdataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyb
 * @since 2023-01-01
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/txdata")
public class TxdataController {

    @Autowired
    private TxdataService txdataService;
    @Autowired
    private TxdataServiceImpl txdataServiceImpl;


    //查询所有的发射机属性信息
    @GetMapping("/list")
    public List<Txdata> list(){
        return txdataServiceImpl.listAllTxdata();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Txdata txdata){
        if(!txdataServiceImpl.save(txdata)){
            return Result.fail();
        }
        return Result.success(null, 0L);
    }
    //修改
    @PatchMapping("/mod")
    public Result mod(@RequestBody Txdata txdata){
        txdataServiceImpl.updateLocation(txdata.getFlag(), txdata.getLongitude(), txdata.getLatitude());
        return Result.success(null, 0L);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Txdata txdata){
        return txdataService.saveOrUpdate(txdata);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return txdataServiceImpl.removeTxdataById(id);
    }
    //查询（模糊、匹配）
    @PostMapping("/listP")
    public List<Txdata> listP(@RequestBody Txdata txdata){
        LambdaQueryWrapper<Txdata> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Txdata::getName, txdata.getName());
        return txdataService.list(lambdaQueryWrapper);
    }


    //分页查询
    @PostMapping("/listPage")
    public List<Txdata> listPage(@RequestBody QueryPageParam query){
        /*System.out.println("num===" + query.getPageNum());      //第一个参数
        System.out.println("size===" + query.getPageSize());    //第二个参数*/
        HashMap param = query.getParam();                       //第三个参数
        /*System.out.println("num==="+(String) param.get("name"));
        System.out.println("no==="+(String) param.get("no"));*/
        String name = (String)param.get("name");

        Page<Txdata> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Txdata> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Txdata::getName, name);
        IPage result = txdataService.page(page, lambdaQueryWrapper);
        System.out.println("total===" + result.getTotal());
        return result.getRecords();
    }


    //后端数据封装
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Txdata> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Txdata> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Txdata::getName, name);
        IPage result = txdataService.page(page, lambdaQueryWrapper);
        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

    /**
     *
     * @param txName:发射机名称
     * @return；如果发射机已存在，返回1201和天线名称重名。如果发射机不存在，返回成功（可以继续添加此发射机信息）。
     */
    @GetMapping("/isDuplicate")
    public Result isDuplicate(String txName){
        // 根据天线的名称进行校验
        LambdaQueryWrapper<Txdata> txQueryWrapper = new LambdaQueryWrapper<>();
        txQueryWrapper.eq(Txdata::getName, txName);
        Txdata txdata = txdataService.getOne(txQueryWrapper);

        // 如果查询到，返回"发射机已存在"
        if(txdata != null) {
            return Result.respon(1001, "发射机名称重名", 0L, null);
        }
        return Result.success(null, 0L);
    }
}
