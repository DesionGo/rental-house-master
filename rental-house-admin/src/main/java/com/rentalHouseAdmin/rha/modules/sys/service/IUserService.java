package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.User;
import com.rentalHouseAdmin.rha.modules.sys.vo.UserQueryVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
public interface IUserService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.User> {

    /**
     * 获取用户列表。分页
     * @param queryVO 查询参数
     * @return
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.User> listUserPage(UserQueryVO queryVO);

    com.rentalHouseAdmin.rha.modules.sys.entity.User getByUsername(String username);

    void updateUserPassword(String id, String password);

    /**
     * 根据查询参数模糊搜索username和realname值可能为query的所有用户
     * @param query 查询参数
     * @return list
     */
    List<User> search(String query);

}
