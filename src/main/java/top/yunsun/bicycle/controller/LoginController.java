package top.yunsun.bicycle.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.yunsun.bicycle.common.config.ConstantConfig;
import top.yunsun.bicycle.controller.result.UserInfo;
import top.yunsun.bicycle.core.redis.RedisUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/index.html")
    public ModelAndView login(ModelMap modelMap, HttpSession session) {
        logger.info("跳转首页");
        List<UserInfo> list = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setSex("男");
        userInfo.setCreateTime(new Date());
        session.setAttribute(ConstantConfig.USER_LOGIN_SESSION, userInfo);
        list.add(userInfo);
        for (int i = 0; i <= 2; i++) {
            UserInfo user = new UserInfo();
            user.setName("admin_" + i);
            user.setSex("男");
            user.setCreateTime(new Date());
            list.add(user);
        }
        modelMap.put("userList", list);
        modelMap.put("system", "admin:超级管理员");
        return new ModelAndView("index");
    }

    @RequestMapping("/list.json")
    public UserInfo list(ModelMap modelMap, HttpSession session) {
        logger.info("跳转集合页面");
        UserInfo user = (UserInfo) session.getAttribute(ConstantConfig.USER_LOGIN_SESSION);
        return user;
    }

    @RequestMapping("/result.html")
    public ModelAndView result(ModelMap modelMap) {
        return new ModelAndView("404");
    }

}
