package com.rentalHouseAdmin.rha.modules.sys.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.common.utils.CryptionKit;
import com.rentalHouseAdmin.rha.common.utils.ShiroKit;
import com.rentalHouseAdmin.rha.modules.sys.dto.UserEditDTO;
import com.rentalHouseAdmin.rha.modules.sys.dto.UserRoleGroupDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dept;
import com.rentalHouseAdmin.rha.modules.sys.entity.User;
import com.rentalHouseAdmin.rha.modules.sys.service.IDeptService;
import com.rentalHouseAdmin.rha.modules.sys.service.IUserRoleService;
import com.rentalHouseAdmin.rha.modules.sys.service.IUserService;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserQueryVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserRoleService userRoleService;

    @RequiresPermissions("sys:user:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/user");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("sys/user_edit");
        com.rentalHouseAdmin.rha.modules.sys.dto.UserEditDTO userEditDTO = new UserEditDTO();
        com.rentalHouseAdmin.rha.modules.sys.dto.UserRoleGroupDTO userRoleGroupDTO = new UserRoleGroupDTO();
        if (id != null) {
            com.rentalHouseAdmin.rha.modules.sys.entity.User user = userService.getById(id);
            Dept dept = deptService.getById(user.getDeptId());
            userRoleGroupDTO = userRoleService.getUserRoleGroupDTOByUserId(id);
            BeanUtil.copyProperties(user, userEditDTO);
            userEditDTO.setDeptName(dept == null ? "" : dept.getName());
        }
        userEditDTO.setUserRole(userRoleGroupDTO);
        mv.addObject("editInfo", userEditDTO);
        return mv;
    }

    @GetMapping(value = "info")
    public ModelAndView info() {
        ModelAndView mv = new ModelAndView("sys/user_info");
        com.rentalHouseAdmin.rha.modules.sys.entity.User user = userService.getById(com.rentalHouseAdmin.rha.common.utils.ShiroKit.getUserId());
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping(value = "password")
    public ModelAndView password() {
        return new ModelAndView("sys/user_pwd");
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(UserQueryVO queryVO) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.User> page = userService.listUserPage(queryVO);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("sys:user:add")
    @Transactional
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.User user, @RequestParam("roleIds") List<Long> roleIds) {
        user.setDeptId(user.getDeptId() == null ? 0 : user.getDeptId());
        // 生成用户初始密码并加密
        user.setPassword(com.rentalHouseAdmin.rha.common.utils.CryptionKit.genUserPwd());
        userService.saveOrUpdate(user);
        userRoleService.saveOrUpdateBatchUserRole(roleIds, user.getId());
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Transactional
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(com.rentalHouseAdmin.rha.modules.sys.entity.User user, @RequestParam("roleIds") List<Long> roleIds) {
        user.setDeptId(user.getDeptId() == null ? 0 : user.getDeptId());
        userService.updateById(user);
        userRoleService.saveOrUpdateBatchUserRole(roleIds, user.getId());
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @PostMapping(value = "updateInfo")
    public com.rentalHouseAdmin.rha.common.dto.R updateInfo(com.rentalHouseAdmin.rha.modules.sys.entity.User user) {
        userService.updateById(user);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:user:del")
    @PostMapping(value = "remove/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R remove(@PathVariable Long id) {
        userService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("sys:user:del")
    @PostMapping(value = "removeBatch")
    public com.rentalHouseAdmin.rha.common.dto.R removeBatch(@RequestParam("ids") List<String> ids) {
        userService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 管理员重置某个用户密码
     * @param id 用户ID
     * @return
     */
    @RequiresPermissions("sys:user:reset")
    @PostMapping(value = "{id}/resetPwd")
    public com.rentalHouseAdmin.rha.common.dto.R resetPwd(@PathVariable String id) {
        userService.updateUserPassword(id, com.rentalHouseAdmin.rha.common.utils.CryptionKit.genUserPwd());
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 用户修改密码
     * @param oldPassword 旧密码
     * @param password  新密码
     * @return
     */
    @PostMapping(value = "changePwd")
    public com.rentalHouseAdmin.rha.common.dto.R changePwd(String oldPassword, String password) {
        if (StrUtil.isBlank(oldPassword) && StrUtil.isBlank(password)) {
            return com.rentalHouseAdmin.rha.common.dto.R.fail("修改失败，非法的参数");
        }
        // 用户修改密码
        User user = userService.getById(com.rentalHouseAdmin.rha.common.utils.ShiroKit.getUserId());
        oldPassword = com.rentalHouseAdmin.rha.common.utils.CryptionKit.genUserPwd(oldPassword);
        if (user.getPassword().equals(oldPassword)) {
            password = CryptionKit.genUserPwd(password);
            if (user.getPassword().equals(password)) {
                return com.rentalHouseAdmin.rha.common.dto.R.fail("新密码不能与旧密码相同");
            }
        } else {
            return com.rentalHouseAdmin.rha.common.dto.R.fail("旧密码不正确");
        }
        userService.updateUserPassword(ShiroKit.getUserId(), password);
        return R.ok();
    }

}

