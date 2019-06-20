package org.cyk.utility.network.message;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;

@Dependent
public class MessageImpl extends AbstractObject implements Message , Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String body;
	private Receivers receivers;
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public Message setTitle(String title) {
		this.title = title;
		return this;
	}
	
	@Override
	public String getBody() {
		return body;
	}
	
	@Override
	public Message setBody(String body) {
		this.body = body;
		return this;
	}
	
	@Override
	public Message setReceivers(Receivers receivers) {
		this.receivers = receivers;
		return this;
	}
	
	@Override
	public Receivers getReceivers() {
		return receivers;
	}
	
	@Override
	public Receivers getReceivers(Boolean injectIfNull) {
		return (Receivers) __getInjectIfNull__(FIELD_RECEIVERS, injectIfNull);
	}
	
	@Override
	public Message addReceivers(Collection<Receiver> receivers) {
		getReceivers(Boolean.TRUE).add(receivers);
		return this;
	}
	
	@Override
	public Message addReceivers(Receiver... receivers) {
		getReceivers(Boolean.TRUE).add(receivers);
		return this;
	}
	
	@Override
	public Message addReceiversByIdentifiers(Collection<Object> receiversIdentifiers) {
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(receiversIdentifiers))){
			for(Object index : receiversIdentifiers)
				addReceivers(__inject__(Receiver.class).setIdentifier(index));
		}
		return this;
	}
	
	@Override
	public Message addReceiversByIdentifiers(Object... receiversIdentifiers) {
		if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isNotEmpty(receiversIdentifiers))){
			for(Object index : receiversIdentifiers)
				addReceivers(__inject__(Receiver.class).setIdentifier(index));
		}
		return this;
	}
	
	/**/
	
	public static final String FIELD_RECEIVERS = "receivers";
}
