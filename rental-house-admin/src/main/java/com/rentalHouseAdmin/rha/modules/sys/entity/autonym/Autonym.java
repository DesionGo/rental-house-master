package com.rentalHouseAdmin.rha.modules.sys.entity.autonym;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.rentalHouseAdmin.rha.common.entity.BaseEntity;

/**
 * <p>
 * 实名表
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("autonym")
public class Autonym extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 身份证
     */
    private String identityCard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 客户用户id
     */
    private String clientUserId;

}
