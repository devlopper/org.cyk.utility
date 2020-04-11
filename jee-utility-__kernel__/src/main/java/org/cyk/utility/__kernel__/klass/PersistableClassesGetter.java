package org.cyk.utility.__kernel__.klass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface PersistableClassesGetter {

	@SuppressWarnings("unchecked")
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
		PersistableClassesGetter instance = (PersistableClassesGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(PersistableClassesGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", PersistableClassesGetter.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
	Value COLLECTION = new Value();	
}