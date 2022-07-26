package com.project;






import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import com.project.model.Course;
import com.project.model.Student;
import com.project.repository.StudentRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	void itShouldCheckIfStudentExistsName() {
		//given
		String name="Ei";
		Student student=new Student(name,"1990-04-04","female","092545846844","Bachelor of Computer Science");
		 Course course1= entityManager.find(Course.class, 1); 
		 student.addCourse(course1);
		studentRepository.save(student);
		
		//when
	        boolean  exists=studentRepository.findByName(name);
	        
	        //then
	        assertTrue(exists);
             
	}
	@Test
	void itShouldCheckIfStudentNotExistsName() {
		//given
		String name="Ei";
		
		
		//when
	        boolean  exists=studentRepository.findByName(name);
	        
	        //then
	        assertFalse(exists);
             
	}
	
	
	@Test
	public  void testCreateCourse() {

		//Given
		  Course course1=new Course("C"); 
		  Course course2=new Course("HTML"); 
		  Course course3=new Course("Javascript"); 
		  
		  //When
		  Course savedCourse1=entityManager.persist(course1);
		  Course savedCourse2=entityManager.persist(course2); 
		  Course savedCourse3=entityManager.persist(course3);
		  
		  //Then
		  assertNotNull(savedCourse1);
		  assertNotNull(savedCourse2);
		  assertNotNull(savedCourse3);
		
	}
	
	@Test
	public void createNewStudent() {
		
		
		//Given
		  Course course1= entityManager.find(Course.class, 1); 
		  System.out.println(course1);
		  Student student= new Student("saw mon","1993-05-02","female", "09251275847","Bachelor of Computer Science"); 
		  		student.addCourse(course1);
		  System.out.println(student.toString());
		  
		  //when
		  Student savedStudent=studentRepository.save(student);
		  
		 //then
		  assertNotNull(savedStudent);
	}
	
	
	@Test
	public void createNewStudentWithTwoCourses() {
			//Given
			Course course1= entityManager.find(Course.class, 1); 
			Course course2= entityManager.find(Course.class, 3);
			System.out.println(course1);
			List<Course> courseList=new ArrayList<Course>();
			courseList.add(course1);
			courseList.add(course2);
			Student student= new Student("Kia","1990-12-02","male", "09251275847","Bachelor of Information Technology");
			Student student1= new Student(1,"Kia","1990-12-02","male", "09251275847","Bachelor of Information Technology",courseList);
		  	student.addCourse(course1);
		  	student.addCourse(course2);
		  	System.out.println(student.toString());
		  	Student student3= new Student();
		  	student3.setCourses(courseList);
		  	student3.setId(3);
		  	student3.setName("Su");
            student3.setDob("1993-12-02");	
            student3.setGender("female");
            student3.setEducation("IT Certificate");
            student3.setPhone("11111111");
            Student student4= new Student("Kia","1990-12-02","male", "09251275847","Bachelor of Information Technology",courseList);
            
            
		  	//When
		  	Student savedStudent= studentRepository.save(student);
		    Student savedStudent1=studentRepository.save(student1);
		    Student savedStudent2=studentRepository.save(student3);
		    Student savedStudent3=entityManager.persist(student4);
		  	//Then
		  	assertNotNull(savedStudent);
		  	assertNotNull(savedStudent1);
		  	assertNotNull(savedStudent2);
			assertNotNull(savedStudent3);
		  	System.out.println(savedStudent.getName());
			System.out.println(savedStudent.getDob());
			System.out.println(savedStudent.getGender());
			System.out.println(savedStudent.getPhone());
			System.out.println(savedStudent.getEducation());
			System.out.println(savedStudent.getId());
			System.out.println(savedStudent.getCourses());
	}
	
	@Test
	public void testAddCourseToExistingStudent() {
		
			//Given
			Student student = studentRepository.findById(1).get();
			Course  course2=entityManager.find(Course.class, 2);
			student.addCourse(course2);
		
			//When
			Student savedStudent= studentRepository.save(student);
			
			//Then
			assertNotNull(savedStudent);
		
	}
	
	@Test
	public void testRemoveCourseFromExistingStudent() {
		//Given 
		Student student = studentRepository.findById(1).get();
		Course  course2=entityManager.find(Course.class, 2);
		student.addCourse(course2);
		
	    //when
		boolean removed=student.removeCourse(course2);
		
		//then
		System.out.println(removed);
		assertTrue(removed);
		
		
		
	}
	
	@Test
	public void testCreateNewStudentWithNewCourse() {
		//Given
		Course course =new Course("Go");
		Student student=new Student("John","1990-02-21","male","01236549","Diploma in IT");
		student.addCourse(course);
		
		//when
		Student savedStudent=studentRepository.save(student);
		
		//then
		assertNotNull(savedStudent);
	}
	
	@Test
	public void testGetStudent() {
		Student student=studentRepository.findById(1).get();
		System.out.println(student.getName());
		System.out.println(student.getCourses());
		
		//then
		assertNotNull(student);
	}
	
	@Test
	public void testRemoveStudent() {
		studentRepository.deleteById(10);
	}
	
}
