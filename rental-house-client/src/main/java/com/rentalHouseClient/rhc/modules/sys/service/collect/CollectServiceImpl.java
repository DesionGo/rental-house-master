package com.rentalHouseClient.rhc.modules.sys.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.common.dto.R;
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
import com.rentalHouseClient.rhc.modules.sys.service.issue.IssueService;
import com.rentalHouseClient.rhc.modules.sys.service.label.LabelService;
import com.rentalHouseClient.rhc.modules.sys.service.labelItem.LabelItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IssueService issueService;

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
       List<Issue> issues=issueService.selectUserCollectList(userId).stream().skip((current-1)*6).limit(6).collect(Collectors.toList());

       if(issues.size()!=0){
           issueIndexDTO.setSum(issues.size());
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

       }else {
           issueIndexDTO.setSum(0);
       }
        return issueIndexDTO;
    }

    @Override
    public void updateStatus(String issueId, String userId) {
        baseMapper.updateStatus(issueId,userId);
    }

    @Override
    public R add(Collect collect) {
        try{
            baseMapper.add(collect);
        }catch (Exception e){
            return R.fail("收藏失败");
        }
        return  R.ok("收藏成功");
    }

    @Override
    public Collect selectById(Collect collect) {
        return baseMapper.selectById(collect);
    }


}
