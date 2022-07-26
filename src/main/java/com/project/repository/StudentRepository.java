package com.project.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	
  List<Student> findDistinctByNameContainingOrCourses_NameContaining(String name,String courseName);
  List<Student> findDistinctById(Integer id);
  
  @Query( "SELECT CASE WHEN COUNT(s) > 0 THEN " +
           "TRUE ELSE FALSE END " +
		  "FROM Student s "+
           "WHERE s.name = ?1"
		  )
  boolean findByName(String name);


}
