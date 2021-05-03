package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.common.utils.ShiroKit;
import com.rentalHouseAdmin.rha.modules.sys.entity.Role;
import com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu;
import com.rentalHouseAdmin.rha.modules.sys.mapper.RoleMapper;
import com.rentalHouseAdmin.rha.modules.sys.vo.RoleMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, com.rentalHouseAdmin.rha.modules.sys.entity.Role> implements IRoleService {

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.Role> listRolePage(com.rentalHouseAdmin.rha.modules.sys.entity.Role role) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Role> page = new Page<>(role.getCurrent(), role.getSize());
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(role.getName())) {
            role.setParentId(role.getId() == null ? 0L : role.getId()); // treegrid默认传的id作为parentId
        }
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Role> roles = baseMapper.selectRoleList(role, page);
        return page.setRecords(roles);
    }

    @Override
    @Transactional
    public void saveOrUpdatePermission(RoleMenuVO roleMenuVO) {
        Long roleId = roleMenuVO.getId();
        Long[] menuIds = roleMenuVO.getMenuIds();

        if (menuIds == null) return;

        // 更新，先删除多余的权限
        if (menuIds.length == 0) {
            roleMenuService.remove(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu::getRoleId, roleId));
        } else {
            roleMenuService.remove(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu>()
                    .eq(com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu::getRoleId, roleId).notIn(com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu::getMenuId, Arrays.asList(menuIds)));
            List<com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : menuIds) {
                com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu roleMenu = roleMenuService.getOne(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu>()
                        .eq(com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu::getRoleId, roleId).eq(com.rentalHouseAdmin.rha.modules.sys.entity.RoleMenu::getMenuId, menuId));
                if (roleMenu == null) {
                    roleMenu = new RoleMenu(roleId, menuId);
                }
                roleMenus.add(roleMenu);
            }
            roleMenuService.saveOrUpdateBatch(roleMenus);
        }

        // 更新shiro权限缓存使能立即生效
        ShiroKit.flushPrivileges();

    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Role> listRoleByParentId(Long parentId) {
        return super.list(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.Role>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.Role::getParentId, parentId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithChildren(Long id) {
        super.removeById(id);
        roleMenuService.deleteByRoleId(id);
        this.deleteRecur(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithRoleMenu(List<Long> ids) {
        super.removeByIds(ids);
        roleMenuService.deleteByRoleIds(ids);
    }

    private void deleteRecur(Long parentId) {
        List<Role> roles = this.listRoleByParentId(parentId);
        roles.forEach(role -> {
            deleteRecur(role.getId());
            super.removeById(role.getId());
            roleMenuService.deleteByRoleId(role.getId());
        });
    }
}
