package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeployment;
import org.cyk.utility.sql.builder.Attribute;
import org.cyk.utility.sql.builder.Tuple;
import org.junit.Assert;
import org.junit.Test;

public class MyEntityPersistenceIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeployment<MyEntity> {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Test
	public void readByIntegerValueUsingCustom(){
		__createEntity__(new MyEntity().setCode("ee01").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("ee02").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("ee03").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("ee04").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("ee05").setIntegerValue(2));
		
		String query = __inject__(MyEntityPersistence.class).instanciateReadByIntegerValueQueryStringBuilder()
				.orderBy("code")
				.execute().getOutput();

		Collection<MyEntity> c1 = (Collection<MyEntity>) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setQueryValue(query)
				.setQueryParameters(new Properties().set("integerValue", 2)).execute().getEntities();
		Assert.assertEquals(3, c1.size());
		
		query = __inject__(MyEntityPersistence.class).instanciateReadByIntegerValueQueryStringBuilder()
				.orderBy(new Attribute().setName("code").setTuple(new Tuple().setName("MyEntity")).setSortOrder(SortOrder.DESCENDING))
				.execute().getOutput();
		Collection<MyEntity> c2 = (Collection<MyEntity>) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setQueryValue(query)
				.setQueryParameters(new Properties().set("integerValue", 2)).execute().getEntities();
		Assert.assertEquals(3, c2.size());
		
		Collection<MyEntity> c3 = (Collection<MyEntity>) __inject__(MyEntityPersistence.class).read();
		Assert.assertEquals(5, c3.size());
		
		__deleteEntitiesAll__(MyEntity.class);
		
	}
	
	@Test
	public void readIdentifierContains(){
		__createEntity__(new MyEntity().setIdentifier("123").setCode(__getRandomCode__()).setIntegerValue(1));
		__createEntity__(new MyEntity().setIdentifier("133").setCode(__getRandomCode__()).setIntegerValue(2));
		__createEntity__(new MyEntity().setIdentifier("144").setCode(__getRandomCode__()).setIntegerValue(1));
		__createEntity__(new MyEntity().setIdentifier("150").setCode(__getRandomCode__()).setIntegerValue(2));
		__createEntity__(new MyEntity().setIdentifier("623").setCode(__getRandomCode__()).setIntegerValue(2));
		
		Collection<MyEntity> entities = __inject__(MyEntityPersistence.class).read(new Properties().setQueryFilters(__inject__(CollectionHelper.class).instanciate("123")));
		org.assertj.core.api.Assertions.assertThat(entities).isNotEmpty();
		org.assertj.core.api.Assertions.assertThat(entities.stream().map(MyEntity::getIdentifier)).containsExactly("123");
		
		entities = __inject__(MyEntityPersistence.class).read(new Properties().setQueryFilters(__inject__(CollectionHelper.class).instanciate("23")));
		org.assertj.core.api.Assertions.assertThat(entities).isNotEmpty();
		org.assertj.core.api.Assertions.assertThat(entities.stream().map(MyEntity::getIdentifier)).containsExactly("123","623");
		
		entities = __inject__(MyEntityPersistence.class).read(new Properties().setQueryFilters(__inject__(CollectionHelper.class).instanciate("3")));
		org.assertj.core.api.Assertions.assertThat(entities).isNotEmpty();
		org.assertj.core.api.Assertions.assertThat(entities.stream().map(MyEntity::getIdentifier)).containsExactly("123","133","623");
		
		__deleteEntitiesAll__(MyEntity.class);
		
	}
	
	@Test
	public void readByIntegerValue(){
		__createEntity__(new MyEntity().setCode("e01").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("e02").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("e03").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("e04").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("e05").setIntegerValue(2));
		
		Collection<MyEntity> collection = ____inject____(MyEntityPersistence.class).readByIntegerValue(2);
		Assert.assertNotNull(collection);
		Assert.assertEquals(3, collection.size());
		Assert.assertEquals(new Long(3), ____inject____(MyEntityPersistence.class).countByIntegerValue(2));
		
		__deleteEntitiesAll__(MyEntity.class);
	}
	
	@Test
	public void executeIncrementIntegerValue(){
		__createEntity__(new MyEntity().setCode("e01A").setIntegerValue(10));
		__createEntity__(new MyEntity().setCode("e02B").setIntegerValue(20));
		__createEntity__(new MyEntity().setCode("e03C").setIntegerValue(10));
		__createEntity__(new MyEntity().setCode("e04D").setIntegerValue(20));
		__createEntity__(new MyEntity().setCode("e05E").setIntegerValue(20));
		
		try {
			userTransaction.begin();
			____inject____(MyEntityPersistence.class).executeIncrementIntegerValue(7);
			userTransaction.commit();	
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		
		MyEntity myEntity = ____inject____(MyEntityPersistence.class).readOneByBusinessIdentifier("e02B");
		Assert.assertEquals(new Integer(27), myEntity.getIntegerValue());
		
		__deleteEntitiesAll__(MyEntity.class);
		
	}
}
