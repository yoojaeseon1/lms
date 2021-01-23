package com.yoo.lms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static com.yoo.lms.domain.QStudent.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LmsApplicationTests {


	@Autowired
	EntityManager em;

	@Autowired
	private MemberRepository memberRepository;

//	@BeforeEach
//	public void before(){
//
//		Student student1 = new Student("yoo1", "1234", "name1", 12, new Address("1","2","3"));
//		Student student2 = new Student("yoo2", "1234", "name2", 12, new Address("1","2","3"));
//
//
//		em.persist(student1);
//		em.persist(student2);
//
//
//	}

	@Test
	void contextLoads() {
	}

	@Test
	public void insertTest(){



		em.flush();
		em.clear();


		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		Student findStudent = jpaQueryFactory.selectFrom(student)
				.where(student.id.eq("yoo1"))
				.fetchOne();

		assertThat(findStudent.getName()).isEqualTo("name1");

	}

	@Test
	public void dataJpaTest(){


		Member yoo1 = memberRepository.findById("yoo1").get();

		assertThat(yoo1.getName()).isEqualTo("name1");

	}

	@Test
	public void dateTest(){

		LocalDate date = LocalDate.of(2021,1,1).with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));

		System.out.println("date123 = " + date);
	}

	@Test
	public void insertTest2(){

		Teacher teacher = new Teacher("teacherId", "1234", "name", 23, new Address("1","2","3"));
		em.persist(teacher);

		Course course = new Course("courseName", teacher);
		course.addTeacher(teacher);

		em.persist(course);

		CourseSchedule courseSchedule = new CourseSchedule();
		courseSchedule.addCourse(course);

		em.persist(courseSchedule);

	}

}
