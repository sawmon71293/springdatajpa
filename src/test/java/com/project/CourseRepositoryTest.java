package com.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.project.model.Course;
import com.project.repository.CourseRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseRepositoryTest {
	
	@Autowired
	private CourseRepository repo;
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void itShouldCheckSaveCourse() {
		
		//Given
		String courseName="C";
		Course course1=new Course(courseName);
		//when
		 Course savedCourse1=entityManager.persist(course1);
		
		//then
		 assertNotNull(savedCourse1);
	
		
		
	}
	
	@Test
	public void itShouldCheckFindAllCourse() {
		
		//Given
		String courseName="C";
		Course course1=new Course(courseName);
		Course course2=new Course();
		entityManager.persist(course1);
		
		
		//when
		  List<Course> courseList=repo.findAll();
		
		//then
		 assertNotNull(courseList);
	
		 //Getters setters
		System.out.println(course1.toString());
		course1.getId();
		course1.getName();
		course2.setId(2);
		course2.setName("C++");
		Course course3=new Course(3);
		course3.setName("Python");
		
		
	}

}
