package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.persistence.server.MetricsManager;
import org.junit.jupiter.api.Test;

public class MetricsUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
		
	@Override
	protected String getPersistenceUnitName() {
		return "statistics";
	}
	
	@Test
	public void show(){
		MetricsManager.getInstance().print();
	}
}