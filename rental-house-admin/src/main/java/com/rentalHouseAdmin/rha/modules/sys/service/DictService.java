package com.rentalHouseAdmin.rha.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseAdmin.rha.modules.sys.entity.Dict;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * @since 2019-08-10 15:52:56
 */
public interface DictService extends IService<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> {

    /**
     * 获取列表。分页
     * @param dict 查询参数
     * @return page
     */
    Page<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> listDictPage(com.rentalHouseAdmin.rha.modules.sys.entity.Dict dict);

    com.rentalHouseAdmin.rha.modules.sys.entity.Dict getByCode(String code);

    /**
     * 根据字典码获取下面所有的字典项条目
     * @param code 字典码
     * @return
     */
    List<com.rentalHouseAdmin.rha.modules.sys.entity.Dict> listAllDictItemByCode(String code);

    List<Dict> listByParentId(Long parentId);

    void deleteWithChildren(Long id);

}
