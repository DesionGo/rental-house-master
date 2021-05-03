package com.rentalHouseAdmin.rha.modules.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.schedule.entity.Job;
import com.rentalHouseAdmin.rha.modules.schedule.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定时任务控制层
 */
@Slf4j
@RestController
@RequestMapping(value = "schedule/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequiresPermissions("schedule:job:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("schedule/job");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("schedule/job_edit");
        com.rentalHouseAdmin.rha.modules.schedule.entity.Job job;
        if (id == null) {
            job = new com.rentalHouseAdmin.rha.modules.schedule.entity.Job();
        } else {
            job = jobService.getById(id);
        }
        mv.addObject("editInfo", job);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.schedule.entity.Job job) {
        Page<com.rentalHouseAdmin.rha.modules.schedule.entity.Job> page = jobService.listJobPage(job);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    /**
     * 添加定时任务
     * @param job 任务
     */
    @RequiresPermissions("schedule:job:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.schedule.entity.Job job) {
        jobService.createJob(job);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 暂停定时任务
     * @param id 任务ID
     */
    @RequiresPermissions("schedule:job:pause")
    @PostMapping(value = "pause/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R pause(@PathVariable Long id) {
        jobService.pauseJob(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 恢复定时任务
     * @param id 任务ID
     */
    @RequiresPermissions("schedule:job:resume")
    @PostMapping(value = "resume/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R resume(@PathVariable Long id) {
        jobService.resumeJob(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 更新定时任务
     * @param job 任务
     */
    @RequiresPermissions("schedule:job:edit")
    @PostMapping(value = "update")
    public com.rentalHouseAdmin.rha.common.dto.R update(Job job) {
        jobService.updateJob(job);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    /**
     * 删除定时任务
     * @param id 任务ID
     */
    @RequiresPermissions("schedule:job:del")
    @PostMapping(value = "delete/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R delete(@PathVariable Long id) {
        jobService.deleteJob(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(jobService.getById(id));
    }


}

