package com.rentalHouseClient.rhc.modules.sys.controller.attention;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.service.attention.AttentionService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.modules.sys.entity.attention.Attention;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 关注表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:47
 */
@RestController
@RequestMapping("attention/attention")
public class AttentionController extends BaseController {

    @Autowired
    private AttentionService attentionService;

    @RequiresPermissions("attention:attention:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("attention/attention");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("attention/attention_edit");
        Attention attention;
        if (id == null) {
            attention = new Attention();
        } else {
            attention = attentionService.getById(id);
        }
        mv.addObject("editInfo", attention);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Attention attention) {
        Page<Attention> page = attentionService.listAttentionPage(attention);
        return R.ok(page);
    }

    @RequiresPermissions("attention:attention:add")
    @PostMapping(value = "add")
    public R add(Attention attention) {
        attentionService.save(attention);
        return R.ok();
    }

    @RequiresPermissions("attention:attention:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        attentionService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("attention:attention:edit")
    @PostMapping(value = "edit")
    public R edit(Attention attention) {
        attentionService.updateById(attention);
        return R.ok();
    }

    @RequiresPermissions("attention:attention:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        attentionService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(attentionService.getById(id));
    }

}

