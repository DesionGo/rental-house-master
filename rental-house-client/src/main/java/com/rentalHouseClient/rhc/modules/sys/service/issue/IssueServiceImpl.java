package com.rentalHouseClient.rhc.modules.sys.service.issue;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.common.utils.FileUtils;
import com.rentalHouseClient.rhc.modules.sys.dto.*;
import com.rentalHouseClient.rhc.modules.sys.mapper.labelItem.LabelItemMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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
    public IssueIndexDTO userIssue(String createId,int current) {
        List<Issue> issues=baseMapper.selectUserIssue(createId);

        IssueIndexDTO issueIndexDTO=new IssueIndexDTO();
        List<IssueDTO> issueDTOS=new ArrayList<>();
        ClientUser clientUser=new ClientUser();
        clientUser  = clientUserService.getById(ShiroKit.getUserId());
        issueIndexDTO.setUserName(clientUser.getUserName());
        List<Issue> issues1 =issues.stream().skip((current-1)*6).limit(6).collect(Collectors.toList());
        for(Issue issue1:issues1){
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
        issueIndexDTO.setCurrent(current);
        issueIndexDTO.setSum(issues1.size());
        issueIndexDTO.setIssue(issueDTOS);
        return issueIndexDTO;
    }

    @Override
    public IssueIndexDTO listIssueDTO(String ip,int current) {
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
        List<Issue> issueOut=issues.stream().skip((current-1)*6).limit(6).collect(Collectors.toList());
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
        issueIndexDTO.setCurrent(current);
        issueIndexDTO.setSum(issueOut.size());
        return issueIndexDTO;
    }

    @Override
    public IssueIndexDTO listIssueDTOGo(String ip, String city, String province,String counties,String houseType,Integer moneyMin,Integer moneyMax,String rentOutType,int current) {

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

        //把对应的图片放进去
        issue.setProvince(province);
        issue.setCity(city);
        issue.setCounty(counties);
        List<Issue> issueList=baseMapper.selectIssue(issue);
        List<Issue> issueOut=issueList.stream().skip((current-1)*6).limit(6).collect(Collectors.toList());
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
        issueIndexDTO.setCurrent(current);
        issueIndexDTO.setSum(issueOut.size());
        return issueIndexDTO;

    }

    @Override
    public R add(AddPropertyDTO addPropertyDTO) {
        Issue issue=new Issue();
        BeanUtils.copyProperties(addPropertyDTO,issue);
        issue.setId(UUID.randomUUID().toString());
        issue.setStatus("2");
        issue.setCreateUserId(ShiroKit.getSessionAttribute("id").toString());
        issue.setCreateUserName(ShiroKit.getSessionAttribute("userName").toString());
        try{
            baseMapper.add(issue);
        }catch (Exception e){
            return R.fail(500,"发布失败");
        }


        //绑定标签
        for(String id:addPropertyDTO.getLabel()){
            LabelItem labelItem=new LabelItem();
            labelItem.setId(UUID.randomUUID().toString());
            labelItem.setLabelId(id);
            labelItem.setUseId(issue.getId());
            labelItem.setType(1);
            try{
                labelItemService.add(labelItem);
            }catch (Exception e){
               return R.fail(500,"绑定标签失败");
            }

        }


        //上传图片 绑定图片
     /*   for (MultipartFile file : files) {    //循环保存文件
            Files files1=new Files();
            Map<String,Object> result=new HashMap<String, Object>();//一个文件上传的结果


            if (file.getSize() / 1000 > 100){

                return R.fail(500,"图片大小不能超过100KB");
            }
            else{
                //判断上传文件格式
                String fileType = file.getContentType();
                if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                    // 要上传的目标文件存放的绝对路径
                    final String localPath="/static/img/overlay/";
                    //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                    //获取文件名
                    String fileName = file.getOriginalFilename();
                    //获取文件后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    //重新生成文件名
                    fileName = UUID.randomUUID()+suffixName;
                    if (FileUtils.upload(file, localPath, fileName)) {
                        //文件存放的相对路径(一般存放在数据库用于img标签的src)
                        files1.setUrl(localPath+fileName);
                        files1.setAscriptionId(issue.getId());
                        files1.setFileName(fileName);
                        files1.setFilePath(suffixName);
                        filesService.add(files1);
                        String relativePath="overlay/"+fileName;
                        result.put("relativePath",relativePath);//前端根据是否存在该字段来判断上传是否成功


                    }
                    else{

                        return R.fail(500,"图片上传失败");
                    }
                }
                else{
                    return R.fail(500,"图片格式不正确");

                }
            }

        }*/
        ShiroKit.setSessionAttribute("issueId", issue.getId());
        return R.ok();
    }

    @Override
    public IssueIndexDTO IssueDetail(String issueId) {

        //获取单子的数据
      Issue issue=  baseMapper.selectById(issueId);
      //获取单子发布者的信息
     ClientUser clientUser= clientUserService.getById(issue.getCreateUserId());
     //获取单子所对应的标签
    List<LabelItem> labelItems=  labelItemService.selectService(issueId);
    List<Label> label =new ArrayList<>();
    for(LabelItem labelItem:labelItems){
        label.add(labelService.getById(labelItem.getLabelId()));
    }
    //获取单子所对应的照片
    List<Files> files=filesService.selectFilesById(issueId);

        IssueDTO issueDTO=new IssueDTO();
        BeanUtils.copyProperties(issue,issueDTO);
        issueDTO.setLabel(label);
        issueDTO.setFiles(files);
        IssueIndexDTO issueIndexDTO=new IssueIndexDTO();
        issueIndexDTO.setClientUser(clientUser);
        issueIndexDTO.setIssueDTO(issueDTO);
        issueIndexDTO.setUserName(clientUser.getUserName());
        return issueIndexDTO;
    }

    @Override
    public List<Issue> selectUserCollectList(String userId) {
        return baseMapper.selectUserCollectList(userId);
    }


}
