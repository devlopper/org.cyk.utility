package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface CountQueryIdentifierGetter {

	String get(String readQueryIdentifier);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements CountQueryIdentifierGetter,Serializable {		
		@Override
		public String get(String readQueryIdentifier) {
			if(StringHelper.isBlank(readQueryIdentifier))
				return null;
			if(MAP.containsKey(readQueryIdentifier))
				return MAP.get(readQueryIdentifier);
			String identifier = __get__(readQueryIdentifier);
			MAP.put(readQueryIdentifier, identifier);
			return identifier;
		}
		
		protected String __get__(String readQueryIdentifier) {
			return QueryIdentifierBuilder.getInstance().buildCountFrom(readQueryIdentifier);
		}
	}
	
	/**/
	
	static CountQueryIdentifierGetter getInstance() {
		return Helper.getInstance(CountQueryIdentifierGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	Map<String,String> MAP = new HashMap<>();
}