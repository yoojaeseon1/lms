package com.yoo.lms;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.MemberRepository;
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

        public void dbInit1() {
            Student student = new Student("yoo1", "1234", "name1", 12, new Address("1","2","3"));
            Teacher teacher = new Teacher("yoo2", "1234", "name2", 12, new Address("1","2","3"));
            memberRepository.save(student);
            memberRepository.save(teacher);



            Course course1 = new Course("course1", teacher);
            Course course2 = new Course("course2", teacher);

            em.flush();

            courseRepository.save(course1);
            courseRepository.save(course2);

        }

//        public void dbInit2() {
//
//
//        }

    }
}
