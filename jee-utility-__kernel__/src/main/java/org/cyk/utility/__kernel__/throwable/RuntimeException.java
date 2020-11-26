package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RuntimeException extends java.lang.RuntimeException implements Serializable {

	private Collection<Message> messages;
	
	public RuntimeException() {
		super();
	}

	public RuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeException(String message) {
		super(message);
	}

	public RuntimeException(Throwable cause) {
		super(cause);
	}

	public Collection<Message> getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = new ArrayList<>();
		return messages;
	}
	
	public RuntimeException addMessages(Collection<Message> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		getMessages(Boolean.TRUE).addAll(messages);
		return this;
	}
	
	public RuntimeException addMessages(Message...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return addMessages(CollectionHelper.listOf(messages));
	}
	
	public String computeMessage() {
		if(CollectionHelper.isEmpty(messages))
			return getMessage();
		Integer index = 1;
		Collection<String> strings = new ArrayList<>();
		for(Message message : messages) {
			strings.add((messages.size() == 1 ? ConstantEmpty.STRING : (index+" - "))+message.getSummary());
			index = index + 1;
		}
		return StringUtils.join(strings,"\n\r");
	}
	
	@Override
	public String toString() {
		if(CollectionHelper.isEmpty(messages))
			return super.toString();
		return super.toString()+" : "+messages;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {

		private String message;
		private ArrayList<Message.Dto> messages;
		
		public Dto(String messageIdentifier,String messageSummary,String messageDetails) {
			addMessages(new Message.Dto(messageIdentifier,messageSummary,messageDetails));
		}
		
		public Dto(String messageIdentifier,String messageSummary) {
			this(messageIdentifier,messageSummary,null);
		}
		
		public Dto(String messageIdentifier) {
			this(messageIdentifier,null);
		}
		
		public ArrayList<Message.Dto> getMessages(Boolean injectIfNull) {
			if(messages == null && Boolean.TRUE.equals(injectIfNull))
				messages = new ArrayList<>();
			return messages;
		}
		
		public Dto addMessages(Collection<Message.Dto> messages) {
			if(CollectionHelper.isEmpty(messages))
				return this;
			getMessages(Boolean.TRUE).addAll(messages);
			return this;
		}
		
		public Dto addMessages(Message.Dto...messages) {
			if(ArrayHelper.isEmpty(messages))
				return this;
			return addMessages(CollectionHelper.listOf(messages));
		}
		
		@Override
		public String toString() {
			return message+" : "+messages;
		}
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, RuntimeException> {
			
			@Override
			protected void __listenGetSourceAfter__(RuntimeException destination, Dto source) {
				super.__listenGetSourceAfter__(destination, source);
				source.setMessage(destination.getMessage());
			}
		}
	}
}