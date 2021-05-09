package com.rentalHouseClient.rhc.modules.sys.service.clientUser;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.mapper.clientUser.ClientUserMapper;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 客户用户 服务实现类
 * </p>
 * @since 2021-01-29 11:13:05
 */
@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, ClientUser> implements ClientUserService {

    @Override
    public Page<ClientUser> listUserPage(ClientUser clientUser) {
        Page<ClientUser> page = new Page<>(clientUser.getCurrent(), clientUser.getSize());
        List<ClientUser> users = baseMapper.selectUserList(clientUser, page);
        return page.setRecords(users);
    }

    @Override
    public ClientUser getByEmail(String email) {

            return super.getOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getEmail, email));

    }

    @Override
    public void updateUserPassword(String id, String password) {
        super.update(new LambdaUpdateWrapper<ClientUser>()
                .set(ClientUser::getPassword, password)
                .eq(ClientUser::getId, id));
    }

    @Override
    public void updateUser(ClientUser clientUser) {
         baseMapper.updateUser(clientUser);
    }
}
