package com.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.project.model.Student;
import com.project.repository.StudentRepository;
import com.project.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
@RestController
@RequestMapping()
public class SpringbootThymeleafSpringDataJpaApplication {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ReportService service;

	@GetMapping("/report/{format}")
	public  void generateReport(@PathVariable String format,HttpServletResponse response) throws JRException, IOException{
            service.exportReport(format,response);
	}

	@GetMapping("/getStudentData")
	public List<Student> getStudentData(){
		return studentRepository.findAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootThymeleafSpringDataJpaApplication.class, args);
	}

}
