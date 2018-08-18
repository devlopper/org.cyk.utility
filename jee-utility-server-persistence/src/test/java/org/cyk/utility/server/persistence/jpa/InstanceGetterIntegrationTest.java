package org.cyk.utility.server.persistence.jpa;

import org.cyk.utility.__kernel__.test.arquillian.ArchiveBuilder;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTest;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;

public class InstanceGetterIntegrationTest extends AbstractPersistenceArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void get(){
		__createEntity__(new MyEntity().setCode("ee01").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("ee02").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("ee03").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("ee04").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("ee05").setIntegerValue(2));
		
		MyEntity myEntity = __inject__(InstanceHelper.class).getByIdentifierBusiness(MyEntity.class, "ee01");
		Assert.assertNotNull(myEntity);
		Assert.assertEquals(new Integer(1), myEntity.getIntegerValue());
		
		myEntity = __inject__(InstanceHelper.class).getByIdentifierBusiness(MyEntity.class, "ee04");
		Assert.assertNotNull(myEntity);
		Assert.assertEquals(new Integer(2), myEntity.getIntegerValue());
		
		__deleteEntitiesAll__(MyEntity.class);
		
	}
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new ArchiveBuilder<WebArchive>(WebArchive.class).setBeanXml("org/cyk/utility/server/persistence/jpa/beans.xml").execute(); 
	}
	
}
