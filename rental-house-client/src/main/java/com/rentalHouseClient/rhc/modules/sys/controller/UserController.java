package com.rentalHouseClient.rhc.modules.sys.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.common.util.BaiDuAPIUtil;
import com.rentalHouseClient.rhc.common.utils.CryptionKit;
import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.dto.ClientUserDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.UserEditDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.UserRoleGroupDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.Dept;
import com.rentalHouseClient.rhc.modules.sys.entity.User;
import com.rentalHouseClient.rhc.modules.sys.entity.autonym.Autonym;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.service.IDeptService;
import com.rentalHouseClient.rhc.modules.sys.service.IUserRoleService;
import com.rentalHouseClient.rhc.modules.sys.service.IUserService;
import com.rentalHouseClient.rhc.modules.sys.service.autonym.AutonymService;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import com.rentalHouseClient.rhc.modules.sys.vo.UserQueryVO;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

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

    @Value("${kvf.ip}")
    private String ip;

    @Autowired
    private IUserService userService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private AutonymService autonymService;

    @RequiresPermissions("sys:user:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/user");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("sys/user_edit");
        UserEditDTO userEditDTO = new UserEditDTO();
        UserRoleGroupDTO userRoleGroupDTO = new UserRoleGroupDTO();
        if (id != null) {
            User user = userService.getById(id);
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
        User user = userService.getById(ShiroKit.getUserId());
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping(value = "password")
    public ModelAndView password() {
        return new ModelAndView("sys/user_pwd");
    }

    @GetMapping(value = "list/data")
    public R listData(UserQueryVO queryVO) {
        Page<User> page = userService.listUserPage(queryVO);
        return R.ok(page);
    }

  
    @Transactional
    @PostMapping(value = "add")
    public R add(ClientUserDTO clientUserDTO) {

        ClientUser clientUser =new ClientUser();
        BeanUtils.copyProperties(clientUserDTO,clientUser);
        BaiDuAPIUtil baiDuAPIUtil=new BaiDuAPIUtil();
        if(clientUserDTO.getUserName()==null||clientUserDTO.getUserName().equals("")){
            return R.fail("用户名不能为空！");
        }
        if(clientUserDTO.getEmail()==null||clientUserDTO.getEmail().equals("")){
            return R.fail("邮箱不能为空！");
        }
        if(clientUserDTO.getPassword()==null||clientUserDTO.getPassword().equals("")){
            return R.fail("密码不能为空！");
        }
        if(clientUserDTO.getIdentityCard()==null||clientUserDTO.getIdentityCard().equals("")){
            return R.fail("身份证不能为空！");
        }
        if(clientUserDTO.getName()==null||clientUserDTO.getName().equals("")){
            return R.fail("姓名不能为空！");
        }
        if(clientUserService.getByEmail(clientUser.getEmail())==null||clientUserDTO.getUserName().equals("")){
            clientUser.setProvince(baiDuAPIUtil.baiDuApiProvince(ip));
            clientUser.setCity(baiDuAPIUtil.baiDuApiCity(ip));
            clientUser.setSex(2);
            // 生成用户初始密码并加密
            clientUser.setPassword(CryptionKit.genUserPwd(clientUser.getPassword()));
            Boolean isSure=  clientUserService.saveOrUpdate(clientUser);
            if(isSure){
                ClientUser clientUser1=  clientUserService.getByEmail(clientUser.getEmail());
                Autonym autonym=new Autonym();
                BeanUtils.copyProperties(clientUserDTO,autonym);
                autonym.setId(UUID.randomUUID().toString());
                autonym.setClientUserId(clientUser1.getId());
                autonymService.add(autonym);
            }
        }else {
            return R.fail("邮箱已经被注册了！");
        }
        return R.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Transactional
    @PostMapping(value = "edit")
    public R edit(User user, @RequestParam("roleIds") List<Long> roleIds) {
        user.setDeptId(user.getDeptId() == null ? 0 : user.getDeptId());
        userService.updateById(user);
        userRoleService.saveOrUpdateBatchUserRole(roleIds, user.getId());
        return R.ok();
    }

    @PostMapping(value = "updateInfo")
    public R updateInfo(User user) {
        userService.updateById(user);
        return R.ok();
    }

    @RequiresPermissions("sys:user:del")
    @PostMapping(value = "remove/{id}")
    public R remove(@PathVariable Long id) {
        userService.removeById(id);
        return R.ok();
    }

    @RequiresPermissions("sys:user:del")
    @PostMapping(value = "removeBatch")
    public R removeBatch(@RequestParam("ids") List<Long> ids) {
        userService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 管理员重置某个用户密码
     * @param id 用户ID
     * @return
     */
    @RequiresPermissions("sys:user:reset")
    @PostMapping(value = "{id}/resetPwd")
    public R resetPwd(@PathVariable Long id) {
        userService.updateUserPassword(id, CryptionKit.genUserPwd());
        return R.ok();
    }

    /**
     * 用户修改密码
     * @param oldPassword 旧密码
     * @param password  新密码
     * @return
     */
    @PostMapping(value = "changePwd")
    public R changePwd(String oldPassword, String password) {
        if (StrUtil.isBlank(oldPassword) && StrUtil.isBlank(password)) {
            return R.fail("修改失败，非法的参数");
        }
        // 用户修改密码
       String s= ShiroKit.getUserId();
        ClientUser clientUser = clientUserService.getById(ShiroKit.getUserId());
        oldPassword = CryptionKit.genUserPwd(oldPassword);
        if (clientUser.getPassword().equals(oldPassword)) {
            password = CryptionKit.genUserPwd(password);
            if (clientUser.getPassword().equals(password)) {
                return R.fail("新密码不能与旧密码相同");
            }
        } else {
            return R.fail("旧密码不正确");
        }
        clientUserService.updateUserPassword(ShiroKit.getUserId(), password);
        return R.ok();
    }

}

