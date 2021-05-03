package com.rentalHouseAdmin.rha.modules.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.User;
import com.rentalHouseAdmin.rha.modules.sys.mapper.UserMapper;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserQueryVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, com.rentalHouseAdmin.rha.modules.sys.entity.User> implements IUserService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.User> listUserPage(UserQueryVO queryVO) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.User> page = new Page<>(queryVO.getCurrent(), queryVO.getSize());
        List<com.rentalHouseAdmin.rha.modules.sys.entity.User> users = baseMapper.selectUserList(queryVO, page);
        return page.setRecords(users);
    }

    @Override
    public com.rentalHouseAdmin.rha.modules.sys.entity.User getByUsername(String username) {
        return super.getOne(new LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.User>().eq(com.rentalHouseAdmin.rha.modules.sys.entity.User::getUsername, username));
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        super.update(new LambdaUpdateWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.User>()
                .set(com.rentalHouseAdmin.rha.modules.sys.entity.User::getPassword, password)
                .eq(com.rentalHouseAdmin.rha.modules.sys.entity.User::getId, id));
    }

    @Override
    public List<com.rentalHouseAdmin.rha.modules.sys.entity.User> search(String query) {
        if (StrUtil.isBlank(query)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<com.rentalHouseAdmin.rha.modules.sys.entity.User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(com.rentalHouseAdmin.rha.modules.sys.entity.User::getUsername, query).or().like(User::getRealname, query);
        return super.list(queryWrapper);
    }
}
