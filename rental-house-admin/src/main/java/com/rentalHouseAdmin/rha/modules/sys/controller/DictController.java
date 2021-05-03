package com.rentalHouseAdmin.rha.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dict;
import com.rentalHouseAdmin.rha.modules.sys.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 字典表 前端控制器
 * </p>
 * @since 2019-08-10 16:00:10
 */
@RestController
@RequestMapping("sys/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @RequiresPermissions("sys:dict:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/dict");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/dict_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict;
        if (id == null) {
            dict = new com.rentalHouseAdmin.rha.modules.sys.entity.Dict();
        } else {
            dict = dictService.getById(id);
        }
        mv.addObject("editInfo", dict);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> page = dictService.listDictPage(dict);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("sys:dict:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict) {
        com.rentalHouseAdmin.rha.modules.sys.entity.Dict d = dictService.getByCode(dict.getCode());
        if (d != null) {
            return com.rentalHouseAdmin.rha.common.dto.R.fail("字典码({code})已被使用".replace("{code}", dict.getCode()));
        }
        dictService.save(dict);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dict:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        dictService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dict:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict) {
        Dict d = dictService.getByCode(dict.getCode());
        if (d != null) {
            return com.rentalHouseAdmin.rha.common.dto.R.fail("字典码({code})已被使用".replace("{code}", dict.getCode()));
        }
        dictService.updateById(dict);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dict:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        dictService.deleteWithChildren(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(dictService.getById(id));
    }

}

