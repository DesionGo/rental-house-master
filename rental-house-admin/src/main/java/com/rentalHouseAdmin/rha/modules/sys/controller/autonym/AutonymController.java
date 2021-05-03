package com.rentalHouseAdmin.rha.modules.sys.controller.autonym;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym;
import com.rentalHouseAdmin.rha.modules.sys.service.autonym.AutonymService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rentalHouseAdmin.rha.common.dto.R;

import java.util.List;


/**
 * <p>
 * 实名表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:48
 */
@RestController
@RequestMapping("autonym/autonym")
public class AutonymController extends BaseController {

    @Autowired
    private AutonymService autonymService;

    @RequiresPermissions("autonym:autonym:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/autonym/autonym");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/autonym/autonym_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym autonym;
        if (id == null) {
            autonym = new com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym();
        } else {
            autonym = autonymService.getById(id);
        }
        mv.addObject("editInfo", autonym);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym autonym) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym> page = autonymService.listAutonymPage(autonym);
        return R.ok(page);
    }

    @RequiresPermissions("autonym:autonym:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym autonym) {
        autonymService.save(autonym);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("autonym:autonym:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        autonymService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("autonym:autonym:edit")
    @PostMapping(value = "edit")
    public R edit(Autonym autonym) {
        autonymService.updateById(autonym);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("autonym:autonym:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        autonymService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(autonymService.getById(id));
    }

}

