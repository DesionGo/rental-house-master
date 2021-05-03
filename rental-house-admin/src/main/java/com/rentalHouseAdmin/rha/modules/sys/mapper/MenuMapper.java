package com.rentalHouseAdmin.rha.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.dto.ZTreeDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface MenuMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> {

    List<String> selectPermission(@Param("userId") Long userId);

    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> selectMenuList(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu, Page page);

    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> selectMenuList(@Param("menu") com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu);

    /**
     * 查询角色菜单列表
     * @param roleId 角色ID
     * @return
     */
    List<ZTreeDTO> selectRoleMenu(@Param("roleId") Long roleId);

    /**
     * 查询用户有权限的目录菜单列表（用于首页左侧树）
     * @param parentId
     * @param userId
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> selectUserPermissionMenuList(@Param("parentId") Long parentId, @Param("userId") Long userId);

    /**
     * 查询用户有权限的导航菜单列表（用于横向导航菜单）
     * @param userId
     * @return
     */
    List<Menu> selectUserPermissionNavMenuList(Long userId);

}
