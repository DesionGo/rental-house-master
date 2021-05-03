package com.rentalHouseClient.rhc.modules.sys.mapper.footprint;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.footprint.Footprint;

import java.util.List;

/**
 * <p>
 * 足迹表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface FootprintMapper extends BaseMapper<Footprint> {

    /**
     * 查询列表(分页)
     * @param footprint 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Footprint> selectFootprintList(Footprint footprint, IPage page);

}
