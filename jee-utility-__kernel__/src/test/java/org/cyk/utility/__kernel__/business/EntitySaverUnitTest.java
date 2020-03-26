package org.cyk.utility.__kernel__.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityReader;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class EntitySaverUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	/* read all*/
	
	@Test
	public void save() throws Exception{		
		EntitySaver.Arguments<TestedEntityParent> arguments = new EntitySaver.Arguments<TestedEntityParent>().setIsTransactional(Boolean.TRUE)
				.setProvidedCollection(List.of(new TestedEntityParent("1","c1","n1")));
		EntitySaver.getInstance().save(TestedEntityParent.class,arguments);
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class), "1");
		
		arguments = new EntitySaver.Arguments<TestedEntityParent>().setIsTransactional(Boolean.TRUE)
				.setProvidedCollection(List.of(CollectionHelper.getFirst(EntityReader.getInstance().read(TestedEntityParent.class))
						,new TestedEntityParent("2","c2","n2"),new TestedEntityParent("3","c3","n3")));
		arguments.setExistingCollection(EntityReader.getInstance().read(TestedEntityParent.class));
		EntitySaver.getInstance().save(TestedEntityParent.class,arguments);
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class), "1","2","3");
	
		arguments = new EntitySaver.Arguments<TestedEntityParent>().setIsTransactional(Boolean.TRUE);
		arguments.setProvidedCollection(List.of(new TestedEntityParent("4","c4","n4"))).setExistingCollection(EntityReader.getInstance().read(TestedEntityParent.class));
		EntitySaver.getInstance().save(TestedEntityParent.class,arguments);
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class), "4");
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
}
