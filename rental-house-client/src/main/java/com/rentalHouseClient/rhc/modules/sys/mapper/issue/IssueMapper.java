package com.rentalHouseClient.rhc.modules.sys.mapper.issue;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.dto.PropertiesGridScreenDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param issue 查询参数
     * @param page  分页参数
     * @return list
     */
    List<Issue> selectIssueList(Issue issue, IPage page);

    List<Issue> selectIssue(@Param("issue") Issue issue);

    List<Issue> propertiesGridScreen(@Param("dto") PropertiesGridScreenDTO dto);

    List<Issue> selectUserIssue(@Param("createId")String createId);
}
