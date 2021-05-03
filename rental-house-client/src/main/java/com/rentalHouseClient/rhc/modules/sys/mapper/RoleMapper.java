package com.rentalHouseClient.rhc.modules.sys.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleList(Role role, Page page);

}
