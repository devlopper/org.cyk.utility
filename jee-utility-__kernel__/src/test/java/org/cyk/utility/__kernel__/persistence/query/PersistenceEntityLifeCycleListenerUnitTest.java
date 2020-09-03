package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.__entities__.AuditedEntity;
import org.cyk.utility.__kernel__.object.__static__.persistence.EntityLifeCycleListener;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class PersistenceEntityLifeCycleListenerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence_life_cycle_listener");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.class, EntityLifeCycleListener.class);
		SecurityHelper.PRINCIPALABLE.set(Boolean.FALSE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void listenPersist(){
		EntityCreator.getInstance().createOneInTransaction(new AuditedEntity().setCode("1"));
		/*assertThat(EntityReader.getInstance().readOneBySystemIdentifier(Employee.class,"1s")).isNull();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			EntityCreator.getInstance().createOneInTransaction(new Employee().setIdentifier(index+"s").setCode(index+"b").setName(index+""));
		assertThat(EntityReader.getInstance().readOneBySystemIdentifier(Employee.class,"1s")).isNotNull();
		*/
	}
}