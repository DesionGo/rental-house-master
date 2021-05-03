package com.rentalHouseAdmin.rha.modules.sys.service.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.message.Message;
import com.rentalHouseAdmin.rha.modules.sys.mapper.message.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:49
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> implements MessageService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> listMessagePage(com.rentalHouseAdmin.rha.modules.sys.entity.message.Message message) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> page = new Page<>(message.getCurrent(), message.getSize());
        List<Message> messages = baseMapper.selectMessageList(message, page);
        return page.setRecords(messages);
    }

}
