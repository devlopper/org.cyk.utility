package org.cyk.utility.persistence.server;

import javax.persistence.Persistence;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.junit.jupiter.api.Test;

public class JpaEntityManagerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {		
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
	}
	
	@Test
	public void entityManagerFactory(){
		System.out.println("JpaEntityManagerUnitTest.entityManagerFactory() : "+EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY);
	}
}