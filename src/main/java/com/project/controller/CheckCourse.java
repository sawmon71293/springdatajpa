package com.project.controller;


import java.util.List;


import com.project.model.Course;


public class CheckCourse {

	public boolean checkCourse(List<Course> courseList, String courseName) {

		for (Course c : courseList) {
			if (c.getName().equals(courseName)) {

				return true;
			}
		}
		return false;

	}

}
