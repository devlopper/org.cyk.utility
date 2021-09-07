package org.cyk.utility.persistence.server.unit;

import javax.persistence.EntityManager;

import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.DataType;
import org.junit.jupiter.api.Test;

public class EntityLifeCycleListenerUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void pre_persist(){
		DataType dataType = new DataType().setIdentifier("id01").setCode("c01").setName("n01");
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.persist(dataType);
		entityManager.getTransaction().commit();
	}
}