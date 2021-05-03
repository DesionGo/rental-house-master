package com.rentalHouseAdmin.rha.modules.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.Role;
import com.rentalHouseAdmin.rha.modules.sys.service.IRoleService;
import com.rentalHouseAdmin.rha.modules.sys.vo.RoleMenuVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@RestController
@RequestMapping("sys/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("sys:role:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/role");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id, boolean isRole) {
        ModelAndView mv = new ModelAndView("sys/role_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.Role role;
        if (id == null) {
            role = new com.rentalHouseAdmin.rha.modules.sys.entity.Role();
        } else {
            role = roleService.getById(id);
        }
        mv.addObject("editInfo", role);
        return mv;
    }

    @GetMapping(value = "permission")
    public ModelAndView permission() {
        return new ModelAndView("sys/role_permission");
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.Role role) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Role> page = roleService.listRolePage(role);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @GetMapping(value = "list/tree")
    public com.rentalHouseAdmin.rha.common.dto.R listTree(Long parentId) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(roleService.listRoleByParentId(parentId));
    }

    @RequiresPermissions("sys:role:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.Role role) {
        roleService.save(role);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:role:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Role role) {
        roleService.updateById(role);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:role:permission")
    @PostMapping(value = "set/permission")
    public com.rentalHouseAdmin.rha.common.dto.R setPermission(RoleMenuVO roleMenuVO) {
        roleService.saveOrUpdatePermission(roleMenuVO);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:role:del")
    @PostMapping(value = "remove/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R remove(@PathVariable Long id) {
        roleService.deleteWithChildren(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:role:del")
    @PostMapping(value = "removeBatch")
    public com.rentalHouseAdmin.rha.common.dto.R removeBatch(@RequestParam("ids") List<Long> ids) {
        roleService.deleteWithRoleMenu(ids);
        return R.ok();
    }

}

