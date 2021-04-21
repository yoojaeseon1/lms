package com.yoo.lms.controller;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import com.yoo.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;


    @GetMapping("/courses/new")
    public String createCourseForm(){

        return "teacher/createCourseForm";
    }

    @PostMapping("/courses/new")
    @ResponseBody
    public ResponseEntity<String> createCourse(@RequestBody Course course,
                                               HttpSession session) {


        Teacher loginMember = (Teacher)session.getAttribute("loginMember");

        courseService.createCourse(course, loginMember);

        ResponseEntity<String> entity = new ResponseEntity<>("ok", HttpStatus.OK);

        return entity;
    }

    @GetMapping("/courses/{courseId}")
    public String showCourseMain(@PathVariable Long courseId,
                                 HttpSession session){

        session.setAttribute("sideBarType", "course");
        session.setAttribute("courseId", courseId);

        return "course/courseMain";
    }



    @GetMapping("/student/enroll-courses")
    public String listCourse(CourseSearchCondition searchCondition,
                             String category,
                             String keyword,
                             Model model,
                             HttpSession session) {


        if(keyword != null) {

            if (category.equals("courseName") && keyword.length() > 0) {
                searchCondition.setCourseName(keyword);
            } else if (category.equals("teacherName") && keyword.length() > 0) {
                searchCondition.setTeacherName(keyword);
            }
        }

        Member loginMember = (Member)session.getAttribute("loginMember");

        List<Course> courses = null;

        if(searchCondition.getAcceptType() == null)
            courses = courseService.searchCourse(searchCondition, null);
        else
            courses = courseService.searchCourse(searchCondition, loginMember.getId());

        List<CourseListDto> courseListDtos = (ArrayList<CourseListDto>)session.getAttribute("courseListDtos");

        if(courseListDtos != null) {
            List<Long> courseIds = new ArrayList<>();

            for (CourseListDto courseListDto : courseListDtos) {
                courseIds.add(courseListDto.getId());
            }
            model.addAttribute("courseIds", courseIds);
        }

        model.addAttribute("courses", courses);

        return "/course/courseList";
    }


    @ResponseBody
    @PostMapping("/student/enroll-course")
    public ResponseEntity<String> enrollCourse(@RequestBody Map<String, Object> paramMap,
                                                HttpSession session) {



        Long courseId = Long.parseLong((String)paramMap.get("courseId"));

        Member loginMember = (Member)session.getAttribute("loginMember");

        if(loginMember.getMemberType() == MemberType.STUDENT) {

            loginMember = (Student) loginMember;

            Course course = courseService.enrollCourse(loginMember.getId(), courseId);

            if(course == null)
                return new ResponseEntity<>("fail", HttpStatus.OK);

            List<CourseListDto> courseListDtos = (ArrayList<CourseListDto>)session.getAttribute("courseListDtos");
            courseListDtos.add(new CourseListDto(course.getId(), course.getName()));

        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
