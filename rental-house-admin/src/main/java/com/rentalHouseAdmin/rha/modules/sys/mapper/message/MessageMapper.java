package com.rentalHouseAdmin.rha.modules.sys.mapper.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.message.Message;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:49
 */
public interface MessageMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> {

    /**
     * 查询列表(分页)
     * @param message 查询参数
     * @param page 分页参数
     * @return list
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.message.Message> selectMessageList(Message message, IPage page);

}
