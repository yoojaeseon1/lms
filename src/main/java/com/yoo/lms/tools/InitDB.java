package com.yoo.lms.tools;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.*;
import com.yoo.lms.service.InquiryBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        private final HomeworkBoardRepository homeworkBoardRepository;
        private final StudentCourseRepository studentCourseRepository;
        private final AttendanceRepository attendanceRepository;
        private final InquiryBoardService inquiryBoardService;

        public void dbInit1() {

            // create admin

            Member admin = new Member("admin",
                    "1234",
                    "adminName",
                    "you8054@nate.com",
                    new Address("1","2","3"),
                    LocalDate.now(),
                    MemberType.ADMIN);

            memberRepository.save(admin);

            // create member(teacher)

            Teacher[] teachers = new Teacher[51];

            for(int i = 1; i <= 50; i++) {

                Teacher teacher = new Teacher("teacherid"+i,
                        "1234",
                        "teacherName"+i,
                        "you8054@nate.com",
                        new Address("1","2","3"),
                        LocalDate.now(),
                        MemberType.TEACHER);

                teacher.changeAcceptToAccepted();
                memberRepository.save(teacher);
                teachers[i] = teacher;
            }

//            teachers[1].changeAcceptToAccepted();
//            memberRepository.save(teachers[1]);

            // create member(student)

            Student[] students = new Student[51];

            for(int i = 1; i <= 50; i++) {

                Student student = new Student("studentid"+i,
                        "1234",
                        "studentName"+i,
                        "you8054@nate.com",
                        new Address("1","2","3"),
                        LocalDate.now(),
                        MemberType.STUDENT);

                memberRepository.save(student);
                students[i] = student;
            }

            Student student = new Student(
                    "you8054",
                    "QWer!@34",
                    "유재선",
                    "you8054@nate.com",
                    new Address("1","2","3"),
                    LocalDate.now(),
                    MemberType.STUDENT
            );

            memberRepository.save(student);



            em.flush();

            // create course

            Course[] courses = new Course[51];

            for(int i = 1; i <= 10; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,10, 1),
                        LocalDate.of(2021,3,1));
                course.addTeacher(teachers[i]);
                courseRepository.save(course);
                courses[i] = course;
            }

            for(int i = 11; i <= 20; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,11, 1),
                        LocalDate.of(2021,3,1));

                course.acceptCourse();
                course.addTeacher(teachers[i]);
                courseRepository.save(course);
                courses[i] = course;
            }

            for(int i = 21; i <= 30; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,12, 1),
                        LocalDate.of(2021,3,1));
                course.acceptCourse();
                course.addTeacher(teachers[i]);
                courseRepository.save(course);
                courses[i] = course;
            }

            for(int i = 31; i <= 35; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,12, 1),
                        LocalDate.of(2021,3,1));
                course.acceptCourse();
                course.addTeacher(teachers[1]);
                courseRepository.save(course);
                courses[i] = course;
            }

            for(int i = 36; i <= 40; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,12, 1),
                        LocalDate.of(2021,3,1));

                course.addTeacher(teachers[1]);
                courseRepository.save(course);
                courses[i] = course;
            }

            for(int i = 41; i <= 45; i++) {

                Course course = new Course(
                        "course"+i,
                        50,
                        LocalDate.of(2020,12, 1),
                        LocalDate.of(2021,3,1));


                course.rejectCourse();
                course.addTeacher(teachers[1]);
                courseRepository.save(course);
                courses[i] = course;
            }

//            em.flush();

            // enroll course(student)

            for(int i = 1; i <= 20; i++) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.enrollCourse(students[i], courses[1]);
                studentCourseRepository.save(studentCourse);
            }

            for(int i = 1; i <= 20; i++) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.enrollCourse(students[i], courses[2]);
                studentCourseRepository.save(studentCourse);
            }

//            em.flush();

            // create attendance

            LocalDateTime currenTime = LocalDateTime.now();

            for(int i = 1; i <= 10; i++) {
                Attendance attendance = new Attendance(courses[1], students[i], currenTime, AttendanceType.ATTENDANCE);
                attendanceRepository.save(attendance);
            }

//
            for(int i = 1; i <= 10; i++) {
                Attendance attendance = new Attendance(courses[1], students[i], currenTime, AttendanceType.ATTENDANCE);
                attendanceRepository.save(attendance);
            }

            for(int i = 11; i <= 20; i++) {
                Attendance attendance = new Attendance(courses[1], students[i], currenTime, AttendanceType.LATENESS);
                attendanceRepository.save(attendance);
            }

            /**
             * create courseBoard
             */

            for(int i = 1; i <= 50; i++) {

//                CourseBoard courseBoard = new CourseBoard(courses[i], "title"+i, "content"+i, teachers[i], teachers[i]);
                CourseBoard courseBoard = new CourseBoard(courses[1], "title"+i, "content"+i, teachers[1]);
                courseBoardRepository.save(courseBoard);

            }

            for(int i = 1; i <= 57; i++) {

                CourseBoard courseBoard = new CourseBoard(courses[1], "title"+i, "content"+i, teachers[1]);
                courseBoardRepository.save(courseBoard);

            }

            /**
             * create questionBoard
              */

            for (int i = 1; i <= 57; i++) {
                QuestionBoard questionBoard = new QuestionBoard(courses[1], "질문 게시판" + i, "QBoardContent"+i, students[1]);
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
             * create inquiryBoard
             */

            for(int i = 1; i <= 200; i++) {

                InquiryBoard inquiryBoard = new InquiryBoard("inquiryTitle" + i, "hahahoho", students[1]);
                inquiryBoardService.save(inquiryBoard);

            }

            /**
             * create homeworkBoard
             */

            HomeworkBoard[] homeworkBoards = new HomeworkBoard[51];

            for(int i = 1; i <= 50; i++) {
                homeworkBoards[i] = new HomeworkBoard(courses[1],"homeworkBoardTitle"+i, "homeworkBoardContent"+i,  teachers[1]);
                homeworkBoardRepository.save(homeworkBoards[i]);
            }

            /**
             * boardReply(homework)
             *
             */


//            for(int i = 1; i <= 20 ; i++) {
//
//                BoardReply boardReply = new BoardReply("title "+i, "content "+i, students[i], homeworkBoards[50]);
//
//                boardReplyRepository.save(boardReply);
//
//            }

        }



//        public void dbInit2() {
//
//
//        }

    }
}
