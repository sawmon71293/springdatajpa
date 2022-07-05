package com.project;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.project.model.Course;
import com.project.model.Student;
import com.project.repository.StudentRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TestEntityManager entityManager;
	
	
	public  void testCreateCourse() {

		
		  Course course1=new Course("C++"); 
		  Course course2=new Course("C#"); 
		  Course course3=new Course("Java"); entityManager.persist(course1);
		  entityManager.persist(course2); entityManager.persist(course3);
		 
		
	}
	
	
	public void createNewStudent() {
		
		  Course course1= entityManager.find(Course.class, 1); 
		  System.out.println(course1);
		  Student student= new Student("saw mon","1993-05-02","female", "09251275847","Bachelor of Computer Science"); 
		  		student.addCourse(course1);
		  System.out.println(student.toString());
		  studentRepository.save(student);
		 
	}
	
	
	@Test
	public void createNewStudentWithTwoCourses() {
		
		  Course course1= entityManager.find(Course.class, 1); 
		  Course course2= entityManager.find(Course.class, 3);
		  System.out.println(course1);
		  Student student= new Student("Kia","1990-12-02","male", "09251275847","Bachelor of Information Technology"); 
		  		student.addCourse(course1);
		  		student.addCourse(course2);
		  System.out.println(student.toString());
		  studentRepository.save(student);
		 
	}
	
	
	public void testExistingStudent() {
		Student student = studentRepository.findById(1).get();
		Course  course2=entityManager.find(Course.class, 2);
		student.addCourse(course2);
		
	}
	
	
	public void testRemoveCourseFromExistingStudent() {
		Student student = studentRepository.findById(1).get();
		Course course=new Course(2);
		student.removeCourse(course);
	}
	

	public void testCreateNewStudentWithNewCourse() {
		Course course =new Course("React Native");
		Student student=new Student("John","1990-02-21","male","01236549","Diploma in IT");
		student.addCourse(course);
		studentRepository.save(student);
	}
	
	
	public void testGetStudent() {
		Student student=studentRepository.findById(1).get();
		System.out.println(student.getName());
		System.out.println(student.getCourses());
	}
	
	
	public void testRemoveStudent() {
		studentRepository.deleteById(6);
	}
	
}
