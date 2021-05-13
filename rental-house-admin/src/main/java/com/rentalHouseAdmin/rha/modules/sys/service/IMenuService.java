package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.common.dto.ZTreeDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface IMenuService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> {

    /**
     * 根据用户获取权限列表
     * @param userId 用户ID
     * @return
     */
    List<String> getPermission(String userId);

    /**
     * 获取菜单列表
     * @param menu 参数实体
     * @return
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuPage(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu);

    /**
     * 获取菜单树
     * @param menu 参数实体
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuTree(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu);

    /**
     * 根据父级ID获取菜单列表
     * @param parentId 菜单父级ID
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuByParentId(Long parentId);

    /**
     * 获取角色菜单列表
     * @param roleId 角色ID
     * @return
     */
    List<ZTreeDTO> listRoleMenu(Long roleId);

    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listUserPermissionMenu(Long parentId, String userId);

    /**
     * 获取用户带权限的菜单，带子级菜单。这里只需要获取到第二层菜单即可
     * @param userId 用户ID
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listUserPermissionMenuWithSubByUserId(String userId);

    /**
     * 获取用户有权限的导航菜单列表（用于横向导航菜单）
     * @param userId 用户ID
     * @return
     */
    List<Menu> listUserPermissionNavMenuByUserId(String userId);

    void deleteWithChildren(Long id);

    void deleteWithRoleMenu(List<Long> ids);

}
