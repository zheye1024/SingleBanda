package top.yunsun.bicycle.common.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.yunsun.bicycle.common.config.ConstantConfig;
import top.yunsun.bicycle.controller.result.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BandaInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BandaInterceptor.class);

    /**
     * 重写preHandle方法，在请求发生之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("请求开始");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(ConstantConfig.USER_LOGIN_SESSION);
        if (userInfo == null) {
            response.sendRedirect("/singleBanda/index.html");
            return false;
        }
        return true;
    }

    /**
     * 重写postHandle方法，执行请求
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("本次请求处理时间为：" + new Long(endTime - startTime) + "ms");
        request.setAttribute("handlingTime", endTime - startTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion方法执行！");
    }
}
