package com.rentalHouseClient.rhc.modules.sys.controller.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.message.Message;
import com.rentalHouseClient.rhc.modules.sys.service.message.MessageService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 消息表 前端控制器
 * </p>
 * @since 2021-02-11 17:59:49
 */
@RestController
@RequestMapping("message/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @RequiresPermissions("message:message:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("message/message");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("message/message_edit");
        Message message;
        if (id == null) {
            message = new Message();
        } else {
            message = messageService.getById(id);
        }
        mv.addObject("editInfo", message);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Message message) {
        Page<Message> page = messageService.listMessagePage(message);
        return R.ok(page);
    }

    @RequiresPermissions("message:message:add")
    @PostMapping(value = "add")
    public R add(Message message) {
        messageService.save(message);
        return R.ok();
    }

    @RequiresPermissions("message:message:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        messageService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("message:message:edit")
    @PostMapping(value = "edit")
    public R edit(Message message) {
        messageService.updateById(message);
        return R.ok();
    }

    @RequiresPermissions("message:message:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        messageService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(messageService.getById(id));
    }

}

