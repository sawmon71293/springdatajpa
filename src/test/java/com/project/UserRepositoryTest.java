package com.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.project.model.User;
import com.project.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void itShouldCheckgetUserByEmailAndPassword() {
		
		//Given
		User user= new User(1,"Su","su@gmail.com","1111","admin");
		repo.save(user);
		
		
		//When
		User foundUser=repo.getUserByEmailAndPassword("su@gmail.com", "1111");
		
		//Then
		assertNotNull(foundUser);
	}
	
	
	@Test
	public void itShouldCheckSavedUser() {
		
		//Given
		User user= new User("Su","su@gmail.com","1111","admin");
		User user1=new User(1);
		user1.setEmail("may@gmail.com");
		user1.setName("May");
		user1.setPassword("1111");
		user1.setRole("user");
		
		
		//when
		User savedUser=entityManager.persist(user);
		User savedUser1=repo.save(user1);
		
		
		
		//Then
		assertNotNull(savedUser);
		assertNotNull(savedUser1);
	}
	
	
	@Test
	public void itShouldCheckFindDistinctByNameContaining() {
		//Given
		User user= new User("Su","su@gmail.com","1111","admin");
		user.setId(2);
		User savedUser=repo.save(user);
		
		//when
		List<User> userList=repo.findDistinctByNameContaining("s");
		
		//Then
		assertNotNull(userList);
		System.out.println(savedUser.getEmail());
		System.out.println(savedUser.getId());
		System.out.println(savedUser.getName());
		System.out.println(savedUser.getPassword());
		System.out.println(savedUser.getRole());
		
		
		
	}

}
