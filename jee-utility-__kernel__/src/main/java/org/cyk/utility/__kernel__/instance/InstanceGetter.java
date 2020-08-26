package org.cyk.utility.__kernel__.instance;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceGetter {

	<INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Object identifier,ValueUsageType valueUsageType);
	
	default <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.SYSTEM);
	}
	
	default <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.BUSINESS);
	}
	
	<INSTANCE> Collection<INSTANCE> get(Class<INSTANCE> klass,Properties properties);
	
	<INSTANCE> Collection<INSTANCE> getAll(Class<INSTANCE> klass,Properties properties);
	
	default <INSTANCE> Collection<INSTANCE> getAll(Class<INSTANCE> klass) {
		if(klass == null)
			return null;
		return getAll(klass,null);
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		return CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier, (URI)null, fieldsNames);
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifier(Class<INSTANCE> klass,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		return CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(klass,null, (URI)null, fieldsNames);
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,String...fieldsNames) {
		if(klass == null)
			return null;
		return getFromUniformResourceIdentifier(klass,classifier,CollectionHelper.listOf(fieldsNames));
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifier(Class<INSTANCE> klass,String...fieldsNames) {
		if(klass == null)
			return null;
		return getFromUniformResourceIdentifier(klass,CollectionHelper.listOf(fieldsNames));
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifiers(Class<INSTANCE> klass,Collection<Object> classifiers,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		if(CollectionHelper.isEmpty(classifiers))
			return CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(klass, (URI)null, fieldsNames);
		Collection<INSTANCE> instances = null;
		for(Object classifier : classifiers) {
			Collection<INSTANCE> collection = CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier, (URI)null, fieldsNames);
			if(CollectionHelper.isEmpty(collection))
				continue;
			if(instances == null)
				instances = new ArrayList<>();
			instances.addAll(collection);
		}
		return instances;
	}
	
	default <INSTANCE> Collection<INSTANCE> getFromUniformResourceIdentifiers(Class<INSTANCE> klass,Collection<Object> classifiers,String...fieldsNames) {
		if(klass == null)
			return null;
		return getFromUniformResourceIdentifiers(klass,classifiers,CollectionHelper.listOf(fieldsNames));
	}
	
	/**/
	
	static InstanceGetter getInstance() {
		return Helper.getInstance(InstanceGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}