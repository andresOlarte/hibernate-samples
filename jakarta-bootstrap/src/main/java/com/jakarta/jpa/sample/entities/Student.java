package com.jakarta.jpa.sample.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "AGE")
	private Integer age;
	
	public Student() {}
	
	public Student(/*Long id, */String name, String lastName, Integer age) {
		//this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}

	
}
