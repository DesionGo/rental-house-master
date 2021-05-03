package com.rentalHouseClient.rhc.modules.sys.controller.issue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import com.rentalHouseClient.rhc.modules.sys.service.issue.IssueService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
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
@RequestMapping("issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;




    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("issue/issue_edit");
        Issue issue;
        if (id == null) {
            issue = new Issue();
        } else {
            issue = issueService.getById(id);
        }
        mv.addObject("editInfo", issue);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Issue issue) {
        Page<Issue> page = issueService.listIssuePage(issue);
        return R.ok(page);
    }

    @RequiresPermissions("issue:issue:add")
    @PostMapping(value = "add")
    public R add(Issue issue) {
        issueService.save(issue);
        return R.ok();
    }

    @RequiresPermissions("issue:issue:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        issueService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("issue:issue:edit")
    @PostMapping(value = "edit")
    public R edit(Issue issue) {
        issueService.updateById(issue);
        return R.ok();
    }

    @RequiresPermissions("issue:issue:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        issueService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(issueService.getById(id));
    }

}

