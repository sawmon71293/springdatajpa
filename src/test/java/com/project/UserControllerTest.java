package com.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.project.controller.UserValidation;

import com.project.model.User;
import com.project.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	UserRepository repo;
	@MockBean
	UserValidation valid;

	@Mock
	MockHttpSession sessionMock;
	
	
	@Test
	public void itShouldCheckLoginPage() throws Exception {
		this.mockMvc.perform(get("/LGN001"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"))
		;
	}
       
	
	@Test
	public void itShouldCheckLoginUserSuccess() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		sessionMock.setAttribute("login_user", user);
		when(repo.getUserByEmailAndPassword("sawmon@gmail.com", "1111")).thenReturn(user);
		this.mockMvc.perform(post("/login").param("email","sawmon@gmail.com").param("password", "1111").session(sessionMock))
		.andExpect(status().isOk())
		.andExpect(view().name("MNU001"));
		
	}
	@Test
	public void itShouldCheckLoginUserFail() throws Exception {
		
		    
		this.mockMvc.perform(post("/login").param("email","").param("password", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void itShouldCheckLoginUserFail1() throws Exception {
		
		when(repo.getUserByEmailAndPassword("sawmon@gmail.com", "1111")).thenReturn(null);    
		this.mockMvc.perform(post("/login").param("email","sawmon@gmail.com").param("password", "1111"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void itShouldCheckLogOut() throws Exception {
		this.mockMvc.perform(get("/logout"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/LGN001"));
	}
	
	@Test
	public void testShowUser() throws Exception {
		this.mockMvc.perform(get("/ShowUser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	
	@Test
	public void testSearchUserFail() throws Exception {
		this.mockMvc.perform(post("/SearchUser").param("id","").param("name", ""))
		.andExpect(model().attributeExists("userList"));
		
	}
	
	@Test
	public void testSearchUserSuccess() throws Exception {
		this.mockMvc.perform(post("/SearchUser").param("id","1").param("name", "saw"))
		.andExpect(model().attributeExists("userList"));
		
		this.mockMvc.perform(post("/SearchUser").param("id","").param("name", "saw"))
		.andExpect(model().attributeExists("userList"));
		
	}
	
	@Test
	public void testSearchUserThrowException() throws Exception {
		assertThrows(NumberFormatException.class, () -> Integer.parseInt("s"));
    
	}
	
	@Test
	public void testAddUserPage() throws Exception {
		this.mockMvc.perform(get("/AddUserPage"))
		.andExpect(model().attributeExists("user"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test
	public void testAddUserSuccess() throws Exception {
		this.mockMvc.perform(get("/AddUserSuccess"))
		.andExpect(model().attributeExists("user"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test
	public void testAddUserPostHasError() throws Exception {
		this.mockMvc.perform(post("/AddUser").param("confirmpass", "1111"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test
	public void testAddUserPostSuccess() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		this.mockMvc.perform(post("/AddUser").param("confirmpass", "1111").flashAttr("user", user))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/AddUserSuccess"));
	}
	@Test
	public void testAddUserPostPasswordEqual() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		List<User> userList=new ArrayList<User>();
		userList.add(user);
		when(repo.findAll()).thenReturn(userList);
		when(valid.userValidation(userList, "sawmon@gmail.com")).thenReturn(true);
		this.mockMvc.perform(post("/AddUser").param("confirmpass", "1111")
				.flashAttr("user", user).flashAttr("userList",userList))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test
	public void testAddUserPostPasswordNotEqual() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		this.mockMvc.perform(post("/AddUser").param("confirmpass", "")
				.flashAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	
	@Test
	public void testUpdateUserPage() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		
    	Optional<User> optUser=Optional.of(user);
    	Integer id=1;
    	when(repo.findById(id)).thenReturn(optUser);
		
		this.mockMvc.perform(get("/UpdateUserPage").param("id", "1").flashAttr("User", optUser)
				)
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	
	@Test
	public void testUpdateUserFail() throws Exception {
		
		this.mockMvc.perform(post("/UpdateUser").param("confirmpass", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	@Test
	public void testUpdateUserSuccess() throws Exception {
		User user=new User("saw mon","sawmon@gmail.com","1111","admin");
		
		List<User> userList=new ArrayList<User>();
		userList.add(user);
		when(repo.save(user)).thenReturn(user);
		when(repo.findAll()).thenReturn(userList);
		this.mockMvc.perform(post("/UpdateUser").param("confirmpass", "1111").flashAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	
	@Test
	public void testDeleteUserSuccess() throws Exception {
		this.mockMvc.perform(get("/DeleteUser").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/ShowUser"));
		
	}
	
	@Test
	public void testReset() throws Exception {
		this.mockMvc.perform(get("/Reset"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	
		
	}


