package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.Address;
import org.cyk.utility.__kernel__.__entities__.Department;
import org.cyk.utility.__kernel__.__entities__.DepartmentAddress;
import org.cyk.utility.__kernel__.__entities__.DepartmentEmployee;
import org.cyk.utility.__kernel__.__entities__.Employee;
import org.cyk.utility.__kernel__.__entities__.EmployeeDto;
import org.cyk.utility.__kernel__.__entities__.Mark;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.mapping.MappingSourceBuilder;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.persistence.EntitySaver;
import org.cyk.utility.__kernel__.persistence.EntitySaver.Arguments;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;

public class PersistenceQueriesUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence_queries");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void create_employee(){
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1"));
		Employee employee = EntityFinder.getInstance().find(Employee.class, "1");
		assertThat(employee).isNotNull();
		assertThrows(LazyInitializationException.class, () -> {employee.getMarks().toString();});
	}
	
	@Test
	public void create_employee_marks(){	
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1").addMarks(new Mark().setIdentifier("1").setValue(0)));
		Employee employee = EntityFinder.getInstance().find(Employee.class, "1");
		assertThat(employee).isNotNull();
		assertThrows(LazyInitializationException.class, () -> {employee.getMarks().toString();});
		Mark mark = EntityFinder.getInstance().find(Mark.class, "1"); 
		assertThat(mark).isNotNull();
		assertThrows(LazyInitializationException.class, () -> {mark.getEmployee().toString();});
	}
	
	@Test
	public void create_employee_marks_fetch_marks(){	
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1").addMarks(new Mark().setIdentifier("1").setValue(0)));
		Map<String,EntityGraphArguments.Attributes> map = new HashMap<>();
		map.put("marks", null);
		Employee employee = EntityFinder.getInstance().find(Employee.class, new QueryExecutorArguments().addSystemIdentifiers("1")
				.setProperties(new PropertiesArguments().setEntityGraph(new EntityGraphArguments().setType(EntityGraphArguments.Type.FETCH)
						.setAttributes(new EntityGraphArguments.Attributes().addNamesMap(map)))));
		assertThat(employee).isNotNull();
		assertThat(employee.getMarks()).hasSize(1);
		assertThat(employee.getMarks().stream().map(Mark::getSystemIdentifier).collect(Collectors.toList())).containsExactly("1");
		
		map = new HashMap<>();
		map.put("employee", null);
		Mark mark = EntityFinder.getInstance().find(Mark.class,new QueryExecutorArguments().addSystemIdentifiers("1")
				.setProperties(new PropertiesArguments().setEntityGraph(new EntityGraphArguments().setType(EntityGraphArguments.Type.FETCH)
						.setAttributes(new EntityGraphArguments.Attributes().addNamesMap(map))))); 
		assertThat(mark).isNotNull();
		assertThat(mark.getEmployee()).isNotNull();
	}
	
	@Test
	public void create_employee_departments(){	
		EntityCreator.getInstance().createManyInTransaction(new Address().setIdentifier("1").setCode("1").setName("1"),new Address().setIdentifier("2").setCode("2").setName("2")
				,new Address().setIdentifier("3").setCode("3").setName("3"));
		
		EntityCreator.getInstance().createManyInTransaction(new Department().setIdentifier("1").setCode("1").setName("1")
				.addDepartmentAddresses(new DepartmentAddress().setAddressRefenceFromIdentifier("1"))
				,new Department().setIdentifier("2").setCode("2").setName("2"),new Department().setIdentifier("3").setCode("3").setName("3")
				);
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1")
				.addDepartmentEmployees(new DepartmentEmployee().setIdentifier("1").setDepartmentRefenceFromIdentifier("1"))
				);
		Employee employee = EntityFinder.getInstance().find(Employee.class, "1");
		assertThat(employee).isNotNull();
		assertThrows(LazyInitializationException.class, () -> {employee.getMarks().toString();});
		assertThrows(LazyInitializationException.class, () -> {employee.getDepartmentEmployees().toString();});
		
		QueryResultProcessor.getInstance().process(Employee.class, List.of(employee));
		EmployeeDto employeeDto = MappingSourceBuilder.getInstance().build(employee, EmployeeDto.class, null);
		assertThat(employeeDto.getDepartmentEmployees()).isNull();
		
		Map<String,EntityGraphArguments.Attributes> map = new HashMap<>();
		map.put("departmentEmployees", null);
		Employee employee01 = EntityFinder.getInstance().find(Employee.class,new QueryExecutorArguments().addSystemIdentifiers("1")
				.setProperties(new PropertiesArguments().setEntityGraph(new EntityGraphArguments().setType(EntityGraphArguments.Type.FETCH)
						.setAttributes(new EntityGraphArguments.Attributes().addNamesMap(map)))));
		assertThat(employee01).isNotNull();
		assertThrows(LazyInitializationException.class, () -> {employee01.getMarks().toString();});
		assertThat(employee01.getDepartmentEmployees().stream().map(x -> x.getDepatrment().getIdentifier()).collect(Collectors.toList())).containsExactly("1");
		
		Employee employee02 = EntityFinder.getInstance().find(Employee.class, new QueryExecutorArguments().addSystemIdentifiers("1").setIsResultProcessable(Boolean.TRUE));
		assertThat(employee02).isNotNull();
		assertThat(employee02.getMarks()).isNull();
		assertThat(employee02.getDepartmentEmployees()).isNull();
		
		map = new HashMap<>();
		map.put("departmentEmployees", null);
		Employee employee03 = EntityFinder.getInstance().find(Employee.class,new QueryExecutorArguments().addSystemIdentifiers("1").setIsResultProcessable(Boolean.TRUE)
				.setProperties(new PropertiesArguments().setEntityGraph(new EntityGraphArguments().setType(EntityGraphArguments.Type.FETCH)
						.setAttributes(new EntityGraphArguments.Attributes().addNamesMap(map)))));
		assertThat(employee03).isNotNull();
		assertThat(employee03.getMarks()).isNull();
		assertThat(employee03.getDepartmentEmployees().stream().map(x -> x.getDepatrment().getIdentifier()).collect(Collectors.toList())).containsExactly("1");		
		Collection<Department> departments = employee03.getDepartmentEmployees().stream().map(DepartmentEmployee::getDepatrment).collect(Collectors.toList());
		assertThrows(LazyInitializationException.class, () -> {departments.iterator().next().getDepartmentAddresses().toString();});
		
		//employeeDto = MappingSourceBuilder.getInstance().build(employee03, EmployeeDto.class, null);
		//assertThat(employeeDto.getDepartmentEmployees()).isNotNull();
	}
	
	/* Save */
	
	@Test
	public void save_employees(){
		assertThat(EntityReader.getInstance().readMany(Employee.class)).isNull();
		EntitySaver.getInstance().save(Employee.class, new Arguments<Employee>());
		assertThat(EntityReader.getInstance().readMany(Employee.class)).isNull();
		EntityCreator.getInstance().createOneInTransaction(Employee.instantiateOneRandomlyByIdentifier("1"));
		assertThat(EntityReader.getInstance().readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1");
		EntityCreator.getInstance().createOneInTransaction(Employee.instantiateOneRandomlyByIdentifier("2"));
		assertThat(EntityReader.getInstance().readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2");
		EntityCreator.getInstance().createManyInTransaction((Collection<Object>)CollectionHelper.cast(Object.class, Employee.instantiateManyRandomlyByIdentifiers("3","4","5")));
		assertThat(EntityReader.getInstance().readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2"
				,"3","4","5");
		EntitySaver.getInstance().save(Employee.class, new Arguments<Employee>()
				.setDeletables(List.of(EntityFinder.getInstance().find(Employee.class, "2")))
				.setCreatables(List.of(Employee.instantiateOneRandomlyByIdentifier("a").setName("myname")))
				.setIsTransactional(Boolean.TRUE));
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("myname");
		assertThat(EntityReader.getInstance().readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1"
				,"3","4","5","a");
		EntitySaver.getInstance().save(Employee.class, new Arguments<Employee>()
				.setCreatables(List.of(Employee.instantiateOneRandomlyByIdentifier("a1"),Employee.instantiateOneRandomlyByIdentifier("a2")))
				.setUpdatables(List.of(Employee.instantiateOneRandomlyByIdentifier("a").setName("n01")))
				.setDeletables(List.of(EntityFinder.getInstance().find(Employee.class, "1")))
				.setIsTransactional(Boolean.TRUE));
		assertThat(EntityReader.getInstance().readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder(
				"3","4","5","a","a1","a2");
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("n01");
	}
	
	//@Test
	public void create_parent_child(){	
		EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier("1").setCode("1").setName("1").addMarks(new Mark().setIdentifier("1").setValue(0)));
	}
	
	//@Test
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