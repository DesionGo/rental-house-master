package com.rentalHouseClient.rhc.modules.sys.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueIndexDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import com.rentalHouseClient.rhc.modules.sys.entity.labelItem.LabelItem;
import com.rentalHouseClient.rhc.modules.sys.mapper.collect.CollectMapper;
import com.rentalHouseClient.rhc.modules.sys.service.FilesService;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import com.rentalHouseClient.rhc.modules.sys.service.label.LabelService;
import com.rentalHouseClient.rhc.modules.sys.service.labelItem.LabelItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:48
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private FilesService filesService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private LabelItemService labelItemService;

    @Autowired
    private ClientUserService clientUserService;

    @Override
    public Page<Collect> listCollectPage(Collect collect) {
        Page<Collect> page = new Page<>(collect.getCurrent(), collect.getSize());
        List<Collect> collects = baseMapper.selectCollectList(collect, page);
        return page.setRecords(collects);
    }

    @Override
    public IssueIndexDTO selectUserCollectList(String userId,int current) {
        IssueIndexDTO issueIndexDTO=new IssueIndexDTO();
        List<IssueDTO> issueDTOS=new ArrayList<>();
        ClientUser clientUser=new ClientUser();
        clientUser  = clientUserService.getById(ShiroKit.getUserId());
        issueIndexDTO.setUserName(clientUser.getUserName());
        Page<Issue> page = new Page<>(current, 6);
       List<Issue> issues=baseMapper.selectUserCollectList(userId,page);
        for(Issue issue1:issues){
            IssueDTO issueDTO=new IssueDTO();
            Files file= filesService.selectFilesId(issue1.getId());
            List<Label> labels=labelService.list();
            List<LabelItem> labelItems=labelItemService.selectService(issue1.getId());

            if(labelItems.size()!=0){
                List<Label> labels1=new ArrayList<>();
                for(LabelItem labelItem:labelItems){

                    for(Label label:labels){
                        if(label.getId().equals(labelItem.getLabelId())){
                            labels1.add(label);
                        }
                    }
                    issueDTO.setLabel(labels1);
                }
            }
            BeanUtils.copyProperties(issue1,issueDTO);
            if(file!=null) {
                issueDTO.setUrl(file.getUrl());
            }

            issueDTOS.add(issueDTO);
        }
        issueIndexDTO.setIssue(issueDTOS);
        return issueIndexDTO;
    }

}
