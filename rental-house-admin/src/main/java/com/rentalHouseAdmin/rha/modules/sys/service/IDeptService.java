package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dept;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface IDeptService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> {

    /**
     * 根据parentId获取部门列表
     * @param parentId 父级部门ID
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listDeptByParentId(Long parentId);

    /**
     * 根据部门ID获取它所有的子部门列表
     * @param deptId 部门ID
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listAllChildrenDept(Long deptId);

    /**
     * 获取菜单列表
     * @param dept 参数实体
     * @return
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listDeptPage(Dept dept);

    void deleteWithChildren(Long id);

}
