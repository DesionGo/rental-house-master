package com.rentalHouseClient.rhc.modules.sys.service.evaluate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseClient.rhc.modules.sys.entity.evaluate.Evaluate;

/**
 * <p>
 * 评价表 服务类
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface EvaluateService extends IService<Evaluate> {

    /**
     * 获取列表。分页
     * @param evaluate 查询参数
     * @return page
     */
    Page<Evaluate> listEvaluatePage(Evaluate evaluate);

}
