package com.hibernate.sample.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Student {

	private Long id;
	private String name;
	private String lastName;
	private Integer age;
	
	public Student() {}
	
	public Student(/*Long id, */String name, String lastName, Integer age) {
		//this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}

	
}
