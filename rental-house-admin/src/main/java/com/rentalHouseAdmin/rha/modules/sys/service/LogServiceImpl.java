package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.Log;
import com.rentalHouseAdmin.rha.modules.sys.mapper.LogMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-10
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, com.rentalHouseAdmin.rha.modules.sys.entity.Log> implements ILogService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.Log> listLogPage(com.rentalHouseAdmin.rha.modules.sys.entity.Log log) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.Log> page = new Page<>(log.getCurrent(), log.getSize());
        List<Log> logs = baseMapper.selectLogList(log, page);
        return page.setRecords(logs);
    }
}
