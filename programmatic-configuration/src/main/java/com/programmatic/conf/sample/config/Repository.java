package com.programmatic.conf.sample.config;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Component;

import com.programmatic.conf.sample.entities.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.spi.PersistenceUnitInfo;

@Component
public class Repository {

	private static final Logger log = LogManager.getLogger(Repository.class);
	private static EntityManagerFactory entityManagerFactory;
	private static HibernatePersistenceProvider hibernatePersistenceProvider;
	private static PersistenceUnitInfo persistenceUnitInfo;

	public Repository() {
		log.info("Invocando Contructor");
		setUp();
	}

	private static void setUp() {
		log.info("Invocando setUp");

		hibernatePersistenceProvider = new HibernatePersistenceProvider();
		persistenceUnitInfo = new PersistenceUnitInfoImpl(
				"com.programmatic.conf.sample",
				getManagedClassNames(),
				properties());
		// like discussed with regards to SessionFactory, an EntityManagerFactory is set
		// up once for an application
		// IMPORTANT: notice how the name here matches the name we gave the
		// persistence-unit in persistence.xml!
		entityManagerFactory = hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo,
				properties());

	}

	@PreDestroy
	public void destroy() {
		System.out.println("SessionFactory will be destroyed.");
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	// Criteria API
	public static List<Student> all() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> rootEntry = cq.from(Student.class);
		CriteriaQuery<Student> all = cq.select(rootEntry);

		TypedQuery<Student> allQuery = getEntityManager().createQuery(all);
		return allQuery.getResultList();
	}

	protected static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty("jakarta.persistence.jdbc.driver", "org.h2.Driver");
		properties.setProperty("jakarta.persistence.jdbc.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
		properties.setProperty("jakarta.persistence.jdbc.user", "sa");
		properties.setProperty("jakarta.persistence.jdbc.password", "");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "create");

		return properties;
	}

	private static List<String> getManagedClassNames() {
		Reflections reflections = new Reflections("com.programmatic.conf.sample", Scanners.TypesAnnotated);
		List<String> list = reflections.getTypesAnnotatedWith(Entity.class).stream()
				.map(Class::getName).collect(Collectors.toList());

		return list;
	}
}
