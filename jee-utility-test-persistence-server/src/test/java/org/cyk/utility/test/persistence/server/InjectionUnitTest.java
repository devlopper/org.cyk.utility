package org.cyk.utility.test.persistence.server;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.persistence.query.QueryIdentifierGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class InjectionUnitTest extends AbstractUnitTest {

	@Override
	protected void __listenBefore__() {
		// TODO Auto-generated method stub
		super.__listenBefore__();
		System.out.println("InjectionUnitTest.__listenBefore__() : "+weldInitiator);
	}
	
	@Override
	protected void __listenAfter__() {
		// TODO Auto-generated method stub
		super.__listenAfter__();
		System.out.println("InjectionUnitTest.__listenAfter__() : "+weldInitiator);
	}
	
	@Override
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		
	}
	
	@Test
	public void first() {
		DependencyInjection.inject(QueryIdentifierGetter.class);
	}
	
	@Test
	public void second() {
		DependencyInjection.inject(QueryIdentifierGetter.class);
	}
	
}