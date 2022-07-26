package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;


import com.project.model.Course;
import com.project.model.Student;
import com.project.model.User;
import com.project.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StudentRepository repo;

	@Mock
	MockHttpSession sessionMock;
	
	
	@Test
	public void testSessionIsNull() throws Exception {
		
	    Mockito.when(sessionMock.getAttribute("login_user")).thenReturn("");
	    this.mockMvc.perform(get("/SearchStudentPage")) .andExpect(status().isOk())
		  .andExpect(view().name("LGN001"));
	   
		
	}
	
	
	@Test
	public void testSearchStudentPageSessionIsNotNull() throws Exception {
		 
		Map<String, Object> sessionObj=new HashMap<String, Object>();
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		sessionObj.put("login_user", user);
		 this.mockMvc.perform(get("/SearchStudentPage").sessionAttrs(sessionObj))
		 .andExpect(status().isOk())
		 .andExpect(view().name("STU003")) 
		 .andExpect( model().attributeExists("studentList"));
			 
		 
	}
	
	
	@Test
	public void testSearchStudentFieldsNull() throws Exception {
		this.mockMvc.perform(post("/SearchStudent").param("id", "").param("name", "").param("course", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("STU003"))
		.andExpect(model().attributeExists("studentList"));
	}
	
	@Test
	public void testSearchStudentFieldsNotNull() throws Exception {
		String id="1";
		String name="saw mon";
		String course="C#";
		this.mockMvc.perform(post("/SearchStudent").param("id", id).param("name", name).param("course", course))
		.andExpect(status().isOk())
		.andExpect(view().name("STU003"));	
	}
	
	
	@Test
	public void testStudentIdNotNull() {
		
		Throwable exception =assertThrows(NumberFormatException.class, () -> Integer.parseInt("s"));
		assertEquals("For input string: \"s\"", exception.getMessage());
		}
	
	
	
	@Test
	public void testAddStudentPage() throws Exception {
		 Mockito.when(sessionMock.getAttribute("login_user")).thenReturn("");
		    this.mockMvc.perform(get("/addStuPage")) .andExpect(status().isOk())
			  .andExpect(view().name("LGN001"));
		    
		   
			User user=new User("saw mon","sawmon@gmail.com","1111","admin");
			 this.mockMvc.perform(get("/addStuPage").sessionAttr("login_user", user))
			 .andExpect(status().isOk())
			 .andExpect(view().name("STU001")) 
			 .andExpect(model().attributeExists("courseList"))
			 .andExpect( model().attributeExists("student"));
	}
	
	@Test
	public void testAddStudentPageAgain() throws Exception {
		 Mockito.when(sessionMock.getAttribute("login_user")).thenReturn("");
		    this.mockMvc.perform(get("/addStuPageagain")) .andExpect(status().isOk())
			  .andExpect(view().name("LGN001"));
		    
		    Map<String, Object> sessionObj=new HashMap<String, Object>();
			User user=new User("saw mon","sawmon@gmail.com","1111","admin");
			sessionObj.put("login_user", user);
			 this.mockMvc.perform(get("/addStuPageagain").sessionAttrs(sessionObj))
			 .andExpect(status().isOk())
			 .andExpect(view().name("STU001"));
	}
	
	
	@Test
	public void testAddStudentHasNoError() throws Exception
	{ 
		
		
		Student student=new Student(1,"su su","1990-02-03","female", "098746636","Bachelor of Computer Science");
		Course course1=new Course("C#");
		student.addCourse(course1);
		
		  this.mockMvc.perform(post("/AddStudent").flashAttr("student", student))
		  .andExpect(status().is(302)) 
		  .andExpect(redirectedUrl("/addStuPageagain"));
		
	}
	@Test
	public void testAddStudentHasError() throws Exception
	{ 
	
		  this.mockMvc.perform(post("/AddStudent"))
		  .andExpect(status().isOk())
		  .andExpect(view().name("STU001"));
	}
	@Test
	public void testAddStudentGetMethod() throws Exception
	{ 
	
		  this.mockMvc.perform(get("/AddStudent"))
		  .andExpect(status().is(302)) 
		  .andExpect(redirectedUrl("/addStuPage"));
	}
	
	
    @Test
    public void testStudentData() throws Exception {
    	Student student=new Student(1,"su su","1990-02-03","female", "098746636","Bachelor of Computer Science");
    	Course course1=new Course("C#");
		student.addCourse(course1);
    	Optional<Student> optStudent=Optional.of(student);
    	Integer id=1;
    	when(repo.findById(id)).thenReturn(optStudent);
    	this.mockMvc.perform(get("/StudentData").param("id", "1").flashAttr("student", optStudent))
		  .andExpect(status().isOk()) 
		  .andExpect(view().name("STU002"))
		  .andExpect( model().attributeExists("student"));
	}
   
	
	
	@Test
	public void testUpdateStudentHasNoError() throws Exception {
		Student student=new Student(1,"su su","1990-02-03","female", "098746636","Bachelor of Computer Science");
    	Course course1=new Course("C#");
		student.addCourse(course1);
		 this.mockMvc.perform(post("/UpdateStudent").flashAttr("student", student))
		  .andExpect(status().is(302)) 
		  .andExpect(redirectedUrl("/SearchStudentPage"));
	}
	
	@Test
	public void testUpdateStudentHasError() throws Exception {
		 this.mockMvc.perform(post("/UpdateStudent"))
		  .andExpect(status().isOk()) 
		  .andExpect(view().name("STU002"));
	}
	
	@Test
	public void testDeleteStudent() throws Exception {
		 this.mockMvc.perform(get("/DeleteStudent").param("id", "1"))
		 .andExpect(status().is(302)) 
		  .andExpect(redirectedUrl("/SearchStudentPage"));
		
	}
	
	@Test
	public void testReset() throws Exception {
		 this.mockMvc.perform(get("/ResetStudent"))
		 .andExpect(status().isOk()) 
		  .andExpect(view().name("STU003"));
		
	}
}	   

	
	
	
	
	
	


