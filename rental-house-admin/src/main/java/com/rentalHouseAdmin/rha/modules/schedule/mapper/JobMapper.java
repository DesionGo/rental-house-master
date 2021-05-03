package com.rentalHouseAdmin.rha.modules.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseAdmin.rha.modules.schedule.entity.Job;

import java.util.List;

/**
 * <p>
 * 定时任务表 Mapper 接口
 * </p>
 * @since 2019-08-17 17:06:12
 */
public interface JobMapper extends BaseMapper<com.rentalHouseAdmin.rha.modules.schedule.entity.Job> {

    /**
     * 查询列表(分页)
     * @param job 查询参数
     * @param page 分页参数
     * @return list
     */
    List<com.rentalHouseAdmin.rha.modules.schedule.entity.Job> selectJobList(Job job, IPage page);

}
