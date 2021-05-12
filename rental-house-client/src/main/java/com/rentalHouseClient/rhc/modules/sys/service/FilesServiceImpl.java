package com.rentalHouseClient.rhc.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.mapper.FilesMapper;

import java.util.List;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 * @since 2021-02-27 14:34:28
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {

    @Override
    public Page<Files> listFilesPage(Files files) {
        Page<Files> page = new Page<>(files.getCurrent(), files.getSize());
        List<Files> filess = baseMapper.selectFilesList(files, page);
        return page.setRecords(filess);
    }

    @Override
    public Files selectFiles(String city) {
        Files files=baseMapper.selectFiles(city);
        return  files;
    }

    @Override
    public Files selectFilesId(String ascriptionId) {
        List<Files> files=baseMapper.selectFilesId(ascriptionId);
        if(files.size()==0){
            return    null;
        }else{
            return    files.get(0);
        }

    }

    @Override
    public List<Files> selectFilesById(String ascriptionId) {
        return baseMapper.selectFilesId(ascriptionId);
    }

    @Override
    public void add(Files files) {
        baseMapper.add(files);
    }

}
