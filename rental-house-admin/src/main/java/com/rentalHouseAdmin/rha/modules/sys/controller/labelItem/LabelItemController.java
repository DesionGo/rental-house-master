package com.rentalHouseAdmin.rha.modules.sys.controller.labelItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rentalHouseAdmin.rha.modules.sys.service.labelItem.LabelItemService;

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
    private  LabelItemService  labelitemService;

    @RequiresPermissions("labelItem:labelItem:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/label/item");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/label/item_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem labelItem;
        if (id == null) {
            labelItem = new com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem();
        } else {
            labelItem = labelitemService.getById(id);
        }
        mv.addObject("editInfo", labelItem);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem labelItem) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> page = labelitemService.listItemPage(labelItem);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("labelItem:labelItem:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem labelItem) {
        labelitemService.save(labelItem);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        labelitemService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(LabelItem labelItem) {
        labelitemService.updateById(labelItem);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("labelItem:labelItem:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        labelitemService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(labelitemService.getById(id));
    }

}

