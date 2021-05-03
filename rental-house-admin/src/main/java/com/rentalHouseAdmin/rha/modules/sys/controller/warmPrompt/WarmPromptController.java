package com.rentalHouseAdmin.rha.modules.sys.controller.warmPrompt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.modules.sys.entity.warmPrompt.WarmPrompt;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.service.warmPrompt.WarmPromptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


/**
 * <p>
 * 温馨提示表 前端控制器
 * </p>
 * @since 2021-02-11 18:00:08
 */
@RestController
@RequestMapping("warm/prompt")
public class WarmPromptController extends BaseController {

    @Autowired
    private WarmPromptService warmPromptService;

    @RequiresPermissions("warmPrompt:warmPrompt:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/warm/prompt");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/warm/prompt_edit");
        WarmPrompt warmPrompt;
        if (id == null) {
            warmPrompt = new WarmPrompt();
        } else {
            warmPrompt = warmPromptService.getById(id);
        }
        mv.addObject("editInfo", warmPrompt);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(WarmPrompt warmPrompt) {
        Page<WarmPrompt> page = warmPromptService.listPromptPage(warmPrompt);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("warmPrompt:warmPrompt:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(WarmPrompt warmPrompt) {
        warmPromptService.save(warmPrompt);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("warmPrompt:warmPrompt:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        warmPromptService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("warm:prompt:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(com.rentalHouseAdmin.rha.modules.sys.entity.warmPrompt.WarmPrompt warmPrompt) {
        warmPromptService.updateById(warmPrompt);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("warmPrompt:warmPrompt:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        warmPromptService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(warmPromptService.getById(id));
    }

}

