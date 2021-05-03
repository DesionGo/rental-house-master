package com.rentalHouseAdmin.rha.modules.sys.mapper.collect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect;

import java.util.List;

/**
 * <p>
 * 收藏表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface CollectMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> {

    /**
     * 查询列表(分页)
     * @param collect 查询参数
     * @param page 分页参数
     * @return list
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> selectCollectList(Collect collect, IPage page);

}
