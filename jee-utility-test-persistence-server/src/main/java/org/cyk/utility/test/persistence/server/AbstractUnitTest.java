package org.cyk.utility.test.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringAuditedImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;

public abstract class AbstractUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		initializeEntityManagerFactory(getPersistenceUnitName());
		VariableHelper.write(VariableName.SYSTEM_LOGGING_THROWABLE_PRINT_STACK_TRACE, Boolean.TRUE);
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
		callInitialize();
	}
	
	protected void callInitialize() {
		Initializer.initialize();
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
	
	public void assertThatCountIsEqualTo(Class<?> klass,Long number){
		Long count = EntityCounter.getInstance().count(klass);
		assertThat(count).as(String.format("number of %s is greater than %s",klass.getSimpleName(),number)).isEqualTo(number);
	}
	
	public void assertThatCountIsGreaterThan(Class<?> klass,Long number,Boolean printable){
		Long count = EntityCounter.getInstance().count(klass);
		if(Boolean.TRUE.equals(printable))
			System.out.println(klass.getSimpleName()+" : "+count);
		assertThat(count).as(String.format("number of %s is greater than %s",klass.getSimpleName(),number)).isGreaterThan(number);
	}
	
	public void assertThatCountIsGreaterThanZero(Boolean printable,Collection<Class<?>> classes){
		for(Class<?> klass : classes)
			assertThatCountIsGreaterThan(klass, 0l,printable);
	}
	
	public void assertThatCountIsGreaterThanZero(Collection<Class<?>> classes){
		assertThatCountIsGreaterThanZero(null, classes);
	}
	
	public void assertThatCountIsGreaterThanZero(Boolean printable,Class<?>...classes){
		assertThatCountIsGreaterThanZero(printable,CollectionHelper.listOf(classes));
	}
	
	public void assertThatCountIsGreaterThanZero(Class<?>...classes){
		assertThatCountIsGreaterThanZero(null, classes);
	}
	
	public void assertThatContainsCodesExactlyInAnyOrder(Class<?> klass,String...values){
		Collection<?> collection = EntityReader.getInstance().readMany(klass);
		assertThat(collection).isNotEmpty();
		assertThat(FieldHelper.readBusinessIdentifiersAsStrings(collection)).containsExactlyInAnyOrder(values);
	}
	
	public void assertThatContainsCodes(Class<?> klass,String...values){
		Collection<?> collection = EntityReader.getInstance().readMany(klass);
		assertThat(collection).isNotEmpty();
		assertThat(FieldHelper.readBusinessIdentifiersAsStrings(collection)).contains(values);
	}
	
	public static Object[] asLongs(Object[] array,Integer from) {
		if(array!=null)
			for(Integer index = from; index < array.length; index = index + 1)
				array[index] = NumberHelper.getLong(array[index]);
		return array;
	}
	
	public static <T> T assertExistenceAndGet(Class<T> klass,Object identifier) {
		T instance = EntityFinder.getInstance().find(klass, identifier);
		//T instance = DynamicOneExecutor.getInstance().read(klass, new QueryExecutorArguments().addFilterFieldsValues(Querier.PARAMETER_NAME_IDENTIFIER,identifier));
		assertThat(instance).as(String.format("%s with ID %s not found", klass.getSimpleName(),identifier)).isNotNull();
		return instance;
	}
	
	public static <T> T assertAudits(Class<T> klass,Object identifier,String expectedActor,String expectedFunctionality,String expectedAction) {
		T instance = assertExistenceAndGet(klass, identifier);
		assertThat(ClassHelper.isInstanceOfOne(klass, AbstractIdentifiableSystemScalarStringAuditedImpl.class
				,AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.class
				,AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl.class))
			.as(String.format("%s with ID %s is not audited", klass.getSimpleName(),identifier)).isNotNull();
		String actor = null,functionality = null,what = null;
		//LocalDateTime when = null;
		if(ClassHelper.isInstanceOf(klass, AbstractIdentifiableSystemScalarStringAuditedImpl.class)) {
			actor = ((AbstractIdentifiableSystemScalarStringAuditedImpl)instance).get__auditWho__();
			functionality = ((AbstractIdentifiableSystemScalarStringAuditedImpl)instance).get__auditFunctionality__();
			what = ((AbstractIdentifiableSystemScalarStringAuditedImpl)instance).get__auditWhat__();
			//when = ((AbstractIdentifiableSystemScalarStringAuditedImpl)instance).get__auditWhen__();
		}else if(ClassHelper.isInstanceOf(klass, AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.class)) {
			actor = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl)instance).get__auditWho__();
			functionality = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl)instance).get__auditFunctionality__();
			what = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl)instance).get__auditWhat__();
			//when = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl)instance).get__auditWhen__();
		}else if(ClassHelper.isInstanceOf(klass, AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl.class)) {
			actor = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl)instance).get__auditWho__();
			functionality = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl)instance).get__auditFunctionality__();
			what = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl)instance).get__auditWhat__();
			//when = ((AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl)instance).get__auditWhen__();
		}
		assertThat(actor).as(String.format("%s with ID %s - Actor %s", klass.getSimpleName(),identifier,actor)).isEqualTo(expectedActor);
		assertThat(functionality).as(String.format("%s with ID %s - Functionality %s", klass.getSimpleName(),identifier,functionality)).isEqualTo(expectedFunctionality);
		assertThat(what).as(String.format("%s with ID %s - Action %s", klass.getSimpleName(),identifier,what)).isEqualTo(expectedAction);
		return instance;
	}
}