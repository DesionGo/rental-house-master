package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void deleteByMenuId(Long menuId);

    void deleteByMenuIds(List<Long> menuIds);

    void deleteByRoleId(Long roleId);

    void deleteByRoleIds(List<Long> roleIds);

}
