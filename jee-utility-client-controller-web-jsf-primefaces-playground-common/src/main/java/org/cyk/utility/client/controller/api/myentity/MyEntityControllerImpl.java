package org.cyk.utility.client.controller.api.myentity;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.AbstractControllerEntityImpl;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.collection.CollectionHelper;

@Singleton
public class MyEntityControllerImpl extends AbstractControllerEntityImpl<MyEntity> implements MyEntityController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<MyEntity> readMany(Properties properties) {
		return COLLECTION;
	}
	
	private static final Collection<MyEntity> COLLECTION = DependencyInjection.inject(CollectionHelper.class).instanciate(
			DependencyInjection.inject(MyEntity.class).setIdentifier("1").setCode("C01").setName("Name01").setDescription("Line0101\nLine0102")
			,DependencyInjection.inject(MyEntity.class).setIdentifier("2").setCode("C02").setName("Name02").setDescription("Line0201\nLine0202")
			,DependencyInjection.inject(MyEntity.class).setIdentifier("3").setCode("C03").setName("Name03").setDescription("Line0301\nLine0302")
			);
}
