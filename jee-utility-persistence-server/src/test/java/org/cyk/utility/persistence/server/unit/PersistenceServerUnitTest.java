package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.junit.jupiter.api.Test;

public class PersistenceServerUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void entityManagerFactory_isNotNull(){
		assertThat(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY).isNotNull();
	}
	
}