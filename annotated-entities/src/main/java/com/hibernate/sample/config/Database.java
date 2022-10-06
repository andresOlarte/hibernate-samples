package com.hibernate.sample.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.hibernate.sample.entities.Student;

@Component
public class Database {
	
	private static final Logger log = LogManager.getLogger(Database.class);
	
	@Bean
	public CommandLineRunner init(){
		return args -> {
			log.info("Iniciando registros en base de datos");
			Student student1 = new Student("Andresito1", "cara pito", 47);
			Student student2 = new Student("Andresito2", "cara pito", 47);
			Student student3 = new Student("Andresito3", "cara pito", 47);
			Student student4 = new Student("Andresito4", "cara pito", 47);
			Student student5 = new Student("Andresito5", "cara pito", 47);
			
			Session session = Repository.getSession();
			
			session.beginTransaction();
			
			session.persist(student1);
			session.persist(student2);
			session.persist(student3);
			session.persist(student4);
			session.persist(student5);
			
			session.getTransaction().commit();
			session.close();
			log.info("Fin llenado de base de datos");

			try {
				session = Repository.getSession();
				session.beginTransaction();
				List<Student> result = session.createQuery( "from Student", Student.class ).list();
				for ( Student student : (List<Student>) result ) {
					System.out.println( "-------------------------------------" );
					System.out.println( "Student id:"+student.getId() );
					System.out.println( "Student name:"+student.getName() );
					System.out.println( "Student lastName:"+student.getLastName() );
					System.out.println( "Student age:"+student.getAge() );
				}
				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

}
