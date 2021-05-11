package com.rentalHouseClient.rhc.modules.sys.mapper.labelItem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.labelItem.LabelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签匹配表 Mapper 接口
 * </p>
 * @since 2021-02-11 19:12:36
 */
public interface LabelItemMapper extends BaseMapper<LabelItem> {

    /**
     * 查询列表(分页)
     * @param labelItem 查询参数
     * @param page 分页参数
     * @return list
     */
    List<LabelItem> selectItemList(LabelItem labelItem, IPage page);

    List<LabelItem> selectService(String serviceId);

    void add(@Param("item") LabelItem labelItem);
}
