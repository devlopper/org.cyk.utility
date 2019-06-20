package org.cyk.utility.network;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.network.protocol.ProtocolSimpleMailTransfer;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.stream.distributed.ProducerBuilder;
import org.cyk.utility.stream.distributed.Topic;

@ApplicationScoped
public class MailHelperImpl extends AbstractHelper implements MailHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MailHelper send(String title, String body,Collection<Object> receiversIdentifiers,Boolean isExecuteAsynchronously) {
		SenderMail sender = __inject__(SenderMail.class);
		sender.setProtocol(__inject__(ProtocolDefaults.class).get(ProtocolSimpleMailTransfer.class));
		
		sender.setMessage(__inject__(Message.class).setTitle(title).setBody(body).addReceiversByIdentifiers(receiversIdentifiers));
		
		sender.setIsExecuteAsynchronously(isExecuteAsynchronously);
		sender.execute();
		
		return this;
	}
	
	@Override
	public MailHelper produce(String title, String body, Collection<String> receiversIdentifiers) {
		Message mail = __inject__(Message.class);
		mail.setTitle(title).setBody(body);
		if(receiversIdentifiers!=null)
			receiversIdentifiers.forEach(new Consumer<String>() {
				@Override
				public void accept(String receiversIdentifier) {
					mail.addReceiversByIdentifiers(receiversIdentifier);
				}
			});
		
		Producer producer = __inject__(ProducerBuilder.class).setTopic(Topic.MAIL).execute().getOutput();
		producer.setMessage(UUID.randomUUID().toString(),mail);		
		producer.execute();
		return this;
	}

	@Override
	public MailHelper produce(String title, String body, String... receiversIdentifiers) {
		if(__inject__(ArrayHelper.class).isNotEmpty(receiversIdentifiers))
			produce(title, body, __inject__(CollectionHelper.class).instanciate(receiversIdentifiers));
		return this;
	}
}
