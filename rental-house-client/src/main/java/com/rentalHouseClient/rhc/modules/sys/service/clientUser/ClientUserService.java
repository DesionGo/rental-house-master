package com.rentalHouseClient.rhc.modules.sys.service.clientUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;

/**
 * <p>
 * 客户用户 服务类
 * </p>
 * @since 2021-01-29 11:13:05
 */
public interface ClientUserService extends IService<ClientUser> {

    /**
     * 获取列表。分页
     * @param clientUser 查询参数
     * @return page
     */
    Page<ClientUser> listUserPage(ClientUser clientUser);



    ClientUser getByEmail(String email);

    void updateUserPassword(String id, String password);

    void updateUser(ClientUser clientUser);
}
