package com.rentalHouseClient.rhc.modules.sys.service.issue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.modules.sys.dto.*;
import com.rentalHouseClient.rhc.modules.sys.service.FilesService;
import com.rentalHouseClient.rhc.modules.sys.service.label.LabelService;
import com.rentalHouseClient.rhc.common.util.BaiDuAPIUtil;
import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import com.rentalHouseClient.rhc.modules.sys.entity.labelItem.LabelItem;
import com.rentalHouseClient.rhc.modules.sys.mapper.issue.IssueMapper;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import com.rentalHouseClient.rhc.modules.sys.service.labelItem.LabelItemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 发布表 服务实现类
 * </p>
 * @since 2021-02-11 17:59:49
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IssueService {

    @Autowired
    private FilesService filesService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private LabelItemService labelItemService;

    @Autowired
    private ClientUserService clientUserService;


    @Override
    public Page<Issue> listIssuePage(Issue issue) {
        Page<Issue> page = new Page<>(issue.getCurrent(), issue.getSize());
        List<Issue> issues = baseMapper.selectIssueList(issue, page);
        return page.setRecords(issues);
    }

    @Override
    public List<Issue> listIssue(Issue issue) {
        List<Issue> issues = baseMapper.selectIssue(issue);
        return issues;
    }

    @Override
    public IssueIndexDTO listIssueDTO(String ip) {
        Issue issue=new Issue();
        IssueIndexDTO issueIndexDTO=new IssueIndexDTO();
        //判断是否登陆
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            ClientUser clientUser=new ClientUser();
            clientUser  = clientUserService.getById(ShiroKit.getUserId());
            issueIndexDTO.setUserName(clientUser.getUserName());
            issue.setProvince(clientUser.getProvince());
        }else{
            BaiDuAPIUtil baiDuAPIUtil=new BaiDuAPIUtil();

            issue.setProvince(baiDuAPIUtil.baiDuApiProvince(ip));
        }


        List<Issue> issues = baseMapper.selectIssue(issue);
        Set<String> cityNameSet = new HashSet<String>();
        Set<Integer> typeSet = new HashSet<Integer>();
        List<CityDTO> cityDTOS = new ArrayList<>();
        List<IssueTypeDTO> issueTypeDTOS=new ArrayList<>();
        List<IssueDTO> issueDTOS=new ArrayList<>();
        //将省名去重存放到set
        for(Issue issueVO:issues){
            String cityName = issueVO.getCity();
            int type = issueVO.getType();
            //set中不包含重复的
            if (!cityNameSet.contains(cityName)) {
                cityNameSet.add(cityName);
            }
            //set中不包含重复的
            if (!typeSet.contains(type)) {
                typeSet.add(type);
            }

        }
        //根据省名计算个数
        for(String str:cityNameSet){
            CityDTO cityDTO=new CityDTO();
            cityDTO.setCityName(str);
            int amount=0;
            for(int j=0;j<issues.size();j++){
                if(str!=null){
                    if(str.equals(issues.get(j).getCity())){
                        amount++;
                    }
                }
            }
            cityDTO.setAmount(amount);
            cityDTOS.add(cityDTO);
            //根据租房类型计算个数
            for(Integer type:typeSet){
                IssueTypeDTO issueTypeDTO=new IssueTypeDTO();
                issueTypeDTO.setTypeName(str);
                int typeAmount=0;
                for(int j=0;j<issues.size();j++){
                    if(str!=null){
                        if(str.equals(issues.get(j).getCity())){
                            typeAmount++;
                        }
                    }
                }
                issueTypeDTO.setAmount(typeAmount);
                issueTypeDTOS.add(issueTypeDTO);
            }
        }
        List<Issue> issueOut=issues.stream().limit(6).collect(Collectors.toList());
        for(Issue issue1:issueOut){
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
        issueIndexDTO.setCity(cityDTOS);

        return issueIndexDTO;
    }

    @Override
    public IssueIndexDTO listIssueDTOGo(String ip, String city, String province,String counties,String houseType,Integer moneyMin,Integer moneyMax,String rentOutType) {

        IssueIndexDTO issueIndexDTO=new IssueIndexDTO();
        Issue issue=new Issue();
        PropertiesGridScreenDTO propertiesGridScreenDTO=new PropertiesGridScreenDTO();
        propertiesGridScreenDTO.setHouseType(houseType);
        propertiesGridScreenDTO.setMoneyMax(moneyMax);
        propertiesGridScreenDTO.setMoneyMin(moneyMin);
        propertiesGridScreenDTO.setRentOutType(rentOutType);
        //判断是否登陆
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            ClientUser clientUser=new ClientUser();
            clientUser  = clientUserService.getById(ShiroKit.getUserId());
            issueIndexDTO.setUserName(clientUser.getUserName());
            propertiesGridScreenDTO.setProvince(clientUser.getProvince());
        }else if("".equals(province)||province==null){
            BaiDuAPIUtil baiDuAPIUtil=new BaiDuAPIUtil();
            propertiesGridScreenDTO.setProvince(baiDuAPIUtil.baiDuApiProvince(ip));
        }else {
            propertiesGridScreenDTO.setCity(city);
            propertiesGridScreenDTO.setCounty(counties);
            propertiesGridScreenDTO.setProvince(province);
        }
        List<Issue> issues = baseMapper.propertiesGridScreen(propertiesGridScreenDTO);
        Set<String> cityNameSet = new HashSet<String>();
        Set<Integer> typeSet = new HashSet<Integer>();
        List<CityDTO> cityDTOS = new ArrayList<>();
        List<IssueTypeDTO> issueTypeDTOS=new ArrayList<>();
        List<IssueDTO> issueDTOS=new ArrayList<>();
        //将省名去重存放到set
        for(Issue issueVO:issues){
            String cityName = issueVO.getCity();
            int type = issueVO.getType();
            //set中不包含重复的
            if (!cityNameSet.contains(cityName)) {
                cityNameSet.add(cityName);
            }
            //set中不包含重复的
            if (!typeSet.contains(type)) {
                typeSet.add(type);
            }

        }
        //根据省名计算个数
        for(String str:cityNameSet){
            CityDTO cityDTO=new CityDTO();
            cityDTO.setCityName(str);
            int amount=0;
            for(int j=0;j<issues.size();j++){
                if(str!=null){
                    if(str.equals(issues.get(j).getCity())){
                        amount++;
                    }
                }
            }
            cityDTO.setAmount(amount);
            cityDTOS.add(cityDTO);
            //根据租房类型计算个数
            for(Integer type:typeSet){
                IssueTypeDTO issueTypeDTO=new IssueTypeDTO();
                issueTypeDTO.setTypeName(str);
                int typeAmount=0;
                for(int j=0;j<issues.size();j++){
                    if(str!=null){
                        if(str.equals(issues.get(j).getCity())){
                            typeAmount++;
                        }
                    }
                }
                issueTypeDTO.setAmount(typeAmount);
                issueTypeDTOS.add(issueTypeDTO);
            }
        }
        List<Issue> issueOut=issues.stream().limit(6).collect(Collectors.toList());
        //把对应的图片放进去
        issue.setProvince(province);
        issue.setCity(city);
        issue.setCounty(counties);
        List<Issue> issueList=baseMapper.selectIssue(issue);
        for(Issue issue1:issueList){

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
        issueIndexDTO.setCity(cityDTOS);

        return issueIndexDTO;

    }

}
