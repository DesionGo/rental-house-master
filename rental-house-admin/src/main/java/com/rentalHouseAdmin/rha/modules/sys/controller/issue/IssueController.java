package com.rentalHouseAdmin.rha.modules.sys.controller.issue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue;
import com.rentalHouseAdmin.rha.modules.sys.service.issue.IssueService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 发布表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:49
 */
@RestController
@RequestMapping("issue/issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;

    @RequiresPermissions("issue:issue:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("sys/issue/issue");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("sys/issue/issue_edit");
        com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue issue;
        if (id == null) {
            issue = new com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue();
        } else {
            issue = issueService.getById(id);
        }
        mv.addObject("editInfo", issue);
        return mv;
    }

    @GetMapping(value = "list/data")
    public com.rentalHouseAdmin.rha.common.dto.R listData(com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue issue) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue> page = issueService.listIssuePage(issue);
        return com.rentalHouseAdmin.rha.common.dto.R.ok(page);
    }

    @RequiresPermissions("issue:issue:add")
    @PostMapping(value = "add")
    public com.rentalHouseAdmin.rha.common.dto.R add(com.rentalHouseAdmin.rha.modules.sys.entity.issue.Issue issue) {
        issueService.save(issue);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("issue:issue:del")
    @PostMapping(value = "batchdel")
    public com.rentalHouseAdmin.rha.common.dto.R batchdel(@RequestParam("ids") List<String> ids) {
        issueService.removeByIds(ids);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("issue:issue:edit")
    @PostMapping(value = "edit")
    public com.rentalHouseAdmin.rha.common.dto.R edit(Issue issue) {
        issueService.updateById(issue);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @RequiresPermissions("issue:issue:del")
    @PostMapping(value = "del/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R del(@PathVariable Long id) {
        issueService.removeById(id);
        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @GetMapping(value = "get/{id}")
    public com.rentalHouseAdmin.rha.common.dto.R get(@PathVariable Long id) {
        return R.ok(issueService.getById(id));
    }

}

