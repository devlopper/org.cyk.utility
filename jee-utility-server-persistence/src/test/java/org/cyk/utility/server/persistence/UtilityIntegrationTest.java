package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.instance.InstanceGetterImpl;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Assert;
import org.junit.Test;

public class UtilityIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void getInstanceMyEntityByBusinessIdentifier(){
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class,100,Boolean.TRUE);
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
	
	/**/
	
	public static class InstanceGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public InstanceGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(FieldName.IDENTIFIER.equals(getFunction().getFieldName())) {
						Object one = __inject__(Persistence.class).readOne(getFunction().getClazz(), getFunction().getValue(), new Properties().setValueUsageType(getFunction().getValueUsageType()));
						Collection<Object> collection = new ArrayList<>();
						collection.add(one);
						setOutput(collection);
					}
					//setOutput(output);
					/*if(MyData.class.equals( getFunction().getClazz() )) {
						if("a001".equals(getFunction().getValue()))
							setOutput(Arrays.asList(new MyData().setId("159").setNum("a001")));
					}*/
				}
			});
		}
		
	}
	
}
