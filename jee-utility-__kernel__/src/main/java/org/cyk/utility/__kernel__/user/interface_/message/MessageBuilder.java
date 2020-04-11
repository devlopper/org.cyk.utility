package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MessageBuilder {

	Collection<Message> build(Throwable throwable);
	
	public static abstract class AbstractImpl extends AbstractObject implements MessageBuilder,Serializable{
		
		@Override
		public Collection<Message> build(Throwable throwable) {
			if(throwable == null)
				throw new RuntimeException("throwable is required");
			Collection<Message> messages = new ArrayList<Message>();
			Throwable __throwable__ = ThrowableHelper.getInstanceOf(throwable, org.cyk.utility.__kernel__.throwable.RuntimeException.class);
			if(__throwable__ instanceof org.cyk.utility.__kernel__.throwable.RuntimeException) {
				processRuntimeException((org.cyk.utility.__kernel__.throwable.RuntimeException)__throwable__, messages);
			}else {
				messages.add(new Message().setSummary("Erreur lors de l'exécution de l'opération : "+(__throwable__ == null ? throwable : __throwable__).toString())
						.setSeverity(Severity.ERROR));
			}
			return messages;
		}
		
		private void processRuntimeException(org.cyk.utility.__kernel__.throwable.RuntimeException runtimeException,Collection<Message> messages) {
			if(CollectionHelper.isEmpty(runtimeException.getMessages())) {
				messages.add(new Message().setSummary(runtimeException.getMessage()).setDetails(runtimeException.getMessage()).setSeverity(Severity.ERROR));
			}else {
				runtimeException.getMessages().forEach(message -> {
					messages.add(new Message().setSummary(message.getSummary()).setDetails(message.getDetails()).setSeverity(Severity.ERROR));
				});		
			}			
		}
	}

	/**/
	
	static ProxyGetter getInstance() {
		return Helper.getInstance(ProxyGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}