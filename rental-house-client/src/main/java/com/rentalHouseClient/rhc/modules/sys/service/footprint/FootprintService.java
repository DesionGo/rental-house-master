package com.rentalHouseClient.rhc.modules.sys.service.footprint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseClient.rhc.modules.sys.entity.footprint.Footprint;

/**
 * <p>
 * 足迹表 服务类
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface FootprintService extends IService<Footprint> {

    /**
     * 获取列表。分页
     * @param footprint 查询参数
     * @return page
     */
    Page<Footprint> listFootprintPage(Footprint footprint);

}
