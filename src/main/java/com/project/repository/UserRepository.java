package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(
			value="SELECT * FROM user "
					+ "WHERE user.email = :email and user.password= :password",
			nativeQuery= true
			)
	User getUserByEmailAndPassword(@Param("email") String email,@Param("password")String password);

}
