package com.rentalHouseClient.rhc.modules.sys.mapper.label;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 * @since 2021-02-11 19:11:13
 */
public interface LabelMapper extends BaseMapper<Label> {

    /**
     * 查询列表(分页)
     * @param label 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Label> selectLabelList(Label label, IPage page);

}
