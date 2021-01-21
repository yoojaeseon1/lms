package com.yoo.lms;

import com.yoo.lms.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LmsApplicationTests {


	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
	}

	@Test
	public void insertTest(){

		Student student = Student.createStudent("newId", "newPassword", "newName", 1234);

		em.persist(student);

		em.flush();
		em.clear();

	}

}
