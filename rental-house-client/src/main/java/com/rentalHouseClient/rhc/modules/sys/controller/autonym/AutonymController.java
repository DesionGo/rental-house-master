package com.rentalHouseClient.rhc.modules.sys.controller.autonym;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.autonym.Autonym;
import com.rentalHouseClient.rhc.modules.sys.service.autonym.AutonymService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("autonym/autonym");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("autonym/autonym_edit");
        Autonym autonym;
        if (id == null) {
            autonym = new Autonym();
        } else {
            autonym = autonymService.getById(id);
        }
        mv.addObject("editInfo", autonym);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Autonym autonym) {
        Page<Autonym> page = autonymService.listAutonymPage(autonym);
        return R.ok(page);
    }

    @RequiresPermissions("autonym:autonym:add")
    @PostMapping(value = "add")
    public R add(Autonym autonym) {
        autonymService.save(autonym);
        return R.ok();
    }

    @RequiresPermissions("autonym:autonym:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        autonymService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("autonym:autonym:edit")
    @PostMapping(value = "edit")
    public R edit(Autonym autonym) {
        autonymService.updateById(autonym);
        return R.ok();
    }

    @RequiresPermissions("autonym:autonym:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        autonymService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(autonymService.getById(id));
    }

}

