package com.project.model;





import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.validation.constraints.NotEmpty;



@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String dob;
	@NotEmpty
	private String gender;
	@NotEmpty
	private String phone;
	@NotEmpty
	private String education;
	
	
	public Student(@NotEmpty String name, @NotEmpty String dob, @NotEmpty String gender, @NotEmpty String phone,
			@NotEmpty String education) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
	}
	
	public Student(Integer id, @NotEmpty String name, @NotEmpty String dob, @NotEmpty String gender,
			@NotEmpty String phone, @NotEmpty String education) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
	}

	@NotEmpty
	@ManyToMany(cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(
			 name="student_course",
			 joinColumns = @JoinColumn(name="student_id"),
			 inverseJoinColumns = @JoinColumn(name="course_id")
			)
	private List<Course> courses=new ArrayList<Course>();
	
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", phone=" + phone
				+ ", education=" + education + ", courses=" + courses + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}


	public Student(Integer id, @NotEmpty String name, @NotEmpty String dob, @NotEmpty String gender,
			@NotEmpty String phone, @NotEmpty String education,List<Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.courses = courses;
	}
	
	

	public Student(@NotEmpty String name, @NotEmpty String dob, @NotEmpty String gender, @NotEmpty String phone,
			@NotEmpty String education, @NotEmpty List<Course> courses) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.courses = courses;
	}


	public Student() {
		
	}
	
	public void addCourse(Course course)
	{
		this.courses.add(course);
	}
	
	public boolean removeCourse( Course course) {
		boolean removed= this.courses.remove(course);
		return removed;
	}

	
	

	
	

}
