package com.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.controller.CheckCourse;
import com.project.model.Course;
import com.project.repository.CourseRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CourseRepository repo;
    @MockBean
    CheckCourse checkCourse;
	
	@Test
	public void testAddCoursePage() throws Exception {
		Course course=new Course();
		this.mockMvc.perform(get("/AddCoursePage").flashAttr("course", course))
		 .andExpect(status().isOk())
		 .andExpect(view().name("BUD003")) ;
	}
	
	@Test
	public void testAddCourseGetSuccess() throws Exception {
		Course course=new Course();
		this.mockMvc.perform(get("/AddCourseSuccess").flashAttr("course", course))
		 .andExpect(status().isOk())
		 .andExpect(view().name("BUD003")) ;
	}
	
	@Test
	public void testAddCourseFail() throws Exception {
		
		this.mockMvc.perform(post("/AddCourse"))
		 .andExpect(status().isOk())
		 .andExpect(view().name("BUD003")) ;
	}
	
	
	@Test
	public void testAddCourseCheckFail() throws Exception {
		Course course=new Course("C");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(course);
		 Mockito.when(repo.findAll()).thenReturn(courseList);
		this.mockMvc.perform(post("/AddCourse").flashAttr("course", course))
		 .andExpect(status().isOk())
		 .andExpect(view().name("BUD003")) ;
	}
	
	
	@Test
	public void testAddCourseCheckSuccess() throws Exception {
		Course course=new Course("C");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(course);
		Course course2=new Course("A");
		 Mockito.when(repo.findAll()).thenReturn(courseList);
		this.mockMvc.perform(post("/AddCourse").flashAttr("course",course2))
		 .andExpect(status().is(302))
		 .andExpect(redirectedUrl("/AddCourseSuccess")) ;
	}
	
	@Test
	public void testAddCourseSuccess() throws Exception {
		Course course=new Course("C");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(course);
		this.mockMvc.perform(post("/AddCourse").flashAttr("course", course))
		 .andExpect(status().isOk())
		 .andExpect(view().name("BUD003")) ;
	}
	
}
