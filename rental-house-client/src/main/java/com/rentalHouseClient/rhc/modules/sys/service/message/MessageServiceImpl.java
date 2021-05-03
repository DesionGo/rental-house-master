package com.rentalHouseClient.rhc.modules.sys.service.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.message.Message;
import com.rentalHouseClient.rhc.modules.sys.mapper.message.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:49
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public Page<Message> listMessagePage(Message message) {
        Page<Message> page = new Page<>(message.getCurrent(), message.getSize());
        List<Message> messages = baseMapper.selectMessageList(message, page);
        return page.setRecords(messages);
    }

}
