package com.rentalHouseAdmin.rha.modules.sys.mapper.attention;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention;

import java.util.List;

/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:47
 */
public interface AttentionMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> {

    /**
     * 查询列表(分页)
     * @param attention 查询参数
     * @param page 分页参数
     * @return list
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> selectAttentionList(Attention attention, IPage page);

}
