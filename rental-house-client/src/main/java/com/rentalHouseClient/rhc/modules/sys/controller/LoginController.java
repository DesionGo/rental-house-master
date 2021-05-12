package com.rentalHouseClient.rhc.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.rentalHouseClient.rhc.common.utils.FileUtils;
import com.rentalHouseClient.rhc.modules.sys.dto.AddPropertyDTO;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.Files;
import com.rentalHouseClient.rhc.modules.sys.entity.collect.Collect;
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
 * 作用：LayOA系统登录<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月05日 15:14
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
    @Log("登录")
    @PostMapping(value = "login")
    public R login(@RequestParam("email") String username, @RequestParam("password") String password, boolean rememberMe, String vercode) {

        // 只有开启了验证码功能才需要验证。可在yml配置kvf.login.authcode.enable来开启或关闭
        if (needAuthCode) {
            // 验证码校验
            HttpServletRequest request = HttpServletContextKit.getHttpServletRequest();
            if (!CaptchaUtil.ver(vercode, request)) {
                CaptchaUtil.clear(request);  // 清除session中的验证码
                return R.fail("验证码不正确");
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

    @Log("退出")
    @GetMapping("logout")
    public ModelAndView logout() {

        String username = ShiroKit.getUser().getUserName();
        ShiroKit.logout();
        LOGGER.info("{}退出登录", username);
        return new ModelAndView("sys/login");
    }
    @Log("出租广场")
    @GetMapping("propertiesGrid")
    public ModelAndView propertiesGrid(@RequestParam(value="current",required = false) int current) {

        IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ip, current);
        return  new ModelAndView("properties-grid").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("出租广场(筛选)")
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
    @Log("个人资料")
    @GetMapping("userProfile")
    public ModelAndView userProfile() {
       if(SecurityUtils.getSubject().getPrincipal()!=null){
           //查询用户信息
           ClientUser clientUser = clientUserService.getByEmail(ShiroKit.getSessionAttribute("email").toString());
           return  new ModelAndView("user-profile").addObject("clientUser",clientUser);
       } else{
           return new ModelAndView("sys/login");
       }

    }
    @Log("修改个人信息")
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
            return R.fail("用户token过期，请重新登陆！");
        }

    }
    @Log("我的财产")
    @GetMapping("myProperties")
    public ModelAndView myProperties(@RequestParam(value="current",required = false) Integer current) {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            IssueIndexDTO issueIndexDTO=issueService.userIssue(ShiroKit.getSessionAttribute("id").toString(),current);
            return  new ModelAndView("my-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("我的收藏")
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
    @Log("取消收藏")
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
    @Log("添加收藏")
    @GetMapping("addFavoriteProperties")
    public R addFavoriteProperties(@RequestParam(value="issueId") String issueId) {

        if(SecurityUtils.getSubject().getPrincipal()!=null) {
            Collect collect = new Collect();

            collect.setIssueId(issueId);
            collect.setUserId(ShiroKit.getSessionAttribute("id").toString());
            if (collectService.selectById(collect) == null) {
                collect.setId(UUID.randomUUID().toString());
                collect.setStatus(1);
                return collectService.add(collect);
            } else {
                return R.fail("收藏过了噢！");
            }
        }else{
            return  R.fail(1000,"要登陆下咯");
        }

    }
    @Log("发布")
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
    @Log("修改发布")
    @GetMapping("updateProperty")
    public ModelAndView updateProperty(@RequestParam(value="issueId",required = false) String issueId){

        IssueIndexDTO issueIndexDTO= issueService.IssueDetail(issueId);
            return  new ModelAndView("sys/update-property").addObject("issueIndexDTO",issueIndexDTO);

    }
    @Log("发布信息")
    @PostMapping("addPropertyDetail")
    public R addPropertyDetail(@RequestBody AddPropertyDTO addPropertyDTO ) {


        if(SecurityUtils.getSubject().getPrincipal()!=null){

            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createtime = dtf2.format(LocalDateTime.now());
            LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
            addPropertyDTO.setCreateTime(ldt);

            return issueService.add(addPropertyDTO);
        } else{
            return R.fail("用户token过期，请重新登陆！");
        }
    }
    @Log("租房详情")
    @GetMapping("propertySingleGallery")
    public ModelAndView propertySingleGallery(@RequestParam(value="issueId",required = false) String issueId) {
            IssueIndexDTO issueIndexDTO= issueService.IssueDetail(issueId);
            return  new ModelAndView("property-single-gallery").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("上传图片")
    @RequestMapping("/multipleImageUpload")
    public List multipleImageUpload(@RequestParam("uploadFiles") MultipartFile[] files){
        System.out.println("上传的图片数："+files.length);
        Files files1=new Files();
        List<Map<String,Object>> root=new ArrayList<Map<String,Object>>();

        for (MultipartFile file : files) {    //循环保存文件

            Map<String, Object> result = new HashMap<String, Object>();//一个文件上传的结果
            String result_msg = "";//上传结果信息

          /*  if (file.getSize() / 1000 > 100){
                result_msg="图片大小不能超过100KB";
            }
            else{*/
            //判断上传文件格式
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath1 = localPath;
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //获取文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                if (FileUtils.upload(file, localPath1, fileName)) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
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
                    result.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                    result_msg = "图片上传成功";
                } else {
                    result_msg = "图片上传失败";
                }
            } else {
                result_msg = "图片格式不正确";
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
     * 图片验证码
     */
    @GetMapping(value = "captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 可在yml配置kvf.login.authcode.dynamic切换动静态图片验证码，默认静态
        // 其它验证码样式可前往查看：https://gitee.com/whvse/EasyCaptcha
        if (isDynamic) {
            CaptchaUtil.out(new GifCaptcha(), request, response);
        } else {
            CaptchaUtil.out(request, response);
        }
    }

}
