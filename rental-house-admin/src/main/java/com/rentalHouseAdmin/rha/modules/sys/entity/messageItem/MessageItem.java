package com.rentalHouseAdmin.rha.modules.sys.entity.messageItem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rentalHouseAdmin.rha.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息内容表
 * </p>
 * @since 2021-02-11 19:28:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("message_item")
public class MessageItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 是否查看（1.未查看，2、已读）
     */
    private Integer isLook;

    /**
     * 发送方
     */
    private String sendId;

}
