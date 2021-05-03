package com.rentalHouseAdmin.rha.modules.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.schedule.entity.Job;

/**
 * <p>
 * 定时任务表 服务类
 * </p>
 * @since 2019-08-17 17:06:12
 */
public interface JobService extends IService<com.rentalHouseAdmin.rha.modules.schedule.entity.Job> {

    /**
     * 获取列表。分页
     * @param job 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.schedule.entity.Job> listJobPage(com.rentalHouseAdmin.rha.modules.schedule.entity.Job job);

    void createJob(com.rentalHouseAdmin.rha.modules.schedule.entity.Job job);

    void pauseJob(Long id);

    void resumeJob(Long id);

    void updateJob(com.rentalHouseAdmin.rha.modules.schedule.entity.Job job);

    void deleteJob(Long id);

    Job checkJobExist(Long id);

}
