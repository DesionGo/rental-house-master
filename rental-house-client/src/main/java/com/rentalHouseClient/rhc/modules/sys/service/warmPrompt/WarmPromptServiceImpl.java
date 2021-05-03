package com.rentalHouseClient.rhc.modules.sys.service.warmPrompt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.warmPrompt.WarmPrompt;
import com.rentalHouseClient.rhc.modules.sys.mapper.warmPrompt.WarmPromptMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 温馨提示表 服务实现类
 * </p>
 * @since 2021-02-11 18:00:08
 */
@Service
public class WarmPromptServiceImpl extends ServiceImpl<WarmPromptMapper, WarmPrompt> implements WarmPromptService {

    @Override
    public Page<WarmPrompt> listPromptPage(WarmPrompt warmPrompt) {
        Page<WarmPrompt> page = new Page<>(warmPrompt.getCurrent(), warmPrompt.getSize());
        List<WarmPrompt> prompts = baseMapper.selectPromptList(warmPrompt, page);
        return page.setRecords(prompts);
    }

}
