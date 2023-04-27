package com.scj.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.R;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Model;
import com.scj.reggie_take_out.service.ModelService;
import com.scj.reggie_take_out.service.impl.ModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.nio.file.Files.size;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelServiceImpl modelServiceImpl;

    @Value("${fileSystem.device-path}")
    private String devicePath;



    /**
    *文件导入
    * @param file
    * @return
     */

    @PostMapping("/uploadDevice")
    public R<String> uploadDevice(MultipartFile file) {
        //获取原始的文件名以及后缀
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 使用UUID重新生成文件名，避免上传的文件的重名
        String fileName = UUID.randomUUID().toString() + suffix;

        try {
            System.out.println(devicePath);
            file.transferTo(new File(devicePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 查询模型信息
     * @return：返回具体模型信息和结果条数
     */
    @GetMapping("/list")
    public Result list(){
        List<Model> modelList = modelServiceImpl.listAllModel();
        return Result.success(modelList, Long.valueOf(modelList.size()));
    }

    /**
     *
     * @param model：向model数据库添加信息
     * @return：返回成功或失败
     */
    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody Model model){
        if(!modelServiceImpl.save(model)){
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    /**
     *每次移动模型都能够修改经纬度信息
     * @param model
     * @return；返回成功或失败
     */
    //修改部分数据
    @PatchMapping("/mod")
    public Result mod(@RequestBody Model model){
        modelServiceImpl.updateLocation(model.getFlag(), model.getLongitude(), model.getLatitude());
        return Result.success(null, 0L);
    }

    /**
     * 向model数据库里新增或修改信息
     * @param model
     * @return:返回true或falae
     */
    @PostMapping ("/saveOrMod")
    public boolean saveOrMod(@RequestBody Model model){
        return modelService.saveOrUpdate(model);
    }

    /**
     * 根据id删除模型信息
     * @param id
     * @return：返回true：删除成功 或false：删除失败
     */
    @GetMapping("/delete")
    public boolean delete(int id) {
        return modelServiceImpl.removeModelById(id);
    }

    /**
     *在model数据库查询指定模型名称的信息
     * @param model
     * @return
     */
    // 查询（模糊、匹配）
    @PostMapping("/listP")
    public List<Model> listP(@RequestBody Model model) {
        LambdaQueryWrapper<Model> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Model::getName,model.getName());
        return modelService.list(lambdaQueryWrapper);
    }

    @GetMapping("/showDetails")
    public Model ShowDetails(String flag) {
        LambdaQueryWrapper<Model> flagQueryWrapper = new LambdaQueryWrapper();
        flagQueryWrapper.eq(Model::getFlag,flag);
        Model mod = modelService.getOne(flagQueryWrapper);
        return null;
    }

    // 分页拦截器
//    @PostMapping("/listPage")
//    public Result listPage(@RequestBody QueryPageParam query){
//        HashMap param = query.getParam();
//        String name = (String)param.get("name");
//        Page<Model> page = new Page();
//        page.setCurrent(query.getPageNum());
//        page.setSize(query.getPageSize());
//
//        LambdaQueryWrapper<Model> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.like(Model::getName,name);
//
//        IPage result = modelService.page(page, lambdaQueryWrapper);
//        System.out.println("total=="+result.getTotal());
//        return Result.success(result.getRecords(), result.getTotal());
//    }
}




