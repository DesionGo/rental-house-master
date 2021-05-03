package com.rentalHouseClient.rhc.modules.sys.controller.evaluate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.evaluate.Evaluate;
import com.rentalHouseClient.rhc.modules.sys.service.evaluate.EvaluateService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
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
@RequestMapping("evaluate/evaluate")
public class EvaluateController extends BaseController {

    @Autowired
    private EvaluateService evaluateService;

    @RequiresPermissions("evaluate:evaluate:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("evaluate/evaluate");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("evaluate/evaluate_edit");
        Evaluate evaluate;
        if (id == null) {
            evaluate = new Evaluate();
        } else {
            evaluate = evaluateService.getById(id);
        }
        mv.addObject("editInfo", evaluate);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Evaluate evaluate) {
        Page<Evaluate> page = evaluateService.listEvaluatePage(evaluate);
        return R.ok(page);
    }

    @RequiresPermissions("evaluate:evaluate:add")
    @PostMapping(value = "add")
    public R add(Evaluate evaluate) {
        evaluateService.save(evaluate);
        return R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        evaluateService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:edit")
    @PostMapping(value = "edit")
    public R edit(Evaluate evaluate) {
        evaluateService.updateById(evaluate);
        return R.ok();
    }

    @RequiresPermissions("evaluate:evaluate:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        evaluateService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(evaluateService.getById(id));
    }

}

