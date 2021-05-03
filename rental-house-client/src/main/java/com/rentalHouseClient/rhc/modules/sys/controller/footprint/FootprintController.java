package com.rentalHouseClient.rhc.modules.sys.controller.footprint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.footprint.Footprint;
import com.rentalHouseClient.rhc.modules.sys.service.footprint.FootprintService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("footprint/footprint");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("footprint/footprint_edit");
        Footprint footprint;
        if (id == null) {
            footprint = new Footprint();
        } else {
            footprint = footprintService.getById(id);
        }
        mv.addObject("editInfo", footprint);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Footprint footprint) {
        Page<Footprint> page = footprintService.listFootprintPage(footprint);
        return R.ok(page);
    }

    @RequiresPermissions("footprint:footprint:add")
    @PostMapping(value = "add")
    public R add(Footprint footprint) {
        footprintService.save(footprint);
        return R.ok();
    }

    @RequiresPermissions("footprint:footprint:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        footprintService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("footprint:footprint:edit")
    @PostMapping(value = "edit")
    public R edit(Footprint footprint) {
        footprintService.updateById(footprint);
        return R.ok();
    }

    @RequiresPermissions("footprint:footprint:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        footprintService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(footprintService.getById(id));
    }

}

