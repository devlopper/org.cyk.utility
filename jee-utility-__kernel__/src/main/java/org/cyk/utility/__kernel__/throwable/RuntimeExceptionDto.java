package org.cyk.utility.__kernel__.throwable;

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
public class RuntimeExceptionDto extends AbstractObject implements Serializable {

	private ArrayList<MessageDto> messages;
	
	public RuntimeExceptionDto(String messageIdentifier,String messageSummary,String messageDetails) {
		addMessages(new MessageDto(messageIdentifier,messageSummary,messageDetails));
	}
	
	public RuntimeExceptionDto(String messageIdentifier,String messageSummary) {
		this(messageIdentifier,messageSummary,null);
	}
	
	public RuntimeExceptionDto(String messageIdentifier) {
		this(messageIdentifier,null);
	}
	
	public ArrayList<MessageDto> getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = new ArrayList<>();
		return messages;
	}
	
	public RuntimeExceptionDto addMessages(Collection<MessageDto> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		getMessages(Boolean.TRUE).addAll(messages);
		return this;
	}
	
	public RuntimeExceptionDto addMessages(MessageDto...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return addMessages(CollectionHelper.listOf(messages));
	}
	
	@Override
	public String toString() {
		return messages == null ? super.toString() : messages.toString();
	}
}