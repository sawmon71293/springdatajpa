package com.project.controller;

import java.sql.SQLException;







import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.project.model.Course;
import com.project.model.Student;
import com.project.repository.CourseRepository;
import com.project.repository.StudentRepository;


@Controller
public class StudentController {
	@Autowired
	CourseRepository courseDAO;
	@Autowired
	StudentRepository studentDAO;
	
	@RequestMapping(value = "/SearchStudentPage", method = RequestMethod.GET)
	public String searchStudentPage(ModelMap model, HttpSession ses) throws SQLException {
		if(ses.getAttribute("login_user")==null) { 
			  return "LGN001";
		  
		  }
		
		List<Student> studentList =  studentDAO.findAll();
	  	model.addAttribute("studentList", studentList);
	  	
		
		return "STU003";
	}
	
	@RequestMapping(value = "/SearchStudent", method = RequestMethod.POST)
	public String searchStudent(ModelMap model, HttpSession ses, @RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("course") String course) throws SQLException {
		System.out.println("the id value is " + id);
		if(ses.getAttribute("login_user")==null) { 
			  return "LGN001";
		  
		  }
			
			  System.out.println(id + name + course);
			  if(id.equals("") && name.equals("") && course.equals("")) {
				 List<Student> studentList =studentDAO.findAll(); 
				  model.addAttribute("studentList", studentList); 
				  return "STU003";
			  }
			 
			  
			   
	      else { 
	    	  List<Student> studentList=null;
	    	  int temp=0;
	    	  if(!id.equals(""))
	    	  {        try {
	    		  temp=Integer.parseInt(id);
	    	  }catch(NumberFormatException ex) {
	    		  String warning="Enter a number for Student Id";
	    		  model.addAttribute("warning",warning);
	    		  return "STU003";
	    	  }
	    		     Integer searchId=  (Integer)temp;
	    		Student student=studentDAO.findDistinctById(searchId);
	    		if(student!=null) {
	    		model.addAttribute("studentList",student);
	    		}
	    		else {
	    			model.addAttribute("studentList",studentList);
	    		}
	    		return "STU003";
	    	  }
	    	  else if(id.equals("")|| name.equals("")||course.equals("")) {
	    		  
	    		  String searchName= name.isBlank() ? "~" :name;
	    		  String searchCourse=course.isBlank() ? "~" :course;
	    		  studentList=studentDAO.findDistinctByNameContainingOrCourses_NameContaining(searchName, searchCourse);
	    		  model.addAttribute("studentList",studentList);
	  			return "STU003";
	    	  }
			return "STU003";
			
	}
		
	}

	@RequestMapping(value = "/addStuPage", method = RequestMethod.GET)
	public ModelAndView studentPage(ModelMap model, HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { 
			  return new ModelAndView("LGN001");
		  
		  }
	
		List<Course> allCourses =  courseDAO.findAll();
		 System.out.println(allCourses);
		model.addAttribute("courseList", allCourses);
		return new ModelAndView("STU001", "student", new Student());
	}

	@RequestMapping(value = "/addStuPageagain", method = RequestMethod.GET)
	public ModelAndView studentPageagain(HttpSession session, ModelMap model) throws SQLException {
		
		  if(session.getAttribute("login_user")==null) { return new
		  ModelAndView("LGN001");
		  
		  }
		 
		List<Course> allCourses =  courseDAO.findAll();
		model.addAttribute("courseList", allCourses);
		model.addAttribute("success", "Registered Successfully!");
		return new ModelAndView("STU001", "student", new Student());
	}

	@RequestMapping(value = "/AddStudent", method = RequestMethod.POST)
	public String addStudent(ModelMap model, HttpSession ses, @ModelAttribute("student")@Validated Student student,BindingResult bs, HttpSession session)
			throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { return "LGN001";
		  
		  }
		 
		System.out.println(student.toString());
		List<Course> allCourses =  courseDAO.findAll();
		model.addAttribute("courseList", allCourses);
		if (bs.hasErrors()) {
			
			return "STU001";

		}

		else {
			studentDAO.save(student);
			return "redirect:/addStuPageagain";
		}

	}

	@RequestMapping(value = "/AddStudent", method = RequestMethod.GET)
	public String addStudent1( HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) {
			  return "LGN001";
		  
		  }
		 
		return "redirect:/addStuPage";

	}

	@RequestMapping(value = "/StudentData")
	public ModelAndView studentData(@RequestParam("id") Integer id, ModelMap model, HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { return new ModelAndView("LGN001");
		  
		  }
		 
	
		Student student = studentDAO.findById(id).get();
		
		List<Course> allCourses =  courseDAO.findAll();
		  model.addAttribute("courseList", allCourses); 
		 
		 
		 return new ModelAndView("STU002","student",student);
	}

	@RequestMapping(value = "/UpdateStudent", method=RequestMethod.POST)
	public String updateStudent(@ModelAttribute("student")@Validated Student student,BindingResult bs, ModelMap model, HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { return "LGN001";
		  
		  }
		 
		
		List<Course> allCourses =  courseDAO.findAll();
		model.addAttribute("courseList", allCourses);
		if(bs.hasErrors()) {
			return "STU002";
		}
		
		studentDAO.save(student);
		return "redirect:/SearchStudentPage";
		
		
		
	}

	@RequestMapping(value = "/DeleteStudent", method = RequestMethod.GET)
	public String deleteStudent(@RequestParam("id") Integer id, ModelMap model, HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) {
			  return "LGN001";
		  
		  }
		 
		studentDAO.deleteById(id);;
		return "redirect:/SearchStudentPage";
	}
	
	
	@RequestMapping(value= "/ResetStudent", method=RequestMethod.GET)
	public String reset() {
		return "STU003";
	}
	

	
}
