package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.common.constant.Constants;
import com.rentalHouseAdmin.rha.common.dto.ZTreeDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.Menu;
import com.rentalHouseAdmin.rha.modules.sys.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, com.rentalHouseAdmin.rha.modules.sys.entity.Menu> implements IMenuService {

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public List<String> getPermission(Long userId) {
        return baseMapper.selectPermission(userId);
    }

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuPage(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> page = new Page<>(menu.getCurrent(), menu.getSize());
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(menu.getName())) {
            menu.setParentId(menu.getId() == null ? 0L : menu.getId()); // treegrid默认传的id作为parentId
        }
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> menus = baseMapper.selectMenuList(menu, page);
        return page.setRecords(menus);
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuTree(com.rentalHouseAdmin.rha.modules.sys.entity.Menu menu) {
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(menu.getName())) {
            menu.setParentId(menu.getId() == null ? 0L : menu.getId()); // treegrid默认传的id作为parentId
        }
        return baseMapper.selectMenuList(menu);
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listMenuByParentId(Long parentId) {
        return list(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.Menu>()
                .eq(com.rentalHouseAdmin.rha.modules.sys.entity.Menu::getParentId, parentId == null ? 0L : parentId)
                .eq(com.rentalHouseAdmin.rha.modules.sys.entity.Menu::getStatus, Constants.STATUS_0)
                .orderByAsc(com.rentalHouseAdmin.rha.modules.sys.entity.Menu::getSort));
    }

    @Override
    public List<ZTreeDTO> listRoleMenu(Long roleId) {
        return baseMapper.selectRoleMenu(roleId);
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listUserPermissionMenu(Long parentId, Long userId) {
        return baseMapper.selectUserPermissionMenuList(parentId, userId);
    }

//    @Cacheable(value = "urm")
    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listUserPermissionMenuWithSubByUserId(Long userId) {
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> menus = this.listUserPermissionMenu(0L, userId);
        menus.forEach(menu -> {
            List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> submenus = this.listUserPermissionMenu(menu.getId(), userId);
            menu.setSubMenus(submenus);
        });
        return menus;
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Menu> listUserPermissionNavMenuByUserId(Long userId) {
        return baseMapper.selectUserPermissionNavMenuList(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithChildren(Long id) {
        super.removeById(id);
        roleMenuService.deleteByMenuId(id);
        this.deleteRecur(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithRoleMenu(List<Long> ids) {
        super.removeByIds(ids);
        roleMenuService.deleteByMenuIds(ids);
    }

    private void deleteRecur(Long parentId) {
        List<Menu> menus = this.listMenuByParentId(parentId);
        menus.forEach(menu -> {
            deleteRecur(menu.getId());
            super.removeById(menu.getId());
            roleMenuService.deleteByMenuId(menu.getId());
        });
    }

}
