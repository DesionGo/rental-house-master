package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.dto.UserRoleGroupDTO;
import com.rentalHouseAdmin.rha.modules.sys.entity.UserRole;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserRoleVO;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface IUserRoleService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> {

    Page<com.rentalHouseAdmin.rha.modules.sys.entity.UserRole> listUserRolePage(com.rentalHouseAdmin.rha.modules.sys.entity.UserRole userRole);

    List<UserRole> getUserRoleByRoleId(Long roleId);

    void saveOrUpdateBatchUserRole(UserRoleVO userRoleVO);

    void saveOrUpdateBatchUserRole(List<Long> roleIds, Long userId);

    int countUserRoleByRoleId(Long roleId);

    UserRoleGroupDTO getUserRoleGroupDTOByUserId(Long userId);

    String getRoleIdsByUserId(Long userId);

    String getRoleNamesByUserId(Long userId);

}
