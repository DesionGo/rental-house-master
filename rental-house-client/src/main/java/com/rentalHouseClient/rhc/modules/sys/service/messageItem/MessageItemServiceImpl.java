package com.rentalHouseClient.rhc.modules.sys.service.messageItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.messageItem.MessageItem;
import com.rentalHouseClient.rhc.modules.sys.mapper.messageItem.MessageItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息内容表 服务实现类
 * </p>
 * @since 2021-02-11 19:28:17
 */
@Service
public class MessageItemServiceImpl extends ServiceImpl<MessageItemMapper, MessageItem> implements MessageItemService {

    @Override
    public Page<MessageItem> listItemPage(MessageItem messageItem) {
        Page<MessageItem> page = new Page<>(messageItem.getCurrent(), messageItem.getSize());
        List<MessageItem> items = baseMapper.selectItemList(messageItem, page);
        return page.setRecords(items);
    }

}
