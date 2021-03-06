package com.rentalHouseAdmin.rha.modules.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.Log;
import com.rentalHouseAdmin.rha.modules.sys.service.ILogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-10
 */
@RestController
@RequestMapping("sys/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @RequiresPermissions("sys:log:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/log");
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.Log log) {
        Page<Log> page = logService.listLogPage(log);
        return R.ok(page);
    }

}

