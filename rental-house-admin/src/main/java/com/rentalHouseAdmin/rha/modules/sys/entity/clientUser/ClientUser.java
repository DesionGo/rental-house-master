package com.rentalHouseAdmin.rha.modules.sys.entity.clientUser;

import com.baomidou.mybatisplus.annotation.*;
import com.rentalHouseAdmin.rha.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户用户
 * </p>
 * @since 2021-01-29 11:13:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("client_user")
public class ClientUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账户名称
     */
    private String userName;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 用户密码密文
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱(唯一)
     */
    private String email;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

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

}
