package com.rentalHouseClient.rhc.modules.sys.service.evaluate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.evaluate.Evaluate;
import com.rentalHouseClient.rhc.modules.sys.mapper.evaluate.EvaluateMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评价表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {

    @Override
    public Page<Evaluate> listEvaluatePage(Evaluate evaluate) {
        Page<Evaluate> page = new Page<>(evaluate.getCurrent(), evaluate.getSize());
        List<Evaluate> evaluates = baseMapper.selectEvaluateList(evaluate, page);
        return page.setRecords(evaluates);
    }

}
