package com.rentalHouseAdmin.rha.modules.sys.service.autonym;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym;

/**
 * <p>
 * 实名表 服务类
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface AutonymService extends IService<Autonym> {

    /**
     * 获取列表。分页
     * @param autonym 查询参数
     * @return page
     */
    Page<Autonym> listAutonymPage(Autonym autonym);

}
