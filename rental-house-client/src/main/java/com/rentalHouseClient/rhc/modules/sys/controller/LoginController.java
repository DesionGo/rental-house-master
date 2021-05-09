package com.rentalHouseClient.rhc.modules.sys.controller;

import com.rentalHouseClient.rhc.modules.sys.service.clientUser.ClientUserService;
import com.rentalHouseClient.rhc.modules.sys.service.issue.IssueService;
import com.rentalHouseClient.rhc.common.annotation.Log;
import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.common.utils.HttpServletContextKit;
import com.rentalHouseClient.rhc.common.utils.ShiroKit;
import com.rentalHouseClient.rhc.modules.sys.dto.IssueIndexDTO;
import com.rentalHouseClient.rhc.modules.sys.entity.clientUser.ClientUser;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Value(value = "${kvf.login.authcode.dynamic}")
    private boolean isDynamic;

    @Autowired
    private IssueService issueService;


    @Autowired
    private ClientUserService clientUserService;


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

            ShiroKit.setSessionAttribute("user", username);
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
    public ModelAndView propertiesGrid() {
        String ipAddress = "27.156.190.52";
        IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ipAddress);
        return  new ModelAndView("properties-grid").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("出租广场(筛选)")
    @GetMapping("propertiesGridScreen")
    public ModelAndView propertiesGridScreen(@RequestParam(value="province",required = false) String province,@RequestParam(value="city",required = false) String city,@RequestParam(value="counties",required = false) String counties,@RequestParam(value="houseType",required = false) String houseType,@RequestParam(value="moneyMin",required = false) Integer moneyMin,@RequestParam(value="moneyMax",required = false) Integer moneyMax,@RequestParam(value="rentOutType",required = false) String rentOutType) {
        String ipAddress = "27.156.190.52";
        System.out.println("province"+province);
        IssueIndexDTO issueIndexDTO= issueService.listIssueDTOGo(ipAddress,province,city,counties,houseType,moneyMin,moneyMax,rentOutType);
        return  new ModelAndView("properties-grid").addObject("issueIndexDTO",issueIndexDTO);
    }
    @Log("个人资料")
    @GetMapping("userProfile")
    public ModelAndView userProfile() {
       if(SecurityUtils.getSubject().getPrincipal()!=null){
           //查询用户信息
           ClientUser clientUser = clientUserService.getByEmail(ShiroKit.getSessionAttribute("user").toString());
           return  new ModelAndView("user-profile").addObject("clientUser",clientUser);
       } else{
           return new ModelAndView("sys/login");
       }

    }
    @Log("修改个人信息")
    @PostMapping("editUserProfile")
    public R editUserProfile(@RequestBody ClientUser clientUser) {
        clientUser.setId(ShiroKit.getSessionAttribute("id").toString());
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createtime = dtf2.format(LocalDateTime.now());
        LocalDateTime ldt = LocalDateTime.parse(createtime, dtf2);
        clientUser.setUpdateTime(ldt);
        clientUserService.updateUser(clientUser);
        return R.ok();
    }
    @Log("我的财产")
    @GetMapping("myProperties")
    public ModelAndView myProperties() {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            String ipAddress = "27.156.190.52";

            IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ipAddress);
            return  new ModelAndView("my-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("我的收藏")
    @GetMapping("favoriteProperties")
    public ModelAndView favoriteProperties() {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            String ipAddress = "27.156.190.52";

            IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ipAddress);
            return  new ModelAndView("favorite-properties").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("发布")
    @GetMapping("addProperty")
    public ModelAndView addProperty() {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            String ipAddress = "27.156.190.52";

            IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ipAddress);
            return  new ModelAndView("add-property").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
    }
    @Log("租房详情")
    @GetMapping("propertySingleGallery")
    public ModelAndView propertySingleGallery() {
        if(SecurityUtils.getSubject().getPrincipal()==null){
            String ipAddress = "27.156.190.52";

            IssueIndexDTO issueIndexDTO= issueService.listIssueDTO(ipAddress);
            return  new ModelAndView("property-single-gallery").addObject("issueIndexDTO",issueIndexDTO);
        } else{
            return new ModelAndView("sys/login");
        }
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
