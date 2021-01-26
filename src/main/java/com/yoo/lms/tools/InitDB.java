package com.yoo.lms.tools;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.CourseBoardRepository;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.MemberRepository;
import com.yoo.lms.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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

        public void dbInit1() {

            Teacher[] teachers = new Teacher[51];

            for(int i = 1; i <= 50; i++) {
                Teacher teacher = new Teacher("teacher"+i, "1234", "name"+i, 12, new Address("1","2","3"));
                memberRepository.save(teacher);
                teachers[i] = teacher;
            }

            Student[] students = new Student[51];

            for(int i = 1; i <= 50; i++) {
                Student student = new Student("student"+i, "1234", "name"+i, 12, new Address("1","2","3"));
                memberRepository.save(student);
                students[i] = student;
            }



            em.flush();

            Course[] courses = new Course[51];

            for(int i = 1; i <= 50; i++) {
                Course course = new Course("course"+i, teachers[i]);
                courseRepository.save(course);
                courses[i] = course;
            }

            em.flush();

            // create courseBoard

            for(int i = 1; i <= 50; i++) {

                CourseBoard courseBoard = new CourseBoard("title"+i, "content"+i, teachers[i], courses[i]);
                courseBoardRepository.save(courseBoard);

            }

            for (int i = 0; i <= 50; i++) {
                QuestionBoard questionBoard = new QuestionBoard(courses[i], "QBoardTitle"+i, "QBoardContent"+i, students[i]);
                questionBoardRepository.save(questionBoard);
            }


        }

//        public void dbInit2() {
//
//
//        }

    }
}
