package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface DataLoader {

	Response load();
	
	/**/
	
	static DataLoader getInstance() {
		DataLoader instance = (DataLoader) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(DataLoader.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", DataLoader.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
