package com.rentalHouseAdmin.rha.modules.sys.controller;

import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.common.utils.ShiroKit;
import com.rentalHouseAdmin.rha.modules.sys.entity.User;
import com.rentalHouseAdmin.rha.modules.sys.service.IMenuService;
import com.rentalHouseAdmin.rha.modules.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/")
    public ModelAndView index() {


        User user = userService.getById(com.rentalHouseAdmin.rha.common.utils.ShiroKit.getUserId());
        return new ModelAndView("index").addObject("authUserInfo", user);
    }

    @GetMapping(value = "home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping(value = "test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }


    @GetMapping(value = "index/menus")
    public com.rentalHouseAdmin.rha.common.dto.R menus() {
        return com.rentalHouseAdmin.rha.common.dto.R.ok(menuService.listUserPermissionMenuWithSubByUserId(com.rentalHouseAdmin.rha.common.utils.ShiroKit.getUserId()));
    }

    @GetMapping(value = "index/navMenus")
    public com.rentalHouseAdmin.rha.common.dto.R navMenus() {
        return R.ok(menuService.listUserPermissionNavMenuByUserId(ShiroKit.getUserId()));
    }

}
