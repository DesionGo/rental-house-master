package com.rentalHouseClient.rhc.modules.sys.service.labelItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseClient.rhc.modules.sys.entity.labelItem.LabelItem;

import java.util.List;

/**
 * <p>
 * 标签匹配表 服务类
 * </p>
 * @since 2021-02-11 19:12:36
 */
public interface LabelItemService extends IService<LabelItem> {

    /**
     * 获取列表。分页
     * @param labelItem 查询参数
     * @return page
     */
    Page<LabelItem> listItemPage(LabelItem labelItem);

    List<LabelItem> selectService(String serviceId);

    void add(LabelItem labelItem);

}
