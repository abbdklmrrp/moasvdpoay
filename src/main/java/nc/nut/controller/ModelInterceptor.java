package nc.nut.controller;

import nc.nut.security.AuthorizedUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rysakova Anna
 */
@Component
public class ModelInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            nc.nut.entity.User currentUser = AuthorizedUser.nativeUser();
            if (currentUser != null) {
                modelAndView.addObject("user", currentUser); // jsp: ${user.userName}
            }
        }
    }
}
