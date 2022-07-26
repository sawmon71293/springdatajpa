package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(
			value="SELECT * FROM user "
					+ "WHERE user.email = :email and user.password= :password",
			nativeQuery= true
			)
	User getUserByEmailAndPassword(@Param("email") String email,@Param("password")String password);
	
	List<User> findDistinctByNameContaining(String name);
	List<User> findDistinctById(Integer id);
    
}
