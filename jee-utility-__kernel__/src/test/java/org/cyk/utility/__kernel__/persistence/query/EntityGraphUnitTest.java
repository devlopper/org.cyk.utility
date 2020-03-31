package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.Employee;
import org.cyk.utility.__kernel__.__entities__.Mark;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class EntityGraphUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu_entity_graph");
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void persist(){		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1").addMarks(new Mark().setIdentifier("1").setValue(0)));
		
		entityManager = EntityManagerGetter.getInstance().get();
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Employee t",Long.class).getSingleResult()).isEqualTo(1);
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Mark t",Long.class).getSingleResult()).isEqualTo(1);
		
		EntityGraph<Employee> employeeEntityGraph = entityManager.createEntityGraph(Employee.class);
		employeeEntityGraph.addAttributeNodes("marks");
		//System.out.println("EntityManager.find-----------------------------------");
		Employee employee = entityManager.find(Employee.class, "1",Map.of("javax.persistence.fetchgraph",employeeEntityGraph));
		entityManager.close();
		assertThat(employee.getMarks().size()).isEqualTo(1);
		employee.addMarks(new Mark().setIdentifier("2").setValue(0),new Mark().setIdentifier("3").setValue(0));
		
		entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.merge(employee);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = EntityManagerGetter.getInstance().get();
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Employee t",Long.class).getSingleResult()).isEqualTo(1);
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Mark t",Long.class).getSingleResult()).isEqualTo(3);
		
		employeeEntityGraph = entityManager.createEntityGraph(Employee.class);
		employeeEntityGraph.addAttributeNodes("marks");
		employee = entityManager.find(Employee.class, "1",Map.of("javax.persistence.fetchgraph",employeeEntityGraph));
		entityManager.close();
		
		assertThat(employee.getMarks().size()).isEqualTo(3);
		employee.getMarks().clear();
		entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.merge(employee);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = EntityManagerGetter.getInstance().get();
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Employee t",Long.class).getSingleResult()).isEqualTo(1);
		assertThat(entityManager.createQuery("SELECT COUNT(t) FROM Mark t",Long.class).getSingleResult()).isEqualTo(0);
	}
}
