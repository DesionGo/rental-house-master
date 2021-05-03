package com.rentalHouseAdmin.rha.modules.sys.mapper.issue;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue;

import java.util.List;

/**
 * <p>
 * 发布表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:49
 */
public interface IssueMapper extends BaseMapper<Issue> {

    /**
     * 查询列表(分页)
     * @param issue 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Issue> selectIssueList(Issue issue, IPage page);

}
