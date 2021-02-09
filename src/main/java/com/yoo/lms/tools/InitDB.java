package com.yoo.lms.tools;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
//        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final CourseRepository courseRepository;
        private final CourseBoardRepository courseBoardRepository;
        private final QuestionBoardRepository questionBoardRepository;
        private final CounselBoardRepository counselBoardRepository;
        private final HomeworkBoardRepository homeworkBoardRepository;
        private final StudentCourseRepository studentCourseRepository;
        private final AttendanceRepository attendanceRepository;

        public void dbInit1() {

            // create member(teacher)

            Teacher[] teachers = new Teacher[51];

            for(int i = 1; i <= 50; i++) {

                Teacher teacher = new Teacher("teacherId"+i,
                        "1234",
                        "teacherName"+i,
                        "email"+i,
                        12,
                        new Address("1","2","3"),
                        LocalDate.now(),
                        MemberType.TEACHER);

                memberRepository.save(teacher);
                teachers[i] = teacher;
            }

            // create member(student)

            Student[] students = new Student[51];

            for(int i = 1; i <= 50; i++) {

                Student student = new Student("studentId"+i,
                        "1234",
                        "studentName"+i,
                        "email"+i,
                        12,
                        new Address("1","2","3"),
                        LocalDate.now(),
                        MemberType.STUDENT);

                memberRepository.save(student);
                students[i] = student;
            }

            em.flush();

            // create course

            Course[] courses = new Course[51];

            for(int i = 1; i <= 50; i++) {

                Course course = new Course(
                        "course"+i,
                        teachers[i],
                        50,
                        0,
                        LocalDate.of(2020,12,1),
                        LocalDate.of(2021,3,1));

                courseRepository.save(course);
                courses[i] = course;
            }

            em.flush();

            // enroll course(student)

            for(int i = 1; i <= 20; i++) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.enrollCourse(students[i], courses[1]);
                studentCourseRepository.save(studentCourse);

            }

            em.flush();

            // create attendance

//            for(int i = 1; i <= 10; i++) {
//                Attendance attendance = new Attendance(courses[1], students[i], LocalDate.now(), AttendanceType.ATTENDANCE);
//                attendanceRepository.save(attendance);
//            }
//
            for(int i = 1; i <= 10; i++) {
                Attendance attendance = new Attendance(courses[1], students[i], LocalDate.of(2021,2,7), AttendanceType.ATTENDANCE);
                attendanceRepository.save(attendance);
            }

            for(int i = 11; i <= 20; i++) {
                Attendance attendance = new Attendance(courses[1], students[i], LocalDate.of(2021,2,7), AttendanceType.LATENESS);
                attendanceRepository.save(attendance);
            }

            // create courseBoard

//            for(int i = 1; i <= 50; i++) {
//
//                CourseBoard courseBoard = new CourseBoard( courses[i], "title"+i, "content"+i, teachers[i], teachers[i]);
//                courseBoardRepository.save(courseBoard);
//
//            }

//            for(int i = 1; i <= 157; i++) {
//
//                CourseBoard courseBoard = new CourseBoard( courses[2], "title"+i, "content"+i, teachers[i % 50]);
//                courseBoardRepository.save(courseBoard);
//
//            }

            // create questionBoard

            for (int i = 1; i <= 157; i++) {
                QuestionBoard questionBoard = new QuestionBoard(courses[1], "질문 게시판"+i, "QBoardContent"+i, students[i % 50]);
//                questionBoard.initReply("replyTitle", "replyContent");
                questionBoardRepository.save(questionBoard);
            }


            /**
             * create counselBoard
             */

//            for(int i = 1; i <= 50; i++) {
//                CounselBoard counselBoard = new CounselBoard("counselBoardTitle"+i, "counselBoardContent"+i, teachers[i]);
//                counselBoardRepository.save(counselBoard);
//            }

            /**
             * create homeworkBoard
             */

//            for(int i = 1; i <= 50; i++) {
//                HomeworkBoard homeworkBoard = new HomeworkBoard(courses[2],"homeworkBoardTitle"+i, "homeworkBoardContent"+i,  teachers[i]);
//                homeworkBoardRepository.save(homeworkBoard);
//            }
        }

//        public void dbInit2() {
//
//
//        }

    }
}
