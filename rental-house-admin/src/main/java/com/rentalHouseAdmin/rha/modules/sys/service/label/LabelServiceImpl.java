package com.rentalHouseAdmin.rha.modules.sys.service.label;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.rentalHouseAdmin.rha.modules.sys.entity.label.Label;
import com.rentalHouseAdmin.rha.modules.sys.mapper.label.LabelMapper;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 * @since 2021-02-11 19:11:13
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {

    @Override
    public Page<Label> listLabelPage(Label label) {
        Page<Label> page = new Page<>(label.getCurrent(), label.getSize());
        List<Label> labels = baseMapper.selectLabelList(label, page);
        return page.setRecords(labels);
    }

    @Override
    public void saveGo(Label label) {
        baseMapper.saveGo(label);
    }

}
