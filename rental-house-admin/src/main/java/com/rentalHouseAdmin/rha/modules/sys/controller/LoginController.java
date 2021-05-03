package com.rentalHouseAdmin.rha.modules.sys.controller;

import com.rentalHouseAdmin.rha.common.annotation.Log;
import com.rentalHouseAdmin.rha.common.controller.BaseController;
import com.rentalHouseAdmin.rha.common.dto.R;
import com.rentalHouseAdmin.rha.common.utils.HttpServletContextKit;
import com.rentalHouseAdmin.rha.common.utils.ShiroKit;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @GetMapping(value = "login")
    public ModelAndView login() {
        Subject subject = com.rentalHouseAdmin.rha.common.utils.ShiroKit.getSubject();

        if (subject.isAuthenticated()) {
            return new ModelAndView("redirect:");
        }
        return new ModelAndView("login");
    }

    @com.rentalHouseAdmin.rha.common.annotation.Log("登录")
    @PostMapping(value = "login")
    public com.rentalHouseAdmin.rha.common.dto.R login(@RequestParam("username") String username, @RequestParam("password") String password, boolean rememberMe, String vercode) {

        // 只有开启了验证码功能才需要验证。可在yml配置kvf.login.authcode.enable来开启或关闭
        if (needAuthCode) {
            // 验证码校验
            HttpServletRequest request = HttpServletContextKit.getHttpServletRequest();
            if (!CaptchaUtil.ver(vercode, request)) {
                CaptchaUtil.clear(request);  // 清除session中的验证码
                return com.rentalHouseAdmin.rha.common.dto.R.fail("验证码不正确");
            }
        }

        try {
            Subject subject = com.rentalHouseAdmin.rha.common.utils.ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            subject.login(token);

            com.rentalHouseAdmin.rha.common.utils.ShiroKit.setSessionAttribute("user", username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return com.rentalHouseAdmin.rha.common.dto.R.fail(e.getMessage());
        }

        return com.rentalHouseAdmin.rha.common.dto.R.ok();
    }

    @Log("退出")
    @GetMapping(value = "logout")
    public com.rentalHouseAdmin.rha.common.dto.R logout() {
        String username = com.rentalHouseAdmin.rha.common.utils.ShiroKit.getUser().getUsername();
        ShiroKit.logout();
        LOGGER.info("{}退出登录", username);
        return R.ok();
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
