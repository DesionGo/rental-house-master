package com.rentalHouseAdmin.rha.modules.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.Menu;
import com.rentalHouseAdmin.rha.modules.sys.service.IMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@RestController
@RequestMapping("sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("sys:menu:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/menu");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/menu_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu;
        if (id == null) {
            menu = new com.rentalHouseAdmin.rha.modules.sys.entity.Menu();
        } else {
            menu = menuService.getById(id);
        }
        mv.addObject("editInfo", menu);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> page = menuService.listMenuPage(menu);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @GetMapping(value = "list/treeData")
    public com.rentalHouseAdmin.rha.common.dto.R listTreeData(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(menuService.listMenuTree(menu));
    }

    @GetMapping(value = "list/tree")
    public com.rentalHouseAdmin.rha.common.dto.R listTree(Long id) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(menuService.listMenuByParentId(id));
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping(value = "save")
    public com.rentalHouseAdmin.rha.common.dto.R save(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        menuService.saveOrUpdate(menu);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:menu:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        menuService.save(menu);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Menu menu) {
        menuService.updateById(menu);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:menu:del")
    @PostMapping(value = "remove/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R remove(@PathVariable Long id) {
        menuService.deleteWithChildren(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:menu:del")
    @PostMapping(value = "removeBatch")
    public com.rentalHouseAdmin.rha.common.dto.R removeBatch(@RequestParam("ids") List<Long> ids) {
        menuService.deleteWithRoleMenu(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 角色菜单列表接口
     * @param roleId 角色ID
     * @return
     */
    @GetMapping(value = "role/tree")
    public com.rentalHouseAdmin.rha.common.dto.R roleTree(Long roleId) {
        return R.ok(menuService.listRoleMenu(roleId));
    }

}

