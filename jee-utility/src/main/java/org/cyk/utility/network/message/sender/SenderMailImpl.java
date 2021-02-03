package org.cyk.utility.network.message.sender;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.protocol.Protocol;
import org.cyk.utility.network.protocol.ProtocolSimpleMailTransfer;

@Dependent
public class SenderMailImpl extends AbstractSenderImpl implements SenderMail,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends Protocol> __getProtocolClass__() {
		return ProtocolSimpleMailTransfer.class;
	}
	
	@Override
	protected void ________execute________(Protocol protocol,Message message) throws Exception {
		java.util.Properties properties = System.getProperties();
		if(protocol.getHost()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_HOST, protocol.getHost());
		if(protocol.getPort()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_PORT, protocol.getPort());
		if(protocol.getIsAuthenticationRequired()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_AUTHENTICATION, protocol.getIsAuthenticationRequired());
		if(protocol.getIsSecuredConnectionRequired()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_STARTTLS_ENABLE, protocol.getIsSecuredConnectionRequired());
	}
	
}
