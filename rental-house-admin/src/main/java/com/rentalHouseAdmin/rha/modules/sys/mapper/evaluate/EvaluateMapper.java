package com.rentalHouseAdmin.rha.modules.sys.mapper.evaluate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.evaluate.Evaluate;

import java.util.List;

/**
 * <p>
 * 评价表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface EvaluateMapper extends BaseMapper<Evaluate> {

    /**
     * 查询列表(分页)
     * @param evaluate 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Evaluate> selectEvaluateList(Evaluate evaluate, IPage page);

}
