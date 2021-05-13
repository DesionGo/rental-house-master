package com.rentalHouseAdmin.rha.modules.sys.controller.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.service.collect.CollectService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:48
 */
@RestController
@RequestMapping("collect/collect")
public class CollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @RequiresPermissions("collect:collect:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/collect/collect");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/collect/collect_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect collect;
        if (id == null) {
            collect = new com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect();
        } else {
            collect = collectService.getById(id);
        }
        mv.addObject("editInfo", collect);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect collect) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> page = collectService.listCollectPage(collect);
        return R.ok(page);
    }

    @RequiresPermissions("collect:collect:add")
    @PostMapping(value = "add")
    public R add(com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect collect) {

        collectService.save(collect);
        return R.ok();
    }

    @RequiresPermissions("collect:collect:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<String> ids) {
        collectService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("collect:collect:edit")
    @PostMapping(value = "edit")
    public R edit(Collect collect) {

        collectService.updateById(collect);
        return R.ok();
    }

    @RequiresPermissions("collect:collect:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable String id) {
        collectService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(collectService.getById(id));
    }

}

