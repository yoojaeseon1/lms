package com.yoo.lms.controller;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import com.yoo.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/createCourseForm")
    public String createCourseForm(){



        return "course/createCourseForm";
    }

    @PostMapping("/course")
    @ResponseBody
    public ResponseEntity<String> createCourse(@RequestBody Course course,
                                               HttpServletRequest request
                                               ) {

        log.info("createCourse - course.name : " + course.getName());
        log.info("createCourse - course.maxNumStudnet : " + course.getMaxNumStudent());
        log.info("createCourse - course.startDate : " + course.getStartDate());
        log.info("createCourse - course.endDate : " + course.getEndDate());

        HttpSession session = request.getSession();

        Teacher loginMember = (Teacher)session.getAttribute("loginMember");

        log.info("createCourse - loginMember.id : " + loginMember.getId());

//        course.addTeacher(loginMember);

        courseService.createCourse(course, loginMember.getId());

        ResponseEntity<String> entity = new ResponseEntity<>("success", HttpStatus.OK);

        return entity;
    }


    @GetMapping("/student/courseList")
    public String listCourse(CourseSearchCondition searchCondition,
                             String category,
                             String keyword,
                             boolean canApplicable,
                             Model model
                             ) {


        if(category.equals("courseName") && keyword.length() > 0) {
            searchCondition.setCourseName(keyword);
        } else if(category.equals("teacherName") && keyword.length() > 0){
            searchCondition.setTeacherName(keyword);
        }

        List<Course> courses = courseService.searchCourse(searchCondition, canApplicable);

//        log.info("listCourse - searchCondition.courseName : " + searchCondition.getCourseName());
//        log.info("listCourse - searchCondition.teacherName : " + searchCondition.getTeacherName());
//        log.info("listCourse - searchCondition.startDate : " + searchCondition.getStartDate());
//        log.info("listCourse - searchCondition.endDate : " + searchCondition.getEndDate());
//        log.info("listCourse - searchCondition.acceptType : " + searchCondition.getAcceptType());
//        log.info("listCourse - category : " + category);
//        log.info("listCourse - keyword : " + keyword.length());
//        log.info("listCourse - canApplicable : " + canApplicable);

//        for (Course cours : courses) {
//            log.info("listCourse - course.name : " + cours.getName());
//        }

//        courseService.permitCourse(courses.get(0));

        model.addAttribute("courses", courses);

        return "/course/courseList";
    }

    @PostMapping("/student/enrollCourse")
    public String enrollCourse(Long courseId,
                               HttpServletRequest request
                               ) {

        HttpSession session = request.getSession();

        MemberType memberType = (MemberType)session.getAttribute("memberType");



        if(memberType == MemberType.STUDENT) {
            Student loginMember = (Student) session.getAttribute("loginMember");
            courseService.enrollCourse(loginMember, courseId);
        }


        return "redirect:/courseList";
    }
}
