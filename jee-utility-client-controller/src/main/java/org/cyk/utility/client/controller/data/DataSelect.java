package org.cyk.utility.client.controller.data;
import java.util.Collection;

import org.cyk.utility.client.controller.data.Data;

public interface DataSelect<DATA> extends Data {

	DATA getOne();
	DataSelect<DATA> setOne(DATA one);
	
	Collection<DATA> getMany();
	DataSelect<DATA>  setMany(Collection<DATA> many);
	
	Collection<Object> getSystemIdentifiers();
	
	/**/
	
	public static final String PROPERTY_ONE = "one";
	public static final String PROPERTY_MANY = "many";
	
}
