package com.hibernate.sample.config;

import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import com.hibernate.sample.entities.Student;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class Repository {

	private static final Logger log = LogManager.getLogger(Repository.class);
	private static SessionFactory sessionFactory;
	
	public Repository() {
		log.info("Invocando Contructor");
		setUp();
	}


	private static void setUp() {
		log.info("Invocando setUp");
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			log.info("SessionFactory construido");
		} catch (Exception e) {
			log.error("Error construyendo SessionFactory: ", e);
			// The registry would be destroyed by the SessionFactory, but we had trouble
			// building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			//sessionFactory = null;
		}
		
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("SessionFactory will be destroyed.");
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
	//Criteria API
	public static List<Student> all(){
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
	    CriteriaQuery<Student> cq = cb.createQuery(Student.class);
	    Root<Student> rootEntry = cq.from(Student.class);
	    CriteriaQuery<Student> all = cq.select(rootEntry);

	    TypedQuery<Student> allQuery = getSession().createQuery(all);
	    return allQuery.getResultList();
	}

}
