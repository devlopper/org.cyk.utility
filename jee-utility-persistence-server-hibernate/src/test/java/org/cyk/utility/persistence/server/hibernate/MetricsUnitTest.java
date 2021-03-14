package org.cyk.utility.persistence.server.hibernate;

import java.util.Map;

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
		Map<String,Object> map = MetricsManager.getInstance().get();
		if(map != null)
			map.forEach( (k,v) -> {
				System.out.println(k+" : "+v);
			} );
	}
}