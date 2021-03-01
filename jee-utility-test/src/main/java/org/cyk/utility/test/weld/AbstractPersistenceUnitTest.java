package org.cyk.utility.test.weld;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

public abstract class AbstractPersistenceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		//SecurityHelper.PRINCIPALABLE.set(Boolean.FALSE);
		initializeEntityManagerFactory(getPersistenceUnitName());
		createData();
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		//EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
	
	protected void createData() {}
	protected void deleteData() {}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		deleteData();
		//QueryHelper.clear();
	}
	
	/**/
	
	protected abstract Long count(Class<?> klass);
	
	public  void assertCountIsGreaterThanZero(Collection<Class<?>> classes){
		if(classes == null || classes.isEmpty())
			return;
		classes.forEach(klass -> {
			assertThat(count(klass)).as("count "+klass.getSimpleName()+" is greater then zero").isGreaterThan(0l);
		});
	}
	
	public  void assertCountIsGreaterThanZero(Class<?>...classes){
		if(classes == null || classes.length == 0)
			return;
		assertCountIsGreaterThanZero(List.of(classes));
	}
	
	public  void assertCountIsNotNull(Collection<Class<?>> classes){
		if(classes == null || classes.isEmpty())
			return;
		classes.forEach(klass -> {
			assertThat(count(klass)).as("count "+klass.getSimpleName()+" is not null").isNotNull();
		});
	}
	
	public  void assertCountIsNotNull(Class<?>...classes){
		if(classes == null || classes.length == 0)
			return;
		assertCountIsNotNull(List.of(classes));
	}
}