package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.Log;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-10
 */
public interface ILogService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.Log> {

    Page<com.rentalHouseAdmin.rha.modules.sys.entity.Log> listLogPage(Log log);

}
