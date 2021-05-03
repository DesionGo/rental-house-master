package com.rentalHouseClient.rhc.modules.sys.controller.labelItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.labelItem.LabelItem;
import com.rentalHouseClient.rhc.modules.sys.service.labelItem.LabelItemService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 标签匹配表 前端控制器
 * </p>
 * @since 2021-02-11 19:12:36
 */
@RestController
@RequestMapping("labelItem/LabelItem")
public class LabelItemController extends BaseController {

    @Autowired
    private LabelItemService labelitemService;

    @RequiresPermissions("labelItem:labelItem:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("labelItem/labelItem");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("labelItem/labelItem_edit");
        LabelItem labelItem;
        if (id == null) {
            labelItem = new LabelItem();
        } else {
            labelItem = labelitemService.getById(id);
        }
        mv.addObject("editInfo", labelItem);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(LabelItem labelItem) {
        Page<LabelItem> page = labelitemService.listItemPage(labelItem);
        return R.ok(page);
    }

    @RequiresPermissions("labelItem:labelItem:add")
    @PostMapping(value = "add")
    public R add(LabelItem labelItem) {
        labelitemService.save(labelItem);
        return R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        labelitemService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:edit")
    @PostMapping(value = "edit")
    public R edit(LabelItem labelItem) {
        labelitemService.updateById(labelItem);
        return R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        labelitemService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(labelitemService.getById(id));
    }

}

