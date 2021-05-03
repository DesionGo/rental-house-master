package com.rentalHouseAdmin.rha.modules.sys.mapper.labelItem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem;

import java.util.List;

/**
 * <p>
 * 标签匹配表 Mapper 接口
 * </p>
 * @since 2021-02-11 19:12:36
 */
public interface LabelItemMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> {

    /**
     * 查询列表(分页)
     * @param labelItem 查询参数
     * @param page 分页参数
     * @return list
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> selectItemList(LabelItem labelItem, IPage page);

}
