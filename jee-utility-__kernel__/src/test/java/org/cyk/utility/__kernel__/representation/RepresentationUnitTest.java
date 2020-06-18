package org.cyk.utility.__kernel__.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.__entities__.Employee;
import org.cyk.utility.__kernel__.__entities__.EmployeeDto;
import org.cyk.utility.__kernel__.__entities__.EmployeeDtoMapper;
import org.cyk.utility.__kernel__.__entities__.EmployeeSaver;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.mapping.MapperClassGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.EntityFinder;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class RepresentationUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("representation_queries");
		MapperClassGetterImpl.MAP.put(EmployeeDto.class, EmployeeDtoMapper.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(Employee.class, "SELECT t FROM Employee t WHERE t.identifier IN :identifiers"));
		org.cyk.utility.__kernel__.persistence.EntitySaver.Arguments.IS_TRANSACTIONAL = Boolean.TRUE;
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		EntityReader.INSTANCE.set(null);
		EntityCounter.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
		EmployeeSaver.INSTANCE.set(null);
	}
	
	@Test
	public void count_employee(){
		assertThat(EntityCounter.getInstance().count(new Arguments().setRepresentationEntityClass(EmployeeDto.class)).getEntity()).isEqualTo(0l);
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier(index+"").setCode(index+"").setName(index+""));
		assertThat(EntityCounter.getInstance().count(new Arguments().setRepresentationEntityClass(EmployeeDto.class)).getEntity()).isEqualTo(10l);
	}
	
	/* read all */
	
	@Test
	public void employee_withoutTuple_read_all(){		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)));
	}
	
	@Test
	public void employee_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new Employee("1","1","1"));		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)), "1");
	}
	
	@Test
	public void employee_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)), "1","2");
	}
	
	@Test
	public void employee_withTuple_many_read_all_logged(){		
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class).setLoggableAsInfo(Boolean.TRUE)), "1","2");
	}
	
	/* read by system identifier */
	
	@Test
	public void employee_withoutTuple_read_bySystemIdentifiers(){		
		VariableHelper.write(VariableName.SYSTEM_LOGGING_THROWABLE_PRINT_STACK_TRACE, Boolean.TRUE);
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)
				.setQueryExecutorArguments(
						new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))
						)), "1");
	}
	
	@Test
	public void employee_withoutTuple_read_bySystemIdentifier(){		
		EntityCreator.getInstance().createManyInTransaction(new Employee("1","1","1"),new Employee("2","2","1"));
		__assertReadOne__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)
				.setQueryExecutorArguments(
						new QueryExecutorArguments.Dto().setCollectionable(Boolean.FALSE).setQueryIdentifier("Employee.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))
						)), "1");
	}
	
	/* read by business identifier */
	
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
		EmployeeSaver.getInstance().save(List.of(EmployeeDto.instantiateOneRandomlyByIdentifier("a").setName("myname"))
				,null,List.of(EmployeeDto.instantiat(EntityFinder.getInstance().find(Employee.class, "2")))
				, new org.cyk.utility.__kernel__.representation.Arguments());
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("myname");
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder("1"
				,"3","4","5","a");
		EmployeeSaver.getInstance().save(
				List.of(EmployeeDto.instantiateOneRandomlyByIdentifier("a1"),EmployeeDto.instantiateOneRandomlyByIdentifier("a2"))
				,List.of(EmployeeDto.instantiateOneRandomlyByIdentifier("a").setName("n01"))
				,List.of(EmployeeDto.instantiat(EntityFinder.getInstance().find(Employee.class, "1")))
				, new org.cyk.utility.__kernel__.representation.Arguments()				
			);
		assertThat(entityReader.readMany(Employee.class).stream().map(Employee::getIdentifier).collect(Collectors.toList())).containsExactlyInAnyOrder(
				"3","4","5","a","a1","a2");
		assertThat(EntityFinder.getInstance().find(Employee.class, "a").getName()).isEqualTo("n01");
	}
	
	/* exceptions */
	
	@Test
	public void notYetRegistered(){
		Response response = EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(EmployeeDto.class)
				.setPersistenceEntityClass(Employee.class).setQueryExecutorArguments(
						new QueryExecutorArguments.Dto().setQueryIdentifier("Employee.xxx").addFilterField("identifiers",List.of("1"))));
		assertThat(response).isNotNull();
		assertThat(ResponseHelper.isFamilyClientError(response)).isTrue();
	}
	
	/**/
	
	private <T> void __assertReadMany__(Response response,Object...expectedSystemIdentifiers) {
		assertThat(response).as("response is not null").isNotNull();
		if(response.hasEntity())
			assertThat(response.getEntity()).isInstanceOf(Collection.class);
		Collection<?> entities = (Collection<?>) response.getEntity();
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(FieldHelper.readSystemIdentifiers(entities)).containsExactly(expectedSystemIdentifiers);
	}
	
	private <T> void __assertReadOne__(Response response,Object expectedSystemIdentifier) {
		assertThat(response).as("response is not null").isNotNull();
		assertThat(response.getEntity()).isNotInstanceOf(Collection.class);
		Object entity = response.getEntity();
		if(expectedSystemIdentifier == null) {
			assertThat(entity).isNull();
			return;
		}
		assertThat(entity).isNotNull();
		assertThat(FieldHelper.readSystemIdentifier(entity)).isEqualTo(expectedSystemIdentifier);
	}
	
	/**/
	
}