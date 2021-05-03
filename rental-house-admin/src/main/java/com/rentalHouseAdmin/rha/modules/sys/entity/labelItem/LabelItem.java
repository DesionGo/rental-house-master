package com.rentalHouseAdmin.rha.modules.sys.entity.labelItem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rentalHouseAdmin.rha.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签匹配表
 * </p>
 * @since 2021-02-11 19:12:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("label_item")
public class LabelItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 使用id
     */
    private String useId;

    /**
     * 标签id
     */
    private String labelId;

    /**
     * 使用方类型（1.发布，2.用户）
     */
    private Integer type;

}
