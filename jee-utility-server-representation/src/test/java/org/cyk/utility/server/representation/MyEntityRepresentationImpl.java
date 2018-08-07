package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public RepresentationServiceProvider<MyEntity> create(MyEntity myEntity, Properties properties) {
		/*properties = addExecutionPhaseAssertions(properties,Boolean.TRUE, __injectAssertionBuilderNull__(Boolean.FALSE,myEntity,"long1"));
		addExecutionPhaseRunnables(properties, true, new Runnable(){
			@Override
			public void run() {
				System.out.println("MyEntityRepresentationImpl.create(...).new Runnable() {...}.run()");
			}});
		*/
		return super.create(myEntity, properties);
	}

}
