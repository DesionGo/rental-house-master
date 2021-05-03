package com.rentalHouseClient.rhc.modules.sys.controller.warmPrompt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.warmPrompt.WarmPrompt;
import com.rentalHouseClient.rhc.modules.sys.service.warmPrompt.WarmPromptService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
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
        return new ModelAndView("warmPrompt/warmPrompt");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("warmPrompt/warmPrompt_edit");
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
    public R listData(WarmPrompt warmPrompt) {
        Page<WarmPrompt> page = warmPromptService.listPromptPage(warmPrompt);
        return R.ok(page);
    }

    @RequiresPermissions("warmPrompt:warmPrompt:add")
    @PostMapping(value = "add")
    public R add(WarmPrompt warmPrompt) {
        warmPromptService.save(warmPrompt);
        return R.ok();
    }

    @RequiresPermissions("warmPrompt:warmPrompt:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        warmPromptService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("warm:prompt:edit")
    @PostMapping(value = "edit")
    public R edit(WarmPrompt warmPrompt) {
        warmPromptService.updateById(warmPrompt);
        return R.ok();
    }

    @RequiresPermissions("warmPrompt:warmPrompt:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        warmPromptService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(warmPromptService.getById(id));
    }

}

