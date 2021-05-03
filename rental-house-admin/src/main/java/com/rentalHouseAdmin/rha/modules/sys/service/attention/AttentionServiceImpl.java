package com.rentalHouseAdmin.rha.modules.sys.service.attention;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention;
import org.springframework.stereotype.Service;
import com.rentalHouseAdmin.rha.modules.sys.mapper.attention.AttentionMapper;

import java.util.List;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:47
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> implements AttentionService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> listAttentionPage(com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention attention) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.attention.Attention> page = new Page<>(attention.getCurrent(), attention.getSize());
        List<Attention> attentions = baseMapper.selectAttentionList(attention, page);
        return page.setRecords(attentions);
    }

}
