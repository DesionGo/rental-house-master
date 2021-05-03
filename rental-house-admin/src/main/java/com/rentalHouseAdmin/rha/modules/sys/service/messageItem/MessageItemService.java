package com.rentalHouseAdmin.rha.modules.sys.service.messageItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.messageItem.MessageItem;

/**
 * <p>
 * 消息内容表 服务类
 * </p>
 * @since 2021-02-11 19:28:17
 */
public interface MessageItemService extends IService<MessageItem> {

    /**
     * 获取列表。分页
     * @param messageItem 查询参数
     * @return page
     */
    Page<MessageItem> listItemPage(MessageItem messageItem);

}
