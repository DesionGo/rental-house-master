package com.rentalHouseAdmin.rha.modules.sys.service.labelItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem;
import com.rentalHouseAdmin.rha.modules.sys.mapper.labelItem.LabelItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签匹配表 服务实现类
 * </p>
 * @since 2021-02-11 19:12:36
 */
@Service
public class ItemServiceImpl extends ServiceImpl<LabelItemMapper, com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> implements LabelItemService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> listItemPage(com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem labelItem) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.labelItem.LabelItem> page = new Page<>(labelItem.getCurrent(), labelItem.getSize());
        List<LabelItem> items = baseMapper.selectItemList(labelItem, page);
        return page.setRecords(items);
    }

}
