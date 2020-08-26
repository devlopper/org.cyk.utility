package org.cyk.utility.__kernel__.klass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface PersistableClassesGetter {

	default Collection<Class<?>> get() {
		if(COLLECTION.isHasBeenSet())
			return (Collection<Class<?>>) COLLECTION.get();
		EntityManagerFactory entityManagerFactory = DependencyInjection.inject(EntityManagerFactory.class);
		if(CollectionHelper.isEmpty(entityManagerFactory.getMetamodel().getEntities()))
			return null;
		List<Class<?>> classes = new ArrayList<>();
		for(EntityType<?> index : entityManagerFactory.getMetamodel().getEntities())
			classes.add(index.getJavaType());
		PersistenceHelper.sort(classes,Boolean.FALSE);
		COLLECTION.set(classes);
		return classes;
	}
		
	/**/
	
	static PersistableClassesGetter getInstance() {
		return Helper.getInstance(PersistableClassesGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	Value COLLECTION = new Value();	
}