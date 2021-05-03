package com.rentalHouseClient.rhc.modules.sys.mapper.messageItem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.messageItem.MessageItem;

import java.util.List;

/**
 * <p>
 * 消息内容表 Mapper 接口
 * </p>
 * @since 2021-02-11 19:28:17
 */
public interface MessageItemMapper extends BaseMapper<MessageItem> {

    /**
     * 查询列表(分页)
     * @param messageItem 查询参数
     * @param page 分页参数
     * @return list
     */
    List<MessageItem> selectItemList(MessageItem messageItem, IPage page);

}
