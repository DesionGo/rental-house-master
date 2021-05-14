package com.rentalHouseClient.rhc.modules.sys.controller;


import com.rentalHouseClient.rhc.modules.sys.dto.CityDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.IndexDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import com.rentalHouseClient.rhc.modules.sys.service.FilesService;
import com.rentalHouseClient.rhc.modules.sys.service.issue.IssueService;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.util.BaiDuAPIUtil;

import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.service.IMenuService;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class IndexController extends BaseController {

    @Value("${kvf.ip}")
    private  String ip;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private IssueService issueService;




    @Autowired
    private FilesService filesService;

    @GetMapping(value = "/")
    public ModelAndView index(HttpServletRequest request) {


        IndexDTO indexDTO=new IndexDTO();
        Issue issue=new Issue();
        Set<String> set = new HashSet<String>();
        List<CityDTO> cityDTOS = new ArrayList<CityDTO>();
        List<IssueDTO> issueDTOS = new ArrayList<IssueDTO>();
        ClientUser clientUser=new ClientUser();
        Files files =new Files();
        String city = null,province = null;
        if( SecurityUtils.getSubject().getPrincipal()!=null){
       clientUser  = clientUserService.getById(ShiroKit.getUserId());
            indexDTO.setUserName(clientUser.getUserName());
            issue.setProvince(clientUser.getProvince());
            province=clientUser.getProvince();
            city= clientUser.getCity();
          //  issue.setCity(clientUser.getCity());
       }else{
            BaiDuAPIUtil baiDuAPIUtil=new BaiDuAPIUtil();
           province=baiDuAPIUtil.baiDuApiProvince(ip);
             city= baiDuAPIUtil.baiDuApiCity(ip);
            issue.setProvince(province);
        }
        List<Issue> issues= issueService.listIssue(issue);
//去重省名，放到set集合中
        for(int i=0;i<issues.size();i++) {
            String userName = issues.get(i).getCity();
            if (!set.contains(userName)) { //set中不包含重复的
                set.add(userName);

            }
        }
        //根据省名计算个数
        for(String str:set){
            Files file= filesService.selectFiles(str);
            CityDTO cityDTO=new CityDTO();
            if(file!=null){
                cityDTO.setUrl(file.getUrl());
            }

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
        }


        if(issues.size()>6){
            for(int i=0;i<6;i++){
                if(issues.get(i).getCity().equals(city)){
                    List<Files> files1=filesService.selectFilesById(issues.get(0).getId());
                    IssueDTO issueDTO=new IssueDTO();
                    BeanUtils.copyProperties(issues.get(i),issueDTO);
                    issueDTO.setUrl(files1.get(0).getUrl());
                    issueDTOS.add(issueDTO);
                }
            }
        }else{
            for(Issue issue1:issues){
                if(issue1.getCity().equals(city)){
                    List<Files> files1=filesService.selectFilesById(issues.get(0).getId());
                    IssueDTO issueDTO=new IssueDTO();
                    BeanUtils.copyProperties(issue1,issueDTO);
                    issueDTO.setUrl(files1.get(0).getUrl());
                    issueDTOS.add(issueDTO);
                }
            }
        }

        indexDTO.setCity(cityDTOS);
        indexDTO.setIssue(issueDTOS);
        return new ModelAndView("index").addObject("authUserInfo", indexDTO);
    }

    @GetMapping(value = "home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping(value = "test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }



}
