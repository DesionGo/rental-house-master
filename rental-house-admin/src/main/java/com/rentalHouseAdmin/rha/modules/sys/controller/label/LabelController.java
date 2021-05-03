package com.rentalHouseAdmin.rha.modules.sys.controller.label;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rentalHouseAdmin.rha.modules.sys.entity.label.Label;
import com.rentalHouseAdmin.rha.modules.sys.service.label.LabelService;

import java.util.List;


/**
 * <p>
 * 标签表 前端控制器
 * </p>
 * @since 2021-02-11 19:11:13
 */
@RestController
@RequestMapping("label/label")
public class LabelController extends BaseController {

    @Autowired
    private LabelService labelService;

    @RequiresPermissions("label:label:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/label/label");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/label/label_edit");
        Label label;
        if (id == null) {
            label = new com.rentalHouseAdmin.rha.modules.sys.entity.label.Label();
        } else {
            label = labelService.getById(id);
        }
        mv.addObject("editInfo", label);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(Label label) {
        Page<Label> page = labelService.listLabelPage(label);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("label:label:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(Label label) {
        labelService.save(label);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("label:label:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        labelService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("label:label:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(com.rentalHouseAdmin.rha.modules.sys.entity.label.Label label) {
        labelService.updateById(label);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("label:label:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        labelService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(labelService.getById(id));
    }

}

