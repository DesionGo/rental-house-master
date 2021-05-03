package com.rentalHouseClient.rhc.modules.sys.mapper.warmPrompt;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.warmPrompt.WarmPrompt;

import java.util.List;

/**
 * <p>
 * 温馨提示表 Mapper 接口
 * </p>
 * @since 2021-02-11 18:00:08
 */
public interface WarmPromptMapper extends BaseMapper<WarmPrompt> {

    /**
     * 查询列表(分页)
     * @param warmPrompt 查询参数
     * @param page 分页参数
     * @return list
     */
    List<WarmPrompt> selectPromptList(WarmPrompt warmPrompt, IPage page);

}
