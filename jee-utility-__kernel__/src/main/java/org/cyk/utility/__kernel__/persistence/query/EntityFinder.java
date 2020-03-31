package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.value.Value;
import org.jboss.weld.exceptions.IllegalArgumentException;

public interface EntityFinder {

	default <T> T find(Class<T> klass,QueryExecutorArguments arguments) {
		if(klass == null)
			throw new IllegalArgumentException("class is required");
		if(arguments == null)
			throw new IllegalArgumentException("arguments are required");
		if(CollectionHelper.isEmpty(arguments.getSystemIdentifiers()))
			throw new IllegalArgumentException("system identifier is required");
		if(CollectionHelper.getSize(arguments.getSystemIdentifiers()) > 1)
			throw new IllegalArgumentException("only one system identifier is required");
		arguments.prepare();
		Map<String,Object> properties = arguments.getProperties() == null ? null : arguments.getProperties().deriveMap(klass, arguments.get__entityManager__());
		T object = arguments.get__entityManager__().find(klass, CollectionHelper.getFirst(arguments.getSystemIdentifiers()),properties);
		arguments.set__objects__(List.of(object));
		arguments.finalise();
		return object;
	}
	
	default <T> T find(Class<T> klass,Object identifier,Map<String,Object> properties,EntityManager entityManager) {
		if(klass == null || identifier == null || entityManager == null)
			return null;
		return entityManager.find(klass, identifier,properties);
	}
	
	default <T> T find(Class<T> klass,Object identifier,Map<String,Object> properties) {
		if(klass == null || identifier == null)
			return null;
		return find(klass, identifier,properties,EntityManagerGetter.getInstance().get());
	}
	
	default <T> T find(Class<T> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return find(klass, new QueryExecutorArguments().addSystemIdentifiers(identifier));
	}
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityFinder,Serializable {
		private static final long serialVersionUID = 1L;

	}
	
	/**/
	
	static EntityFinder getInstance() {
		return Helper.getInstance(EntityFinder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}