package com.jakarta.jpa.sample.config;

import com.jakarta.jpa.sample.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class Repository {

	private static final Logger log = LogManager.getLogger(Repository.class);
	private static EntityManagerFactory entityManagerFactory;
	
	public Repository() {
		log.info("Invocando Contructor");
		setUp();
	}


	private static void setUp() {
		log.info("Invocando setUp");

		// like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
		// 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
		entityManagerFactory = Persistence.createEntityManagerFactory( "com.jakarta.jpa.sample" );


	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("SessionFactory will be destroyed.");
		if ( entityManagerFactory != null ) {
			entityManagerFactory.close();
		}
	}
	
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	//Criteria API
	public static List<Student> all(){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<Student> cq = cb.createQuery(Student.class);
	    Root<Student> rootEntry = cq.from(Student.class);
	    CriteriaQuery<Student> all = cq.select(rootEntry);

	    TypedQuery<Student> allQuery = getEntityManager().createQuery(all);
	    return allQuery.getResultList();
	}

}
