package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.mapper.DictMapper;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 * @since 2019-08-10 15:52:56
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, com.rentalHouseAdmin.rha.modules.sys.entity.Dict> implements DictService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> listDictPage(com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> page = new Page<>(dict.getCurrent(), dict.getSize());
        if (StrUtil.isBlank(dict.getName())) {
            dict.setParentId(dict.getId() == null ? 0 : dict.getId()); // treegrid默认传的id作为parentId
        }
        List<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> dicts = baseMapper.selectDictList(dict, page);
        return page.setRecords(dicts);
    }

    @Override
    public com.rentalHouseAdmin.rha.modules.sys.entity.Dict getByCode(String code) {
        return super.getOne(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.Dict>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.Dict::getCode, code));
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> listAllDictItemByCode(String code) {
        return baseMapper.selectAllDictItemByCode(code);
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> listByParentId(Long parentId) {
        return super.list(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.Dict>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.Dict::getParentId, parentId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteWithChildren(Long id) {
        super.removeById(id);
        this.deleteRecur(id);
    }

    private void deleteRecur(Long parentId) {
        List<Dict> dicts = this.listByParentId(parentId);
        dicts.forEach(dict -> {
            deleteRecur(dict.getId());
            super.removeById(dict.getId());
        });
    }


}
