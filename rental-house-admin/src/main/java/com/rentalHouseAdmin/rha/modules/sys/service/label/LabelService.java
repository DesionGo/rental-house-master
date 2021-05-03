package com.rentalHouseAdmin.rha.modules.sys.service.label;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.label.Label;

/**
 * <p>
 * 标签表 服务类
 * </p>
 * @since 2021-02-11 19:11:13
 */
public interface LabelService extends IService<Label> {

    /**
     * 获取列表。分页
     * @param label 查询参数
     * @return page
     */
    Page<Label> listLabelPage(Label label);

}
