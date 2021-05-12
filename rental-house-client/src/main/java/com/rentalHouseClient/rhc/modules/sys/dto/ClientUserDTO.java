package com.rentalHouseClient.rhc.modules.sys.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author czf
 * @date 2021/5/12 22:03
 */
@Data
public class ClientUserDTO {
    /**
     * 账户真实姓名
     */
    private String name;

    /**
     * 账户身份证
     */
    private String identityCard;
    /**
     * 账户id
     */
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
