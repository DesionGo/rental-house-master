package com.rentalHouseAdmin.rha.modules.sys.vo;

import com.rentalHouseAdmin.rha.modules.sys.entity.Dept;
import com.rentalHouseAdmin.rha.common.entity.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户查询参数实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserQueryVO extends BasePageEntity {
    private String username;
    private String realname;
    private Integer status;
    private Long deptId;
    private List<Dept> depts;
}
