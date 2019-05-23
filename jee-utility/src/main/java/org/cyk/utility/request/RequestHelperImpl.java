package org.cyk.utility.request;

import java.io.Serializable;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class RequestHelperImpl extends AbstractHelper implements RequestHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private Client client;
	
	@Override
	public Client getClient() {
		return client;
	}
	
	@Override
	public Client getClient(Boolean injectIfNull) {
		Client client = getClient();
		if(client == null && Boolean.TRUE.equals(injectIfNull)) {
			client = ClientBuilder.newClient();
		}
		return client;
	}
	
	@Override
	public RequestHelper setClient(Client client) {
		this.client = client;
		return this;
	}

}
