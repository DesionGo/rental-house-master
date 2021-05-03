package com.rentalHouseAdmin.rha.modules.sys.service.issue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue;

/**
 * <p>
 * 发布表 服务类
 * </p>
 * @since 2021-02-11 17:59:49
 */
public interface IssueService extends IService<Issue> {

    /**
     * 获取列表。分页
     * @param issue 查询参数
     * @return page
     */
    Page<Issue> listIssuePage(Issue issue);

}
