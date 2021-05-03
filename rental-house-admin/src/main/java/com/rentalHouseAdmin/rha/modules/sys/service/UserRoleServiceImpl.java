package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.dto.UserRoleGroupDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.UserRole;
import com.rentalHouseAdmin.rha.modules.sys.mapper.UserRoleMapper;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> implements IUserRoleService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> listUserRolePage(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole userRole) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> page = new Page<>(userRole.getCurrent(), userRole.getSize());
        List<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> userRoles = baseMapper.selectUserRoleList(userRole, page);
        return page.setRecords(userRoles);
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> getUserRoleByRoleId(Long roleId) {
        return super.list(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getRoleId, roleId));
    }

    @Override
    public void saveOrUpdateBatchUserRole(UserRoleVO userRoleVO) {
        Long roleId = userRoleVO.getRoleId();
        List<Long> userIds = userRoleVO.getUserIds();
        List<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> userRoles = new ArrayList<>();

        userIds.forEach(userId -> {
            com.rentalHouseAdmin.rha.modules.sys.entity.UserRole userRole = super.getOne(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>()
                    .eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getRoleId, roleId).eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getUserId, userId));
            if (userRole == null) {
                userRole = new com.rentalHouseAdmin.rha.modules.sys.entity.UserRole();
                userRole.setRoleId(roleId).setUserId(userId);
            }
            userRoles.add(userRole);
        });
        super.saveOrUpdateBatch(userRoles);
    }

    @Transactional
    @Override
    public void saveOrUpdateBatchUserRole(List<Long> roleIds, Long userId) {
        if (CollectionUtil.isEmpty(roleIds)) {
            super.remove(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getUserId, userId));
        } else {
            super.remove(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getUserId, userId).notIn(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getRoleId, roleIds));
            List<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> userRoles = new ArrayList<>();

            roleIds.forEach(roleId -> {
                com.rentalHouseAdmin.rha.modules.sys.entity.UserRole userRole = super.getOne(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>()
                        .eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getRoleId, roleId).eq(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole::getUserId, userId));
                if (userRole == null) {
                    userRole = new com.rentalHouseAdmin.rha.modules.sys.entity.UserRole();
                }
                userRole.setUserId(userId).setRoleId(roleId);
                userRoles.add(userRole);
            });
            super.saveOrUpdateBatch(userRoles);
        }
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return super.count(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole>().eq(UserRole::getRoleId, roleId));
    }

    @Override
    public com.rentalHouseAdmin.rha.modules.sys.dto.UserRoleGroupDTO getUserRoleGroupDTOByUserId(Long userId) {
        return baseMapper.selectUserRoleGroupByUserId(userId);
    }

    @Override
    public String getRoleIdsByUserId(Long userId) {
        return this.getUserRoleGroupDTOByUserId(userId).getRoleIds();
    }

    @Override
    public String getRoleNamesByUserId(Long userId) {
        UserRoleGroupDTO userRoleGroupDTOByUserId = this.getUserRoleGroupDTOByUserId(userId);
        return userRoleGroupDTOByUserId == null ? "" : userRoleGroupDTOByUserId.getRoleNames();
    }
}
