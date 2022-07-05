package com.project.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.project.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	
  List<Student> findDistinctByNameContainingOrCourses_NameContaining(String name,String courseName);
  Student findDistinctById(Integer id);


}
