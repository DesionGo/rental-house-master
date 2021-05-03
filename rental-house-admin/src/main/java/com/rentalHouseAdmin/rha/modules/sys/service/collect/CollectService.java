package com.rentalHouseAdmin.rha.modules.sys.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface CollectService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> {

    /**
     * 获取列表。分页
     * @param collect 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> listCollectPage(Collect collect);

}
