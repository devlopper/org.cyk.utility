package org.cyk.utility.report.jasper.server;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientRequestFilter implements javax.ws.rs.client.ClientRequestFilter {

	@Inject private Configuration configuration;
	
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
    	if (JasperServerClient.isGetReportRequest(requestContext))
    		JasperServerClient.prepareGetReportRequest(requestContext,configuration);
    }

}