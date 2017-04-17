package nc.nut.controller;

import nc.nut.security.SecurityAuthenticationHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ModelInterceptor extends HandlerInterceptorAdapter {
    @Resource
    ApplicationContext applicationContext;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.isReference() && !modelAndView.getViewName().startsWith("redirect:")) {
            SecurityAuthenticationHelper securityAuthenticationHelper = applicationContext.getBean(SecurityAuthenticationHelper.class);
            User currentUser = securityAuthenticationHelper.getCurrentUser();
            if (currentUser != null) {
                modelAndView.addObject("user", currentUser); // jsp: ${user.userName}
            }
        }
    }
}
