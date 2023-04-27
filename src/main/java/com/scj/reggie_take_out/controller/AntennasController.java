package com.scj.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scj.reggie_take_out.common.Result;
import com.scj.reggie_take_out.entity.Antennas;
import com.scj.reggie_take_out.service.AntennasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/antennas")
public class AntennasController {

    @Autowired
    private AntennasService antennasService;

    /**
     * 新增天线
     * @param
     */
    /**
     * http://127.0.0.1:9527/list
     * @return
     */
    @GetMapping("/list")
        public Result list(){
            List<Antennas> AntennasList = antennasService.list();
            return Result.success(AntennasList, Long.valueOf(AntennasList.size()));
        }
    /**
     *
     * @param antenna：天线类型，hdip是半波偶极子天线，smono是短单极子天线，omni是全向天线。
     * @return：返回对应天线信息和总的查询数目
     */
   //查询具体的天线,前端需要查询出大量数据用getmapping
    @GetMapping("/hdiplist")
    public Result hdipList(String antenna){
        List<Antennas> AntennasList = antennasService.hdipList(antenna);
        return Result.success(AntennasList, Long.valueOf(AntennasList.size()));
    }

    @GetMapping("/omnilist")
    public Result omniList(String antenna){
        List<Antennas> AntennasList = antennasService.omniList(antenna);
        return Result.success(AntennasList, Long.valueOf(AntennasList.size()));
    }

    @GetMapping("/smonolist")
    public Result smonoList(String antenna){
        List<Antennas> AntennasList = antennasService.smonoList(antenna);
        return Result.success(AntennasList, Long.valueOf(AntennasList.size()));
    }
    /**
     *
     * @param antennas:新增天线模板到antennas数据库里
     * @return：返回添加成功或失败
     */
    // 新增天线模板,保存到数据库用postmapping
    @PostMapping("/hdipsave")
    public Result hdipSave(@RequestBody Antennas antennas){
        if(!antennasService.hdipSave(antennas)){
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    @PostMapping("/omnisave")
    public Result omniSave(@RequestBody Antennas antennas){
        if(!antennasService.omniSave(antennas)){
            return Result.fail();
        }
        return Result.success(null, 0L);
    }

    @PostMapping("/smonosave")
    public Result smonoSave(@RequestBody Antennas antennas){
        if(!antennasService.smonoSave(antennas)){
            return Result.fail();
        }
        return Result.success(null, 0L);
    }
    /**
     *
     * @param antennasName:天线名称
     * @return；如果天线已存在，返回1001和天线名称重名。如果天线不存在，返回成功（可以继续添加此天线信息）。
     */
    @GetMapping("/isDuplicate")
    public Result isDuplicate(String antennasName){
        // 根据天线的名称进行校验
        LambdaQueryWrapper<Antennas> antennasQueryWrapper = new LambdaQueryWrapper<>();
        antennasQueryWrapper.eq(Antennas::getName, antennasName);
        Antennas antes = antennasService.getOne(antennasQueryWrapper);

        // 如果查询到，返回"天线已存在"
        if(antes != null) {
            return Result.respon(1001, "天线名称重名", 0L, null);
        }
        return Result.success(null, 0L);
    }
    /**
     *
     * @param antennas:在antennas数据库里新增或修改天线信息
     * @return：返回true：新增或修改成功，或false：新增或修改成功
     */
    // 新增或修改
    @PostMapping ("/saveOrMod")
    public boolean saveOrMod(@RequestBody Antennas antennas){
        return antennasService.saveOrUpdate(antennas);
    }
    /**
     *
     * @param id:通过id删除天线信息
     * @return：返回true：删除成功 或false：删除失败
     */
    // 删除
    @GetMapping("/delete")
    public boolean delete(int id) {
        return antennasService.removeById(id);
    }

}
