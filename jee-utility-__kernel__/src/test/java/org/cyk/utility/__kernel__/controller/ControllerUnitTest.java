package org.cyk.utility.__kernel__.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.__entities__.Employee;
import org.cyk.utility.__kernel__.__entities__.EmployeeData;
import org.cyk.utility.__kernel__.__entities__.EmployeeDto;
import org.cyk.utility.__kernel__.__entities__.EmployeeSaver;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.EntityFinder;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.junit.jupiter.api.Test;

public class ControllerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClass(MessageRenderer.class, org.cyk.utility.__kernel__.annotation.SystemOut.Class.class);
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("controller_queries");
		RepresentationEntityClassGetterImpl.MAP.put(EmployeeData.class, EmployeeDto.class);
		RepresentationClassGetterImpl.put(EmployeeData.class, RepresentationClassGetter.Function.SAVER, EmployeeSaver.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(Employee.class, "SELECT t FROM Employee t WHERE t.identifier IN :identifiers"));
		QueryHelper.addQueries(Query.build(Employee.class,"read.1", "SELECT new org.cyk.utility.__kernel__.__entities__.Employee(t.code,t.name) FROM Employee t"));
		Arguments.IS_REPRESENTATION_PROXYABLE = Boolean.FALSE;
		org.cyk.utility.__kernel__.persistence.EntitySaver.Arguments.IS_TRANSACTIONAL = Boolean.TRUE;
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.representation.EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.persistence.query.EntityReader.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
	}
	
	@Test
	public void count_employee(){
		assertThat(EntityCounter.getInstance().count(Employee.class)).isEqualTo(0l);
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier(index+"").setCode(index+"").setName(index+""));
		assertThat(EntityCounter.getInstance().count(Employee.class)).isEqualTo(10l);
	}
	
	/* read all*/
	
	@Test
	public void employee_withoutTuple_read_all(){		
		__assertRead__(EntityReader.getInstance().readMany(EmployeeData.class));
	}
	
	@Test
	public void employee_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new Employee("1","1","1"));
		__assertRead__(EntityReader.getInstance().readMany(EmployeeData.class), "1");
	}
	
	@Test
	public void employee_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(EmployeeData.class), "1","2");
	}
	
	@Test
	public void employee_withTuple_projection(){		
		EntityCreator.getInstance().createOneInTransaction(new Employee("1","2","3"));
		Collection<EmployeeData> employees = EntityReader.getInstance().readMany(EmployeeData.class,new Arguments<EmployeeData>()
				.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
						.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.read.1"))));
		assertThat(employees.stream().map(EmployeeData::getIdentifier).collect(Collectors.toList())).containsExactly(new String[] {null});
		assertThat(employees.stream().map(EmployeeData::getCode).collect(Collectors.toList())).containsExactly(new String[] {"2"});
		assertThat(employees.stream().map(EmployeeData::getName).collect(Collectors.toList())).containsExactly(new String[] {"3"});
	}
	
	/* read by system identifier */
	
	@Test
	public void employee_withoutTuple_read_bySystemIdentifiers(){
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(EmployeeData.class,new Arguments<EmployeeData>().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
				.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))))), "1");
	}
	
	@Test
	public void employee_withoutTuple_read_bySystemIdentifier(){
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));		
		__assertReadOne__(EntityReader.getInstance().readOne(EmployeeData.class,new Arguments<EmployeeData>().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
				.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))
						))), "1");
		
		__assertReadOne__(EntityReader.getInstance().readOne(EmployeeData.class,new Arguments<EmployeeData>().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
				.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("2"))))), "2");
	}
	
	/* read by business identifier */
	
	/* read by filter */
	
	/* save */
	
	@Test
	public void save_employees(){
		org.cyk.utility.__kernel__.persistence.query.EntityReader entityReader = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance();
		assertThat(entityReader.readMany(Employee.class)).isNull();
		EmployeeSaver.getInstance().save(null,null,null, new org.cyk.utility.__kernel__.representation.Arguments());
		assertThat(entityReader.readMany(Employee.class)).isNull();
		EntityCreator.getInstance().createOneInTransaction(Employee.instantiateOneRandomlyByIdentifier("1"));
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1");
		EntityCreator.getInstance().createOneInTransaction(Employee.instantiateOneRandomlyByIdentifier("2"));
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2");
		EntityCreator.getInstance().createManyInTransaction((Collection<Object>)CollectionHelper.cast(Object.class, Employee.instantiateManyRandomlyByIdentifiers("3","4","5")));
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2"
				,"3","4","5");
		EmployeeData employeeData = EmployeeData.instantiateOneRandomlyByIdentifier("a");
		employeeData.setName("myname");
		EntitySaver.getInstance().save(EmployeeData.class, new Arguments<EmployeeData>()
				.setCreatables(List.of(employeeData)).setDeletables(List.of(EmployeeData.instantiat(EntityFinder.getInstance().find(Employee.class, "2")))));	
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("myname");
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1"
				,"3","4","5","a");
		employeeData = EmployeeData.instantiateOneRandomlyByIdentifier("a");
		employeeData.setName("n01");
		EntitySaver.getInstance().save(EmployeeData.class, new Arguments<EmployeeData>().setRepresentation(EmployeeSaver.getInstance())
				.setCreatables(List.of(EmployeeData.instantiateOneRandomlyByIdentifier("a1"),EmployeeData.instantiateOneRandomlyByIdentifier("a2")))
				.setUpdatables(List.of(employeeData))
				.setDeletables(List.of(EmployeeData.instantiat(EntityFinder.getInstance().find(Employee.class, "1"))))
				);	
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder(
				"3","4","5","a","a1","a2");
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("n01");
	}
	
	/* exception */
	
	@Test
	public void notYetRegistered(){
		assertThrows(RuntimeException.class, () -> {
			EntityReader.getInstance().readMany(EmployeeData.class,new Arguments<EmployeeData>().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
					.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.xxx")
							.addFilterField("identifiers",List.of("1")))));
		});
	}
	
	/**/
	
	private <T> void __assertRead__(Collection<T> entities,Object...expectedSystemIdentifiers) {
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(entities.stream().map(entity -> FieldHelper.readSystemIdentifier(entity)).collect(Collectors.toList())).containsExactly(expectedSystemIdentifiers);
	}
	
	private <T> void __assertReadOne__(T entity,Object expectedSystemIdentifier) {
		if(expectedSystemIdentifier == null) {
			assertThat(entity).isNull();
			return;
		}
		assertThat(entity).isNotNull();
		assertThat(FieldHelper.readSystemIdentifier(entity)).isEqualTo(expectedSystemIdentifier);
	}
}