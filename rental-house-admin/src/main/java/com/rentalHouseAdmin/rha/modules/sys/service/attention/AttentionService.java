package com.rentalHouseAdmin.rha.modules.sys.service.attention;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention;

/**
 * <p>
 * 关注表 服务类
 * </p>
 * @since 2021-02-11 17:59:47
 */
public interface AttentionService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> {

    /**
     * 获取列表。分页
     * @param attention 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> listAttentionPage(Attention attention);

}
