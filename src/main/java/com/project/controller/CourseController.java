package com.project.controller;


import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.project.model.Course;
import com.project.repository.CourseRepository;


@Controller
public class CourseController {
	
	@Autowired
	CourseRepository courseDAO;

	@RequestMapping(value = "/AddCoursePage", method = RequestMethod.GET)
	public ModelAndView addCoursePage(HttpSession ses) {
		
		  if(ses.getAttribute("login_user")==null) { return new ModelAndView("LGN001");
		  
		  }
		 
		return new ModelAndView("BUD003", "course", new Course());
	}

	@RequestMapping(value = "/AddCourseSuccess", method = RequestMethod.GET)
	public ModelAndView addUserSuccess(ModelMap model,HttpSession ses) {
		
		  if(ses.getAttribute("login_user")==null) { return new ModelAndView("LGN001");
		  
		  }
		 
		model.addAttribute("message", "Registered Successful!");
		return new ModelAndView("BUD003", "course", new Course());
	}

	@RequestMapping(value = "/AddCourse", method = RequestMethod.POST)
	public String addCourse(ModelMap model, @ModelAttribute("course") Course course,HttpSession ses) {
		
		  if(ses.getAttribute("login_user")==null) { 
			  return "LGN001";
		  
		  }
		 
		
		List<Course> courseList = courseDAO.findAll();
		String courseName = course.getName();
		System.out.println(courseName);
		CheckCourse cc = new CheckCourse();
			
		

		if (course.getName().equals("")) {
			model.addAttribute("error", "Course can't be empty!");
			model.addAttribute("course", course);
			return "BUD003";
		}

		else {

			if (courseList.size() != 0) {
				

				if (cc.checkCourse(courseList, courseName)) {

					model.addAttribute("repeat", "Your course already exists!");
				} else {
					courseDAO.save(course);
                   return "redirect:/AddCourseSuccess";
				}


			return "BUD003";
		}
			else {
				courseDAO.save(course);
				return "BUD003";
			}
			
		}
	}
}

