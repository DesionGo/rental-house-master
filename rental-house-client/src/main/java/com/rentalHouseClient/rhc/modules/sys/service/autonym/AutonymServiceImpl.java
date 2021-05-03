package com.rentalHouseClient.rhc.modules.sys.service.autonym;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.autonym.Autonym;
import com.rentalHouseClient.rhc.modules.sys.mapper.autonym.AutonymMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 实名表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class AutonymServiceImpl extends ServiceImpl<AutonymMapper, Autonym> implements AutonymService {

    @Override
    public Page<Autonym> listAutonymPage(Autonym autonym) {
        Page<Autonym> page = new Page<>(autonym.getCurrent(), autonym.getSize());
        List<Autonym> autonyms = baseMapper.selectAutonymList(autonym, page);
        return page.setRecords(autonyms);
    }

}
