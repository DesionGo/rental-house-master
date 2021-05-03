package com.rentalHouseAdmin.rha.modules.sys.controller.footprint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.modules.sys.service.footprint.FootprintService;

import java.util.List;


/**
 * <p>
 * 足迹表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:48
 */
@RestController
@RequestMapping("footprint/footprint")
public class FootprintController extends BaseController {

    @Autowired
    private FootprintService footprintService;

    @RequiresPermissions("footprint:footprint:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/footprint/footprint");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/footprint/footprint_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint footprint;
        if (id == null) {
            footprint = new com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint();
        } else {
            footprint = footprintService.getById(id);
        }
        mv.addObject("editInfo", footprint);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint footprint) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> page = footprintService.listFootprintPage(footprint);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("footprint:footprint:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint footprint) {
        footprintService.save(footprint);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("footprint:footprint:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        footprintService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("footprint:footprint:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Footprint footprint) {
        footprintService.updateById(footprint);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("footprint:footprint:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        footprintService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(footprintService.getById(id));
    }

}

