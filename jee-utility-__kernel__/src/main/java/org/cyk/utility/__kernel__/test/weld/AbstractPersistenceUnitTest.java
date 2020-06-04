package org.cyk.utility.__kernel__.test.weld;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;

public abstract class AbstractPersistenceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		initializeEntityManagerFactory(getPersistenceUnitName());
		createData();
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
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
		QueryHelper.clear();
	}
	
	/**/
	
	public  void assertCountIsGreaterThanZero(Collection<Class<?>> classes){
		if(CollectionHelper.isEmpty(classes))
			return;
		classes.forEach(klass -> {
			assertThat(EntityCounter.getInstance().count(klass)).as("count "+klass.getSimpleName()).isGreaterThan(0l);
		});
	}
	
	public  void assertCountIsGreaterThanZero(Class<?>...classes){
		if(ArrayHelper.isEmpty(classes))
			return;
		assertCountIsGreaterThanZero(CollectionHelper.listOf(classes));
	}
}