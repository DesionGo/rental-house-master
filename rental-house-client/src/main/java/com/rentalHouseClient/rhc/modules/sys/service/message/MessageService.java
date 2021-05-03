package com.rentalHouseClient.rhc.modules.sys.service.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseClient.rhc.modules.sys.entity.message.Message;

/**
 * <p>
 * 消息表 服务类
 * </p>
 * @since 2021-02-11 17:59:49
 */
public interface MessageService extends IService<Message> {

    /**
     * 获取列表。分页
     * @param message 查询参数
     * @return page
     */
    Page<Message> listMessagePage(Message message);

}
