package com.rentalHouseAdmin.rha.modules.sys.service.warmPrompt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.warmPrompt.WarmPrompt;


/**
 * <p>
 * 温馨提示表 服务类
 * </p>
 * @since 2021-02-11 18:00:08
 */
public interface WarmPromptService extends IService<WarmPrompt> {

    /**
     * 获取列表。分页
     * @param warmPrompt 查询参数
     * @return page
     */
    Page<WarmPrompt> listPromptPage(WarmPrompt warmPrompt);

}
