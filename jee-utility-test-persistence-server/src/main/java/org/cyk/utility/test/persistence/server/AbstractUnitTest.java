package org.cyk.utility.test.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;

public abstract class AbstractUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		initializeEntityManagerFactory(getPersistenceUnitName());	
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
		Initializer.initialize();
	}
	
	protected String getPersistenceUnitName() {
		return "default";
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
}