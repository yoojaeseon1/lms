package com.yoo.lms.interceptor;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.service.CourseService;
import com.yoo.lms.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberInterceptor implements HandlerInterceptor {

    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle() requestURI : {}", request.getRequestURI());
        log.info("preHandle() requestURL : {}", request.getRequestURL());



        HttpSession httpSession = request.getSession();
        Member loginMember = (Member)httpSession.getAttribute("loginMember");

        if(loginMember == null) {
            response.sendRedirect("/loginForm");
            return false;
        }

//        String[] paths = request.getRequestURI().split("/");
        String requestURI = request.getRequestURI();

        StringBuilder path = new StringBuilder();


        int requestI = 1;
        boolean isVisitslash = false;
        while(requestI < requestURI.length()) {
            char currentChar = requestURI.charAt(requestI);
            if(currentChar == '/') {
                if(isVisitslash)
                    break;
                else
                    isVisitslash = true;
            }
            path.append(currentChar);
            requestI++;
        }

        String[] paths = path.toString().split("/");

        if(paths[0].equals("courses")) {

            Long courseId = Long.parseLong(paths[1]);

            if(loginMember.getMemberType() == MemberType.TEACHER) {
                if(!courseService.existCourseByTeacherIdAndCourseId(courseId, loginMember.getId())) {
                    response.sendRedirect("/");
                    return false;
                }
            } else if(loginMember.getMemberType() == MemberType.STUDENT) {
                if(!studentCourseService.existStudentCourse(courseId, loginMember.getId())) {
                    response.sendRedirect("/");
                    return false;
                }
            }
        }

        return true;
    }
}
