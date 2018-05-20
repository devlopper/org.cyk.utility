package org.cyk.utility.common.persistence.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class JpaUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createEntityManagerFactoryWithoutProperties(){
		TestCase testCase = new TestCase();
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unitTest");
		testCase.assertNotNull(entityManagerFactory);
		
	}
	
	@Test
	public void createEntityManagerFactoryWithProperties(){
		TestCase testCase = new TestCase();
		
		Map<String, String> properties = new HashMap<String, String>();
	    properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
	    properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost/db");
	    properties.put("javax.persistence.jdbc.user", "root");
	    properties.put("javax.persistence.jdbc.password", "root");
	    
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unitTest",properties);
		testCase.assertNotNull(entityManagerFactory);
		
	}
	
	@Test
	public void executeSelectQuery(){
		TestCase testCase = new TestCase();
		Map<String, String> properties = new HashMap<String, String>();
	    properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:hsql");
	    
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unitTest",properties);
		
		testCase.assertNotNull(entityManagerFactory);
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.createQuery("SELECT p FROM EntityWithoutExtendsAndPropertyAnnotated p");
		entityManager.createQuery("SELECT p FROM EntityWithoutExtendsAndMethodAnnotated p");
		entityManager.createQuery("SELECT p FROM EntityWithExtendsAndMethodAnnotated p");
	}
}
