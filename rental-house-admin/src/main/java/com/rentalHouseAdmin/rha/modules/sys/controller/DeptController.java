package com.rentalHouseAdmin.rha.modules.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dept;
import com.rentalHouseAdmin.rha.modules.sys.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@RestController
@RequestMapping("sys/dept")
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("sys:dept:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/dept");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/dept_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.Dept dept;
        if (id == null) {
            dept = new com.rentalHouseAdmin.rha.modules.sys.entity.Dept();
        } else {
            dept = deptService.getById(id);
        }
        mv.addObject("editInfo", dept);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.Dept dept) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> page = deptService.listDeptPage(dept);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @GetMapping(value = "list/tree")
    public com.rentalHouseAdmin.rha.common.dto.R listTree(Long parentId) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(deptService.listDeptByParentId(parentId));
    }

    @RequiresPermissions("sys:dept:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.Dept dept) {
        deptService.save(dept);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dept:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Dept dept) {
        deptService.updateById(dept);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dept:del")
    @PostMapping(value = "remove/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R remove(@PathVariable Long id) {
        deptService.deleteWithChildren(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:dept:del")
    @PostMapping(value = "removeBatch")
    public com.rentalHouseAdmin.rha.common.dto.R removeBatch(@RequestParam("ids") List<Long> ids) {
        deptService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(deptService.getById(id));
    }
}

