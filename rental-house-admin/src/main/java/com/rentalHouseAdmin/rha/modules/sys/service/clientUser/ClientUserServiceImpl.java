package com.rentalHouseAdmin.rha.modules.sys.service.clientUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseAdmin.rha.modules.sys.mapper.clientUser.ClientUserMapper;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 客户用户 服务实现类
 * </p>
 * @since 2021-01-29 11:13:05
 */
@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> implements ClientUserService {

    @Override
    public Page<com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> listUserPage(com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser clientUser) {
        Page<com.rentalHouseAdmin.rha.modules.sys.entity.clientUser.ClientUser> page = new Page<>(clientUser.getCurrent(), clientUser.getSize());
        List<ClientUser> users = baseMapper.selectUserList(clientUser, page);
        return page.setRecords(users);
    }

}
