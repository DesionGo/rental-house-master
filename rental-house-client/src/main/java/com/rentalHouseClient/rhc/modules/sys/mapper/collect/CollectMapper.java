package com.rentalHouseClient.rhc.modules.sys.mapper.collect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;

import java.util.List;

/**
 * <p>
 * 收藏表 Mapper 接口
 * </p>
 * @since 2021-02-11 17:59:48
 */
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 查询列表(分页)
     * @param collect 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Collect> selectCollectList(Collect collect, IPage page);
    /**
     * 获取列表。分页
     * @param userId 查询参数
     * @return page
     */
    List<Issue> selectUserCollectList(String userId,IPage page);
}
