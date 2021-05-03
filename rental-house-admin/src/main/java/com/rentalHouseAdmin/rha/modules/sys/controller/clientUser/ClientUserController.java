package com.rentalHouseAdmin.rha.modules.sys.controller.clientUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseAdmin.rha.modules.sys.service.clientUser.ClientUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


/**
 * <p>
 * 客户用户 前端控制器
 * </p>
 * @since 2021-01-29 11:13:05
 */
@RestController
@RequestMapping("client/user")
public class ClientUserController extends BaseController {

    @Autowired
    private ClientUserService clientuserService;

    @RequiresPermissions("client:user:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/client/user");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("sys/client/user_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser clientuser;
        if (id == null) {
            clientuser = new com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser();
        } else {
            clientuser = clientuserService.getById(id);
        }
        mv.addObject("editInfo", clientuser);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser clientUser) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> page = clientuserService.listUserPage(clientUser);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("client:user:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser clientUser) {

        clientuserService.save(clientUser);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("client:user:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<Long> ids) {
        clientuserService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("client:user:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(ClientUser clientUser) {
        clientuserService.updateById(clientUser);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("client:user:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        clientuserService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(clientuserService.getById(id));
    }

}

