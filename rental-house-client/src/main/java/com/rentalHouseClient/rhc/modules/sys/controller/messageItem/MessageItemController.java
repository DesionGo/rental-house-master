package com.rentalHouseClient.rhc.modules.sys.controller.messageItem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rentalHouseClient.rhc.modules.sys.entity.messageItem.MessageItem;
import com.rentalHouseClient.rhc.modules.sys.service.messageItem.MessageItemService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 消息内容表 前端控制器
 * </p>
 * @since 2021-02-11 19:28:17
 */
@RestController
@RequestMapping("message/item")
public class MessageItemController extends BaseController {

    @Autowired
    private MessageItemService messageItemService;

    @RequiresPermissions("messageItem:messageItem:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("messageItem/messageItem");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("messageItem/messageItem_edit");
        MessageItem messageItem;
        if (id == null) {
            messageItem = new MessageItem();
        } else {
            messageItem = messageItemService.getById(id);
        }
        mv.addObject("editInfo", messageItem);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(MessageItem messageItem) {
        Page<MessageItem> page = messageItemService.listItemPage(messageItem);
        return R.ok(page);
    }

    @RequiresPermissions("messageItem:messageItem:add")
    @PostMapping(value = "add")
    public R add(MessageItem messageItem) {
        messageItemService.save(messageItem);
        return R.ok();
    }

    @RequiresPermissions("messageItem:messageItem:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        messageItemService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("messageItem:messageItem:edit")
    @PostMapping(value = "edit")
    public R edit(MessageItem messageItem) {
        messageItemService.updateById(messageItem);
        return R.ok();
    }

    @RequiresPermissions("messageItem:messageItem:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        messageItemService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(messageItemService.getById(id));
    }

}

