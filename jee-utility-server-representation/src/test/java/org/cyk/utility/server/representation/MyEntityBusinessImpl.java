package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessServiceProvider;

@Singleton
public class MyEntityBusinessImpl extends AbstractBusinessEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<MyEntity> __getPersistenceEntityClass__() {
		return MyEntity.class;
	}
	
	@Override
	public BusinessServiceProvider<MyEntity> create(MyEntity myEntity, Properties properties) {
		/*properties = addExecutionPhaseAssertions(properties,Boolean.TRUE, __injectAssertionBuilderNull__(Boolean.FALSE,myEntity,"long1"));
		addExecutionPhaseRunnables(properties, true, new Runnable(){
			@Override
			public void run() {
				System.out.println("MyEntityBusinessImpl.create(...).new Runnable() {...}.run()");
			}});
		*/
		return super.create(myEntity, properties);
	}

}
