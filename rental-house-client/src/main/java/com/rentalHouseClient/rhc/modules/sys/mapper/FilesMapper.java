package com.rentalHouseClient.rhc.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件上传 Mapper 接口
 * </p>
 * @since 2021-02-27 14:34:28
 */
public interface FilesMapper extends BaseMapper<Files> {

    /**
     * 查询列表(分页)
     * @param files 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Files> selectFilesList(Files files, IPage page);

    Files selectFiles(String city);

    Files selectFilesId(String ascriptionId);

    void add(@Param("files") Files files);
}
