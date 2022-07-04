package com.project.controller;

import java.util.ArrayList;
import java.util.List;

import com.project.model.User;



public class UserValidation {

	public boolean userValidation(List<User> userList, String email) {

		for (User dto : userList) {
			if (dto.getEmail().equals(email)) {
				return true;
			}

		}
		return false;

	}
	
	

}
