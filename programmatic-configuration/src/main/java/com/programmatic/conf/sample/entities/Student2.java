package com.programmatic.conf.sample.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student2 {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "AGE")
	private Integer age;
	
	public Student2() {}
	
	public Student2(/*Long id, */String name, String lastName, Integer age) {
		//this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}

	
}
