package com.rentalHouseAdmin.rha.modules.sys.service.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.message.Message;

/**
 * <p>
 * 消息表 服务类
 * </p>
 * @since 2021-02-11 17:59:49
 */
public interface MessageService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> {

    /**
     * 获取列表。分页
     * @param message 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> listMessagePage(Message message);

}
