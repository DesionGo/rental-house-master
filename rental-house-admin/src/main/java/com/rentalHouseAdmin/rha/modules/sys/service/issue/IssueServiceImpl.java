package com.rentalHouseAdmin.rha.modules.sys.service.issue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue;
import com.rentalHouseAdmin.rha.modules.sys.mapper.issue.IssueMapper;

import java.util.List;

/**
 * <p>
 * 发布表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:49
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IssueService {

    @Override
    public Page<Issue> listIssuePage(Issue issue) {
        Page<Issue> page = new Page<>(issue.getCurrent(), issue.getSize());
        List<Issue> issues = baseMapper.selectIssueList(issue, page);
        return page.setRecords(issues);
    }

}
