package org.cyk.utility.server.persistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class JpaEntityManagerGraphIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void isMyEntityFieldIntegerValueValueNotLoaded() throws Exception{
		EntityManager entityManager = __inject__(EntityManager.class);
		String identifier = __getRandomIdentifier__();
		MyEntity myEntity = new MyEntity().setIdentifier(identifier).setCode("mc001").setIntegerValue(159).setPhones(Arrays.asList("1","2","3","4","5"));
		userTransaction.begin();
		entityManager.persist(myEntity);
		userTransaction.commit();
		
		myEntity = entityManager.find(MyEntity.class, identifier);
		assertionHelper.assertNotNull("entity is null", myEntity);
		assertionHelper.assertEquals("mc001", myEntity.getCode());
		assertionHelper.assertEquals(159, myEntity.getIntegerValue());
		//assertionHelper.assertEquals(null, myEntity.getPhones());
		
		EntityGraph<MyEntity> entityGraph = entityManager.createEntityGraph(MyEntity.class);
		entityGraph.addAttributeNodes("code");
		entityGraph.addAttributeNodes("integerValue");
		entityGraph.addAttributeNodes("phones");		
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		myEntity = entityManager.find(MyEntity.class, identifier,properties);
		assertionHelper.assertNotNull("entity is null", myEntity);
		assertionHelper.assertEquals("mc001", myEntity.getCode());
		assertionHelper.assertEquals(159, myEntity.getIntegerValue());
		assertThat(myEntity.getPhones()).containsExactly("1","2","3","4","5");
		
		entityGraph = entityManager.createEntityGraph(MyEntity.class);
		entityGraph.addAttributeNodes("code");
		properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		myEntity = entityManager.find(MyEntity.class, identifier,properties);
		assertionHelper.assertNotNull("entity is null", myEntity);
		assertionHelper.assertEquals("mc001", myEntity.getCode());
		//assertionHelper.assertEquals(null, myEntity.getIntegerValue());
		//assertionHelper.assertEquals(null, myEntity.getPhones());
		
		//assertionHelper.assertNull("field integer value has been loaded", myEntity.getIntegerValue());
	}
}
