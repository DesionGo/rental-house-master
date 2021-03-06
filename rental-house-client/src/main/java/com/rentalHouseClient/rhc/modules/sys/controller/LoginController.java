package com.rentalHouseClient.rhc.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.rentalHouseClient.rhc.common.utils.FileUtils;
import com.rentalHouseClient.rhc.modules.sys.dto.AddPropertyDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
import com.rentalHouseClient.rhc.modules.sys.entity.issue.Issue;
import com.rentalHouseClient.rhc.modules.sys.entity.label.Label;
import com.rentalHouseClient.rhc.modules.sys.service.FilesService;
import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import com.rentalHouseClient.rhc.modules.sys.service.collect.CollectService;
import com.rentalHouseClient.rhc.modules.sys.service.issue.IssueService;
import com.rentalHouseClient.rhc.common.annotation.Log;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.common.utils.HttpServletContextKit;
import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueIndexDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.rentalHouseClient.rhc.modules.sys.service.label.LabelService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ?????????LayOA????????????<br>
 * ?????????(???)
 *
 * @author Kalvin
 * @Date 2019???05???05??? 15:14
 */
@RestController
public class LoginController extends BaseController {

    @Value(value = "${kvf.login.authcode.enable}")
    private boolean needAuthCode;

    @Value("${kvf.localPath}")
    private String localPath;

    @Value("${kvf.ip}")
    private String ip;

    @Value(value = "${kvf.login.authcode.dynamic}")
    private boolean isDynamic;

    @Autowired
    private IssueService issueService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private FilesService filesService;

    @GetMapping(value = "login")
    public ModelAndView login() {
        Subject subject = ShiroKit.getSubject();
        if (subject.isAuthenticated()) {
            return new ModelAndView("redirect:");
        }
        return new ModelAndView("sys/login");
    }
    @GetMapping(value = "register")
    public ModelAndView register() {

        return new ModelAndView("register");
    }
    @Log("??????")
    @PostMapping(value = "login")
    public R login(@RequestParam("email") String username, @RequestParam("password") String password, boolean rememberMe, String vercode) {

        // ??????????????????????????????????????????????????????yml??????kvf.login.authcode.enable??????????????????
        if (needAuthCode) {
            // ???????????????
            HttpServletRequest request = HttpServletContextKit.getHttpServletRequest();
            if (!CaptchaUtil.ver(vercode, request)) {
                CaptchaUtil.clear(request);  // ??????session???????????????
                return R.fail("??????????????????");
            }
        }

        try {
            Subject subject = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            subject.login(token);


        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return R.fail(e.getMessage());
        }

        return R.ok();
    }

    @Log("??????")
    @GetMapping("logout")
    public ModelAndView logout() {

        String username = ShiroKit.getSessionAttribute("userName").toString();
        ShiroKit.logout();
        LOGGER.info("{}????????????", username);
        return new ModelAndView("sys/login");
    }
    @Log("????????????")
    @GetMapping("propertiesGrid")
    public ModelAndView propertiesGrid(@RequestParam(value="current",required = false) int current) {

        IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ip, current);
        return  new ModelAndView("properties-grid").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("????????????(??????)")
    @GetMapping("propertiesGridScreen")
    public ModelAndView propertiesGridScreen(@RequestParam(value="current",required = false) Integer current,@RequestParam(value="province",required = false) String province,@RequestParam(value="city",required = false) String city,@RequestParam(value="counties",required = false) String counties,@RequestParam(value="houseType",required = false) String houseType,@RequestParam(value="moneyMin",required = false) Integer moneyMin,@RequestParam(value="moneyMax",required = false) Integer moneyMax,@RequestParam(value="rentOutType",required = false) String rentOutType) {


        if(current==null){
            current=1;
        }
        IssueIndexDTO issueIndexDTO= issueService.listIssueDTOGo(ip,city,province,counties,houseType,moneyMin,moneyMax,rentOutType,current);
        IssueDTO issueDTO=new IssueDTO();
        issueDTO.setCity(city);
        issueDTO.setProvince(province);
        issueDTO.setCounty(counties);
        issueIndexDTO.setIssueDTO(issueDTO);
        return  new ModelAndView("sys/user-properties-grid").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("????????????")
    @GetMapping("userProfile")
    public ModelAndView userProfile() {
       if(SecurityUtils.getSubject().getPrincipal()!=null){
           //??????????????????
           ClientUser clientUser = clientUserService.getByEmail(ShiroKit.getSessionAttribute("email").toString());
           return  new ModelAndView("user-profile").addObject("clientUser",clientUser);
       } else{
           return new ModelAndView("sys/login");
       }

    }
    @Log("??????????????????")
    @PostMapping("editUserProfile")
    public R editUserProfile(@RequestBody ClientUser clientUser) {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
        clientUser.setId(ShiroKit.getSessionAttribute("id").toString());
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createtime = dtf2.format(LocalDateTime.now());
        LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
        clientUser.setUpdateTime(ldt);
        clientUserService.updateUser(clientUser);
            return R.ok();
        } else{
            return R.fail("??????token???????????????????????????");
        }

    }
    @Log("????????????")
    @GetMapping("myProperties")
    public ModelAndView myProperties(@RequestParam(value="current",required = false) Integer current) {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            IssueIndexDTO issueIndexDTO=issueService.userIssue(ShiroKit.getSessionAttribute("id").toString(),current);
            return  new ModelAndView("my-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("????????????")
    @GetMapping("favoriteProperties")
    public ModelAndView favoriteProperties(@RequestParam(value="current",required = false) Integer current) {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            IssueIndexDTO issueIndexDTO=collectService.selectUserCollectList(ShiroKit.getSessionAttribute("id").toString(),current);
            issueIndexDTO.setCurrent(current);

            return  new ModelAndView("favorite-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("????????????")
    @GetMapping("deleteFavoriteProperties")
    public ModelAndView deleteFavoriteProperties(@RequestParam(value="issueId",required = false) String issueId) {

        if(SecurityUtils.getSubject().getPrincipal()!=null){
            collectService.updateStatus(issueId,ShiroKit.getSessionAttribute("id").toString());
            IssueIndexDTO issueIndexDTO=collectService.selectUserCollectList(ShiroKit.getSessionAttribute("id").toString(),1);
            issueIndexDTO.setCurrent(1);

            return  new ModelAndView("favorite-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("????????????")
    @GetMapping("addFavoriteProperties")
    public R addFavoriteProperties(@RequestParam(value="issueId") String issueId) {

        if(SecurityUtils.getSubject().getPrincipal()!=null) {
            Collect collect = new Collect();

            collect.setIssueId(issueId);
            collect.setUserId(ShiroKit.getSessionAttribute("id").toString());
            if (collectService.selectById(collect) == null) {
                collect.setId(UUID.randomUUID().toString());
                collect.setStatus(1);
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String createtime = dtf2.format(LocalDateTime.now());
                LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
                collect.setCreateTime(ldt);
                return collectService.add(collect);
            } else {
                return R.fail("??????????????????");
            }
        }else{
            return  R.fail(1000,"???????????????");
        }

    }
    @Log("??????")
    @GetMapping("addProperty")
    public ModelAndView addProperty() {
        if(SecurityUtils.getSubject().getPrincipal()!=null){

            IssueIndexDTO issueIndexDTO= new IssueIndexDTO();
            issueIndexDTO.setUserName(ShiroKit.getSessionAttribute("userName").toString());
            issueIndexDTO.setLabelList(labelService.list());
            return  new ModelAndView("add-property").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("????????????")
    @GetMapping("updateProperty")
    public ModelAndView updateProperty(@RequestParam(value="issueId",required = false) String issueId){

        IssueIndexDTO issueIndexDTO= issueService.IssueDetail(issueId);
            return  new ModelAndView("sys/update-property").addObject("issueIndexDTO",issueIndexDTO);

    }
    @Log("????????????")
    @PostMapping("addPropertyDetail")
    public R addPropertyDetail(@RequestBody AddPropertyDTO addPropertyDTO ) {


        if(SecurityUtils.getSubject().getPrincipal()!=null){
            if(addPropertyDTO.getLabel().size()==0){
                return R.fail(500,"??????????????????");
            }
            if(addPropertyDTO.getCity()==null){
                return R.fail(500,"?????????????????????");

            }
            if(addPropertyDTO.getContent()==null){
                return R.fail(500,"???????????????????????????");

            }
            if(addPropertyDTO.getCounty()==null){
                return R.fail(500,"?????????????????????");

            }
            if(addPropertyDTO.getHeadline()==null){
                return R.fail(500,"?????????????????????");

            }
            if(addPropertyDTO.getMoney()==0){
                return R.fail(500,"?????????????????????");

            }
            if(addPropertyDTO.getProvince()==null){
                return R.fail(500,"?????????????????????");

            }
            if(addPropertyDTO.getDetailSite()==null){
                return R.fail(500,"???????????????????????????");

            }
            if(addPropertyDTO.getType()==null){
                return R.fail(500,"???????????????????????????");

            }
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createtime = dtf2.format(LocalDateTime.now());
            LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
            addPropertyDTO.setCreateTime(ldt);

            return issueService.add(addPropertyDTO);
        } else{
            return R.fail(500,"??????token???????????????????????????");
        }
    }

    @Log("??????????????????")
    @GetMapping("deleteMyProperties")
    public ModelAndView deleteMyProperties(@RequestParam(value="issueId",required = false) String issueId) {
        Issue issue=new Issue();
        issue.setStatus("3");
        issue.setId(issueId);
        issueService.updateById(issue);
        IssueIndexDTO issueIndexDTO=issueService.userIssue(ShiroKit.getSessionAttribute("id").toString(),1);
        return  new ModelAndView("my-properties").addObject("issueIndexDTO",issueIndexDTO);


    }
    @Log("????????????")
    @GetMapping("propertySingleGallery")
    public ModelAndView propertySingleGallery(@RequestParam(value="issueId",required = false) String issueId) {
            IssueIndexDTO issueIndexDTO= issueService.IssueDetail(issueId);
            return  new ModelAndView("property-single-gallery").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("????????????")
    @RequestMapping("/multipleImageUpload")
    public List multipleImageUpload(@RequestParam("uploadFiles") MultipartFile[] files){
        System.out.println("?????????????????????"+files.length);
        Files files1=new Files();
        List<Map<String,Object>> root=new ArrayList<Map<String,Object>>();

        for (MultipartFile file : files) {    //??????????????????

            Map<String, Object> result = new HashMap<String, Object>();//???????????????????????????
            String result_msg = "";//??????????????????

          /*  if (file.getSize() / 1000 > 100){
                result_msg="????????????????????????100KB";
            }
            else{*/
            //????????????????????????
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // ?????????????????????????????????????????????
                final String localPath1 = localPath;
                //???????????????????????????(?????????????????????????????????????????????)
                //???????????????
                String fileName = file.getOriginalFilename();
                //?????????????????????
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //?????????????????????
                fileName = UUID.randomUUID() + suffixName;
                if (FileUtils.upload(file, localPath1, fileName)) {
                    //???????????????????????????(??????????????????????????????img?????????src)
                    files1.setId(UUID.randomUUID().toString());
                    files1.setUrl("/static/img/user/" + fileName);
                    files1.setAscriptionId(ShiroKit.getSessionAttribute("issueId").toString());
                    files1.setFileName(fileName);
                    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String createtime = dtf2.format(LocalDateTime.now());
                    LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
                    files1.setCreateDate(ldt);
                    files1.setFilePath(suffixName);
                    filesService.add(files1);
                    String relativePath = "user/" + fileName;
                    result.put("relativePath", relativePath);//????????????????????????????????????????????????????????????
                    result_msg = "??????????????????";
                } else {
                    result_msg = "??????????????????";
                }
            } else {
                result_msg = "?????????????????????";
            }
     //   }
            result.put("result_msg",result_msg);
            root.add(result);
        }
        String root_json= JSON.toJSONString(root);
        System.out.println(root_json);
        return root;
    }
    /**
     * ???????????????
     */
    @GetMapping(value = "captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ??????yml??????kvf.login.authcode.dynamic?????????????????????????????????????????????
        // ???????????????????????????????????????https://gitee.com/whvse/EasyCaptcha
        if (isDynamic) {
            CaptchaUtil.out(new GifCaptcha(), request, response);
        } else {
            CaptchaUtil.out(request, response);
        }
    }

}
