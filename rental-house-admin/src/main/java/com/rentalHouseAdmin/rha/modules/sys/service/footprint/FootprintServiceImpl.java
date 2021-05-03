package com.rentalHouseAdmin.rha.modules.sys.service.footprint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint;
import com.rentalHouseAdmin.rha.modules.sys.mapper.footprint.FootprintMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 足迹表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> implements FootprintService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> listFootprintPage(com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint footprint) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.footprint.Footprint> page = new Page<>(footprint.getCurrent(), footprint.getSize());
        List<Footprint> footprints = baseMapper.selectFootprintList(footprint, page);
        return page.setRecords(footprints);
    }

}
