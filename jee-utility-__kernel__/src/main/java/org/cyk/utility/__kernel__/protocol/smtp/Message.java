package org.cyk.utility.__kernel__.protocol.smtp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Message extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String subject;
	private String body;
	private Attachment attachment;
	private Collection<Object> receivers;
	
	public Collection<Object> getReceivers(Boolean injectIfNull) {
		if(receivers == null && Boolean.TRUE.equals(injectIfNull))
			receivers = new ArrayList<>();
		return receivers;
	}
	
	public Message addReceivers(Collection<Object> receivers) {
		if(CollectionHelper.isEmpty(receivers))
			return this;
		getReceivers(Boolean.TRUE).addAll(receivers);
		return this;
	}
	
	public Message addReceiversFromStrings(Collection<String> receivers) {
		if(CollectionHelper.isEmpty(receivers))
			return this;
		getReceivers(Boolean.TRUE).addAll(receivers);
		return this;
	}
	
	public Message addReceiversFromStrings(String...receivers) {
		if(ArrayHelper.isEmpty(receivers))
			return this;
		addReceiversFromStrings(CollectionHelper.listOf(receivers));
		return this;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Attachment implements Serializable {
		private byte[] bytes;
		private String name;
		private String extension;
		private String mimeType;
	}
}