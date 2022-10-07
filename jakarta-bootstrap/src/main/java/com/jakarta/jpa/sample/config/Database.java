package com.jakarta.jpa.sample.config;

import com.jakarta.jpa.sample.entities.Student;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

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

			EntityManager entityManager = Repository.getEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(student1);
			entityManager.persist(student2);
			entityManager.persist(student3);
			entityManager.persist(student4);
			entityManager.persist(student5);

			entityManager.getTransaction().commit();
			entityManager.close();

			log.info("Fin llenado de base de datos");

			try {
				entityManager = Repository.getEntityManager();
				entityManager.getTransaction().begin();
				List<Student> result = entityManager.createQuery( "from Student", Student.class ).getResultList();
				for ( Student student : (List<Student>) result ) {
					System.out.println( "-------------------------------------" );
					System.out.println( "Student id:"+student.getId() );
					System.out.println( "Student name:"+student.getName() );
					System.out.println( "Student lastName:"+student.getLastName() );
					System.out.println( "Student age:"+student.getAge() );
				}
				entityManager.getTransaction().commit();
				entityManager.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

}
