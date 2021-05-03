package com.rentalHouseClient.rhc.modules.sys.controller.clientUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
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
        ClientUser clientuser;
        if (id == null) {
            clientuser = new ClientUser();
        } else {
            clientuser = clientuserService.getById(id);
        }
        mv.addObject("editInfo", clientuser);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(ClientUser clientUser) {
        Page<ClientUser> page = clientuserService.listUserPage(clientUser);
        return R.ok(page);
    }

    @RequiresPermissions("client:user:add")
    @PostMapping(value = "add")
    public R add(ClientUser clientUser) {

        clientuserService.save(clientUser);
        return R.ok();
    }

    @RequiresPermissions("client:user:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        clientuserService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("client:user:edit")
    @PostMapping(value = "edit")
    public R edit(ClientUser clientUser) {
        clientuserService.updateById(clientUser);
        return R.ok();
    }

    @RequiresPermissions("client:user:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        clientuserService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(clientuserService.getById(id));
    }

}

