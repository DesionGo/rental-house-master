package com.rentalHouseClient.rhc.modules.sys.service.attention;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.mapper.attention.AttentionMapper;
import com.rentalHouseClient.rhc.modules.sys.entity.attention.Attention;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:47
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {

    @Override
    public Page<Attention> listAttentionPage(Attention attention) {
        Page<Attention> page = new Page<>(attention.getCurrent(), attention.getSize());
        List<Attention> attentions = baseMapper.selectAttentionList(attention, page);
        return page.setRecords(attentions);
    }

}
