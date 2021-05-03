package com.rentalHouseClient.rhc.modules.sys.entity.issue;

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
 * 发布表
 * </p>
 * @since 2021-02-11 17:59:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("issue")
public class Issue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 发布标题
     */
    private String headline;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布类型(0 找租友，1 出租，2 求租）
     */
    private Integer type;

    /**
     * 创建者id
     */
    private String createUserId;

    /**
     * 创建者名字
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;

    /**
     * 金额
     */
    private Integer money;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县/区
     */
    private String county;

    /**
     * 详情地点
     */
    private String detailSite;

    /**
     * 状态(1.正常 2.审核中,3.下架 )
     */
    private String status;

    /**
     * 审核人员id
     */
    private String auditorUserId;

    /**
     * 审核人员姓名
     */
    private String auditorUserName;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditorTime;

    /**
     * 审核状态（0.审核中 1. 审核通过，2.审核驳回）
     */
    private Integer auditorStatus;

    /**
     * 审核回复
     */
    private String auditorRemark;

}
