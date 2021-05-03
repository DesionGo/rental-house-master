package com.rentalHouseAdmin.rha.modules.sys.controller.evaluate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate;
import com.rentalHouseAdmin.rha.modules.sys.service.evaluate.EvaluateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 评价表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:48
 */
@RestController
@RequestMapping("evaluate")
public class EvaluateController extends BaseController {

    @Autowired
    private EvaluateService evaluateService;

    @RequiresPermissions("evaluate:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/evaluate/evaluate");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/evaluate/evaluate_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate evaluate;
        if (id == null) {
            evaluate = new com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate();
        } else {
            evaluate = evaluateService.getById(id);
        }
        mv.addObject("editInfo", evaluate);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate evaluate) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate> page = evaluateService.listEvaluatePage(evaluate);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("evaluate:evaluate:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate evaluate) {
        evaluateService.save(evaluate);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        evaluateService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Evaluate evaluate) {
        evaluateService.updateById(evaluate);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        evaluateService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(evaluateService.getById(id));
    }

}

