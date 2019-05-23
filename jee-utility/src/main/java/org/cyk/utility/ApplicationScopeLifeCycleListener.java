package org.cyk.utility;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.request.RequestHelper;
import org.cyk.utility.stream.distributed.StreamDistributedHelper;
import org.cyk.utility.stream.distributed.Topic;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		if(Boolean.TRUE.equals(DependencyInjection.inject(StreamDistributedHelper.class).getIsEnable())) {
			Topic.startAllConsumers();
		}
	}

	@Override
	public void __destroy__(Object object) {
		if(Boolean.TRUE.equals(DependencyInjection.inject(StreamDistributedHelper.class).getIsEnable())) {
			Topic.stopAllConsumers();
		}

		Client client = __inject__(RequestHelper.class).getClient();
		if(client != null)
			client.close();
	}
	
	/**/
	
	
	
	/**/
	
	public static final Integer LEVEL = new Integer("0");
	
}
