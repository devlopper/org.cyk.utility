package org.cyk.utility.network;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.sender.Receiver;
import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.network.protocol.ProtocolSimpleMailTransfer;

@Singleton
public class MailHelperImpl extends AbstractHelper implements MailHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MailHelper send(String title, String body,Collection<String> receivers,Boolean isExecuteAsynchronously) {
		SenderMail sender = __inject__(SenderMail.class);
		sender.setProtocol(__inject__(ProtocolDefaults.class).get(ProtocolSimpleMailTransfer.class));
		
		sender.setMessage(__inject__(Message.class).setTitle(title).setBody(body));
		for(String index : receivers)
			sender.addReceivers(__inject__(Receiver.class).setIdentifier(index));
		
		sender.setIsExecuteAsynchronously(isExecuteAsynchronously);
		sender.execute();
		
		return this;
	}

}
