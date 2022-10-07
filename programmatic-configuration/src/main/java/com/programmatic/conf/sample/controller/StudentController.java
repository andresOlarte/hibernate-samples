package com.programmatic.conf.sample.controller;

import com.programmatic.conf.sample.config.Repository;
import com.programmatic.conf.sample.entities.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
