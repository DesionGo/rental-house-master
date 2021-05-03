package com.rentalHouseClient.rhc.modules.sys.entity.warmPrompt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rentalHouseClient.rhc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 温馨提示表
 * </p>
 * @since 2021-02-11 18:00:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("warm_prompt")
public class WarmPrompt extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 提醒文字
     */
    private String remindWords;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 删除标识 0正常 -1删除
     */
    private String status;

    /**
     * 删除时间
     */
    private String deleteTime;

    /**
     * 新增时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 新增人姓名
     */
    private String createName;

    /**
     * 新增人
     */
    private String createUser;

}
