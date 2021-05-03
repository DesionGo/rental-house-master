package com.rentalHouseAdmin.rha.modules.sys.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect;
import org.springframework.stereotype.Service;
import com.rentalHouseAdmin.rha.modules.sys.mapper.collect.CollectMapper;

import java.util.List;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> implements CollectService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> listCollectPage(com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect collect) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.collect.Collect> page = new Page<>(collect.getCurrent(), collect.getSize());
        List<Collect> collects = baseMapper.selectCollectList(collect, page);
        return page.setRecords(collects);
    }

}
