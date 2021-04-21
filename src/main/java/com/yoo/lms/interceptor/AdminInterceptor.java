package com.yoo.lms.interceptor;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.enumType.MemberType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession httpSession = request.getSession();
        Member loginMember = (Member)httpSession.getAttribute("loginMember");

        if(loginMember == null) {
            response.sendRedirect("/loginForm");
            return false;
        } else if(loginMember.getMemberType() != MemberType.ADMIN) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
