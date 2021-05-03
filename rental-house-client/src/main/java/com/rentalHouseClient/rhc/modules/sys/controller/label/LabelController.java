package com.rentalHouseClient.rhc.modules.sys.controller.label;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import com.rentalHouseClient.rhc.modules.sys.service.label.LabelService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("label/label");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("label/label_edit");
        Label label;
        if (id == null) {
            label = new Label();
        } else {
            label = labelService.getById(id);
        }
        mv.addObject("editInfo", label);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Label label) {
        Page<Label> page = labelService.listLabelPage(label);
        return R.ok(page);
    }

    @RequiresPermissions("label:label:add")
    @PostMapping(value = "add")
    public R add(Label label) {
        labelService.save(label);
        return R.ok();
    }

    @RequiresPermissions("label:label:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        labelService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("label:label:edit")
    @PostMapping(value = "edit")
    public R edit(Label label) {
        labelService.updateById(label);
        return R.ok();
    }

    @RequiresPermissions("label:label:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        labelService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(labelService.getById(id));
    }

}

