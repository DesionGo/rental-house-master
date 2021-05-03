package com.rentalHouseClient.rhc.modules.sys.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
import com.rentalHouseClient.rhc.modules.sys.mapper.collect.CollectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public Page<Collect> listCollectPage(Collect collect) {
        Page<Collect> page = new Page<>(collect.getCurrent(), collect.getSize());
        List<Collect> collects = baseMapper.selectCollectList(collect, page);
        return page.setRecords(collects);
    }

}
