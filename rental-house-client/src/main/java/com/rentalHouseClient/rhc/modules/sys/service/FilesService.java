package com.rentalHouseClient.rhc.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 * @since 2021-02-27 14:34:28
 */
public interface FilesService extends IService<Files> {

    /**
     * 获取列表。分页
     * @param files 查询参数
     * @return page
     */
    Page<Files> listFilesPage(Files files);

    Files selectFiles(String city);

    Files selectFilesId(String ascriptionId);

    List<Files> selectFilesById(String ascriptionId);

    void add(Files files);
}
