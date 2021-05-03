package com.rentalHouseAdmin.rha.modules.sys.service.labelItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem;

/**
 * <p>
 * 标签匹配表 服务类
 * </p>
 * @since 2021-02-11 19:12:36
 */
public interface LabelItemService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> {

    /**
     * 获取列表。分页
     * @param labelItem 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> listItemPage(LabelItem labelItem);

}
