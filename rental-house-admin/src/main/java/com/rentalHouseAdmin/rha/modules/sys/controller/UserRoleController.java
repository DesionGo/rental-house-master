package com.rentalHouseAdmin.rha.modules.sys.controller;


import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.UserRole;
import com.rentalHouseAdmin.rha.modules.sys.service.IUserRoleService;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserRoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@RestController
@RequestMapping("sys/userRole")
public class UserRoleController extends BaseController {

    @Autowired
    private IUserRoleService userRoleService;

    @GetMapping(value = "index")
    public ModelAndView index() {
        return new ModelAndView("sys/user_role");
    }

    /**
     * 角色成员列表数据接口
     * @param userRole 参数
     * @return r
     */
    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(UserRole userRole) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(userRoleService.listUserRolePage(userRole));
    }

    @RequiresPermissions("sys:userRole:add")
    @PostMapping(value = "save")
    public com.rentalHouseAdmin.rha.common.dto.R save(UserRoleVO userRoleVO) {
        userRoleService.saveOrUpdateBatchUserRole(userRoleVO);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:userRole:del")
    @PostMapping(value = "removeBatch")
    public com.rentalHouseAdmin.rha.common.dto.R removeBatch(@RequestParam("ids") List<Long> ids) {
        userRoleService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "count")
    public com.rentalHouseAdmin.rha.common.dto.R count(Long roleId) {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(userRoleService.countUserRoleByRoleId(roleId));
    }

    @GetMapping(value = "get/roleNames/{userId}")
    public com.rentalHouseAdmin.rha.common.dto.R getRoleNames(@PathVariable Long userId) {
        return R.ok(userRoleService.getRoleNamesByUserId(userId));
    }

}

