package com.rentalHouseClient.rhc.modules.sys.mapper.clientUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;


import java.util.List;

/**
 * <p>
 * 客户用户 Mapper 接口
 * </p>
 * @since 2021-01-29 11:13:05
 */
public interface ClientUserMapper extends BaseMapper<ClientUser> {

    /**
     * 查询列表(分页)
     * @param clientUser 查询参数
     * @param page 分页参数
     * @return list
     */
    List<ClientUser> selectUserList(ClientUser clientUser, IPage page);

}
