package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface QueryIdentifierGetter {

	String get(Class<?> klass,String name);
	default String get(Class<?> klass,QueryName name) {
		if(klass == null || name == null)
			return null;
		return get(klass,name.getValue());
	}
	String getCountFromRead(Class<?> klass,String name);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements QueryIdentifierGetter,Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final Map<String,String> MAP = new HashMap<>();
		
		@Override
		public String get(Class<?> klass,String name) {
			if(klass == null || StringHelper.isBlank(name))
				return null;
			String key = klass.getSimpleName()+"."+name;
			String identifier = MAP.get(key);
			if(MAP.containsKey(key))
				return MAP.get(identifier);
			MAP.put(key, identifier = QueryIdentifierBuilder.getInstance().build(klass, name));
			return identifier;
		}
		
		@Override
		public String getCountFromRead(Class<?> klass,String name) {
			if(klass == null || StringHelper.isBlank(name))
				return null;
			String key = klass.getSimpleName()+".count."+name;
			String identifier = MAP.get(key);
			if(MAP.containsKey(key))
				return MAP.get(identifier);
			MAP.put(key, identifier = QueryIdentifierBuilder.getInstance().build(new QueryIdentifierBuilder.Arguments()
					.setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(QueryIdentifierBuilder.getInstance().build(klass, name))
					.setIsCountInstances(Boolean.TRUE)));
			return identifier;
		}
	
	}
	
	/**/
	
	static QueryIdentifierGetter getInstance() {
		return Helper.getInstance(QueryIdentifierGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}