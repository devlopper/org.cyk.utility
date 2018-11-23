package org.cyk.utility.client.controller.entities.myentity;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.collection.CollectionHelper;

public interface MyEntity extends Data {

	@Override MyEntity setIdentifier(Object identifier);
	@Override MyEntity setCode(String code);
	
	String getName();
	MyEntity setName(String name);
	
	String getDescription();
	MyEntity setDescription(String description);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	
	/**/
	
	Collection<MyEntity> COLLECTION = DependencyInjection.inject(CollectionHelper.class).instanciate(
			DependencyInjection.inject(MyEntity.class).setIdentifier("12").setCode("C01").setName("Name01").setDescription("Line0101\nLine0102")
			,DependencyInjection.inject(MyEntity.class).setIdentifier("17").setCode("C02").setName("Name02").setDescription("Line0201\nLine0202")
			,DependencyInjection.inject(MyEntity.class).setIdentifier("05").setCode("C03").setName("Name03").setDescription("Line0301\nLine0302")
			);
	
	
	/*
	Collection<MyEntityReadRow> ROWS = DependencyInjection.inject(CollectionHelper.class).instanciate(
			DependencyInjection.inject(MyEntityReadRow.class).setData(DependencyInjection.inject(MyEntity.class).setIdentifier("12").setCode("C01").setName("Name01").setDescription("Line0101\n\rLine0102"))
			,DependencyInjection.inject(MyEntityReadRow.class).setData(DependencyInjection.inject(MyEntity.class).setIdentifier("17").setCode("C02").setName("Name02").setDescription("Line0201\n\rLine0202"))
			,DependencyInjection.inject(MyEntityReadRow.class).setData(DependencyInjection.inject(MyEntity.class).setIdentifier("05").setCode("C03").setName("Name03").setDescription("Line0301\n\rLine0202"))
			);
			*/
}
