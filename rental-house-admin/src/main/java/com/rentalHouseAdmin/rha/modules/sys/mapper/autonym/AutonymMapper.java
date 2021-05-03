package com.rentalHouseAdmin.rha.modules.sys.mapper.autonym;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.autonym.Autonym;

import java.util.List;

/**
 * <p>
 * 实名表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface AutonymMapper extends BaseMapper<Autonym> {

    /**
     * 查询列表(分页)
     * @param autonym 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Autonym> selectAutonymList(Autonym autonym, IPage page);

}
