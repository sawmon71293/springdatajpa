package com.project.controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

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


import com.project.model.User;
import com.project.repository.UserRepository;



@Controller
public class UserController {
	
	@Autowired
	UserRepository userdao;
	
	@RequestMapping(value = "/LGN001", method = RequestMethod.GET)
	public String loginPage() {
		return "LGN001";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, ModelMap model) throws SQLException {
		
          System.out.println(email + password);
		
		if (email.isBlank() || password.isBlank()) {
			model.addAttribute("error", "Please fill in your login details.");
			return "LGN001";
		} else {
			if (userdao.getUserByEmailAndPassword(email, password) != null) {
				session.setAttribute("login_user", email);
				return "MNU001";
			} else {
				model.addAttribute("error", "Invalid user email or password!");
				return "/LGN001";
			}
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutUser(HttpSession session, ModelMap model) {
		session.invalidate();

		return "redirect:/LGN001";
	}

	@RequestMapping(value = "/ShowUser", method = RequestMethod.GET)
	public String showUser(ModelMap model,HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { return "LGN001";
		  
		  }
		 

		List<User> userList = null;
	          
			userList =  userdao.findAll();
		
		model.addAttribute("userList", userList);

		return "USR003";
	}

	@RequestMapping(value = "/SearchUser", method = RequestMethod.POST)
	public String searchUser(ModelMap model, @RequestParam("id")String id, @RequestParam("name") String name,HttpSession ses)
			throws SQLException {

		
		  if(ses.getAttribute("login_user")==null) { 
			  return "LGN001";
		  
		  }
		  List<User> userList = null;
		 if(id.equals("") && name.equals("")) {
			 userList = userdao.findAll();
			 model.addAttribute("userList", userList);
		 }
		 
		 else if(!id.equals("")) {
			 int temp = Integer.parseInt(id);
		     Integer searchId= (Integer) temp;
		    	 userList=userdao.findDistinctByUserId(searchId);
		    	if(userList != null) {
		    		model.addAttribute("userList", userList);
		    	}
		    	else {
		    		userList=null;
		    		 model.addAttribute("userList", userList);
		    	}
		    		
					
		     
		    
		 }
		 else {
			     userList =userdao.findDistinctByNameContaining(name);
			      model.addAttribute("userList", userList);
			 
		 }
          
			return "USR003";
		}

	

	@RequestMapping(value = "/AddUserPage", method = RequestMethod.GET)
	public ModelAndView addUserPage(ModelMap model,HttpSession ses) {
		
		  if(ses.getAttribute("login_user")==null) { return new ModelAndView("LGN001");
		  
		  }
		 
		return new ModelAndView("USR001", "user", new User());
	}

	@RequestMapping(value = "/AddUserSuccess", method = RequestMethod.GET)
	public ModelAndView addUserSuccess(ModelMap model,HttpSession ses) {
		
		  if(ses.getAttribute("login_user")==null) { return new ModelAndView("LGN001");
		  
		  }
		 
		
		model.addAttribute("message", "Registered Successful!");
		return new ModelAndView("USR001", "user", new User());
	}

	@RequestMapping(value = "/AddUser", method = RequestMethod.POST)
	public String addUser(HttpSession ses,ModelMap model, @ModelAttribute("user")@Validated User user,BindingResult bs,
			@RequestParam("confirmpass") String confirm) {
		
		  if(ses.getAttribute("login_user")==null) { return "LGN001";
		  
		  }
		 
		UserValidation uv = new UserValidation();
		List<User> userList =  userdao.findAll();
		if (bs.hasErrors()) {
			return "USR001";
		} else {
			if (user.getPassword().equals(confirm)) {
					
					if(userList!=null) {
						 if (uv.userValidation(userList, user.getEmail())) {
								model.addAttribute("warning", "User email already exists!");
								return "USR001";
							}
					}
				
				 userdao.save(user);
				 userList =  userdao.findAll();
				model.addAttribute("userList", userList);
  
				return "redirect:/AddUserSuccess";
			} else {
				model.addAttribute("error", "Passwords must be equal!");
				return "USR001";

			}

		}

	}

	@RequestMapping(value = "/UpdateUserPage", method = RequestMethod.GET)
	public ModelAndView updateUserPage(@RequestParam("id") Integer id, ModelMap model,HttpSession ses) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { 
			  
			  return new ModelAndView("LGN001");
		  
		  }
		 
		User user=userdao.findById(id).get();
		return new ModelAndView("USR002", "user",user);
	}

	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
	public String updateUser(HttpSession ses,@ModelAttribute("user") User user, ModelMap model,
			@RequestParam("confirmpass") String confirmpass) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) {
			  return "LGN001";
		  
		  }
		 
		
		List<User> userList = null;
		Integer id = user.getUserId();
		String name = user.getName();
		String password = user.getPassword();
		String role = user.getRole();
		String email = user.getEmail();
	
		
		if (name.isBlank() || password.isBlank() || confirmpass.isBlank() || !confirmpass.equals(password)) {
			model.addAttribute("message", "Fill the blanks!");
			return "USR002";
		} else {

		
				userdao.save(user);
			
			userList =  userdao.findAll();
			model.addAttribute("message", "Successfully Updated!");
			model.addAttribute("userList", userList);
			return "USR003";
		}

	}

	@RequestMapping(value = "/DeleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpSession ses,@RequestParam("id")Integer id, ModelMap model) throws SQLException {
		
		  if(ses.getAttribute("login_user")==null) { return "LGN001";
		  
		  }
		 
			userdao.deleteById(id);
			List<User> userList = null;
			userList =  userdao.findAll();
		model.addAttribute("userList", userList);
		return "redirect:/ShowUser";

	}
	
	@RequestMapping(value= "/Reset", method=RequestMethod.GET)
	public String reset() {
		return "USR003";
	}

}
