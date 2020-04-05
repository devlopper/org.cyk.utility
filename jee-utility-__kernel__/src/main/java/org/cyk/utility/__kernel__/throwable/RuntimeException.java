package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.representation.AbstractMapperSourceDestinationImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @ToString(callSuper = false)
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
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false)
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
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends AbstractMapperSourceDestinationImpl<Dto, RuntimeException> {
			
			@Override
			protected void __listenGetSourceAfter__(RuntimeException destination, Dto source) {
				super.__listenGetSourceAfter__(destination, source);
				source.setMessage(destination.getMessage());
			}			
		}
	}
}