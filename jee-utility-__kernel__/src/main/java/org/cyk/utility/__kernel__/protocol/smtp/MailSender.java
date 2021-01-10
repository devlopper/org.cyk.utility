package org.cyk.utility.__kernel__.protocol.smtp;

import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MailSender {

	void send(Message message,Properties protocolProperties,Listener listener);
	
	default void send(Message message) {
		send(message, Properties.DEFAULT, null);
	}
	
	default void send(String subject,String body,Collection<String> receivers) {
		send(new Message().setSubject(subject).setBody(body).addReceiversFromStrings(receivers), Properties.DEFAULT, null);
	}
	
	default void send(String subject,String body,String...receivers) {
		send(new Message().setSubject(subject).setBody(body).addReceiversFromStrings(receivers), Properties.DEFAULT, null);
	}
	
	default void ping(Collection<String> receivers) {
		if(CollectionHelper.isEmpty(receivers))
			receivers = CollectionHelper.listOf(Properties.DEFAULT.getAuthenticationCredentials().getIdentifier());
		send("Ping","This is a ping.",receivers);
	}
	
	default void ping(String...receivers) {
		ping(ArrayHelper.isEmpty(receivers) ? null : CollectionHelper.listOf(receivers));
	}
	
	/**/
	
	static MailSender getInstance() {
		return Helper.getInstance(MailSender.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static interface Listener {
		
	}
}
