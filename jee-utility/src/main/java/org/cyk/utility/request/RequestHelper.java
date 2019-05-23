package org.cyk.utility.request;

import javax.ws.rs.client.Client;

import org.cyk.utility.helper.Helper;

public interface RequestHelper extends Helper  {

	Client getClient();
	Client getClient(Boolean injectIfNull);
	RequestHelper setClient(Client client);
}
