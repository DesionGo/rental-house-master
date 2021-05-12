package com.rentalHouseClient.rhc.modules.sys.mapper.collect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param collect 查询参数
     * @param page    分页参数
     * @return list
     */
    List<Collect> selectCollectList(Collect collect, IPage page);

    void updateStatus(@Param("issueId") String issueId, @Param("userId") String userId);

    void add(@Param("collect") Collect collect);

    Collect selectById(@Param("collect") Collect collect);
}
