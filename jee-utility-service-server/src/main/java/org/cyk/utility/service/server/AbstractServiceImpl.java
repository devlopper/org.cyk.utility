package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.service.Service;

public abstract class AbstractServiceImpl implements Service,Serializable {

	@Inject protected RequestExecutor requestExecutor;
	
	protected Response execute(RequestExecutor.Request request) {
		return requestExecutor.execute(request);
	}
	
	/**/
	
	public static final Map<Class<?>,Map<String,String>> PROJECTIONS_MAPS = new HashMap<>();
	public static void setProjections(Class<?> klass,Map<String,String> map) {
		if(klass == null || map == null)
			return;
		PROJECTIONS_MAPS.put(klass, map);
	}
	
	public static void setProjections(Class<?> klass,String...fieldsNames) {
		if(klass == null || ArrayHelper.isEmpty(fieldsNames))
			return;
		setProjections(klass, MapHelper.instantiateStringString(fieldsNames));
	}
	
	public static interface SessionIdentificationListener {
		void set(String identifier);
		String get();
	}
}