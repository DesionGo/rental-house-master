package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dept;
import com.rentalHouseAdmin.rha.modules.sys.mapper.DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, com.rentalHouseAdmin.rha.modules.sys.entity.Dept> implements IDeptService {

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listDeptByParentId(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        return list(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.Dept>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.Dept::getParentId, parentId));
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listAllChildrenDept(Long deptId) {
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> list = new ArrayList<>();
        com.rentalHouseAdmin.rha.modules.sys.entity.Dept dept = getById(deptId);
        if (dept == null) {
            return list;
        }
        list.add(dept);
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> children = this.listDeptByParentId(deptId);
        if (CollectionUtil.isEmpty(children)) {
            return list;
        }
        for (com.rentalHouseAdmin.rha.modules.sys.entity.Dept child : children) {
            list.addAll(this.listAllChildrenDept(child.getId()));
        }
        return list;
    }

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> listDeptPage(com.rentalHouseAdmin.rha.modules.sys.entity.Dept dept) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> page = new Page<>(dept.getCurrent(), dept.getSize());
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(dept.getName())) {
            dept.setParentId(dept.getId() == null ? 0L : dept.getId()); // treegrid默认传的id作为parentId
        }
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Dept> depts = baseMapper.selectDeptList(dept, page);
        return page.setRecords(depts);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithChildren(Long id) {
        super.removeById(id);
        this.deleteRecur(id);
    }

    private void deleteRecur(Long parentId) {
        List<Dept> depts = this.listDeptByParentId(parentId);
        depts.forEach(dept -> {
            deleteRecur(dept.getId());
            super.removeById(dept.getId());
        });
    }
}
