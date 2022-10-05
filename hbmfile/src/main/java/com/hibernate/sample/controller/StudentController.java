package com.hibernate.sample.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibernate.sample.config.Repository;
import com.hibernate.sample.entities.Student;

@RestController
public class StudentController {

	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return ResponseEntity.ok("Hay conexion con el servidor mediante Spring Boot");
	}
	
	@GetMapping("/students")
	public List<Student> all(){
		return Repository.all();
	}
	
}
