package com.rentalHouseAdmin.rha.modules.sys.service.clientUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser;

/**
 * <p>
 * 客户用户 服务类
 * </p>
 * @since 2021-01-29 11:13:05
 */
public interface ClientUserService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> {

    /**
     * 获取列表。分页
     * @param clientUser 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> listUserPage(ClientUser clientUser);

}
