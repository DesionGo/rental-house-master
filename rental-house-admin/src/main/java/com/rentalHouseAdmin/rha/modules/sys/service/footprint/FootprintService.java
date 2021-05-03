package com.rentalHouseAdmin.rha.modules.sys.service.footprint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint;

/**
 * <p>
 * 足迹表 服务类
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface FootprintService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> {

    /**
     * 获取列表。分页
     * @param footprint 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> listFootprintPage(Footprint footprint);

}
