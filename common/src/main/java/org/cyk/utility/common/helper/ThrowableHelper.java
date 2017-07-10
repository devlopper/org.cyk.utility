package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class ThrowableHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static ThrowableHelper INSTANCE;
	
	public static ThrowableHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ThrowableHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public java.lang.Throwable getInstanceOf(java.lang.Throwable throwable,Class<?> aClass){
		java.lang.Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	public java.lang.Throwable getInstanceOf(java.lang.Throwable throwable,Class<?> aClass,Class<?>...classes){
		for(Class<?> index : ArrayUtils.add(classes, aClass)){
			java.lang.Throwable instance = getInstanceOf(throwable, index);
			if(instance!=null)
				return instance;
		}
		return null;
	}
	
	public java.lang.Throwable getFirstCause(java.lang.Throwable throwable){
		java.lang.Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
	
	public void throw_(Throwable throwable){
		if(throwable==null || throwable.getCause()==null)
			return;
		throw new RuntimeException(throwable.getCause());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Throwable implements Serializable  {
		private static final long serialVersionUID = 1L;
		
		protected String identifier;
		protected Set<String> messages;
		protected java.lang.Throwable cause;
		
		public static interface Builder<CAUSE extends java.lang.Throwable> extends org.cyk.utility.common.Builder<Throwable> {
			
			Class<CAUSE> getCauseClass();
			Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass);

			/*
			String getIdentifier();
			Builder setIdentifier(String identifier);
			
			Set<String> getMessages();
			Builder setMessages(Set<String> messages);
			Builder addMessages(Collection<String> messages);
			Builder addMessages(String...messages);
			*/
			@Getter @Setter 
			public static class Adapter<CAUSE extends java.lang.Throwable> extends org.cyk.utility.common.Builder.Adapter.Default<Throwable> implements Builder<CAUSE>,Serializable {
				private static final long serialVersionUID = 1L;
				/*
				protected String identifier;
				protected Set<String> messages;
				*/
				protected Class<CAUSE> causeClass;
				
				public Adapter(Class<CAUSE> causeClass) {
					super(Throwable.class);
					this.causeClass = causeClass;
				}
				
				@Override
				public Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass) {
					return null;
				}
				
				/*
				@Override
				public Builder setIdentifier(String identifier) {
					return null;
				}
				
				@Override
				public Builder setMessages(Set<String> messages) {
					return null;
				}
				
				@Override
				public Builder addMessages(Collection<String> messages) {
					return null;
				}
				
				@Override
				public Builder addMessages(String...messages) {
					return null;
				}
				*/
				public static class Default<CAUSE extends java.lang.Throwable> extends Builder.Adapter<CAUSE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<CAUSE> causeClass) {
						super(causeClass);
					}
					
					/*
					@Override
					public Builder setIdentifier(String identifier) {
						this.identifier = identifier;
						return this;
					}

					@Override
					public Builder setMessages(Set<String> messages) {
						this.messages = messages;
						return this;
					}
					
					@Override
					public Builder addMessages(Collection<String> messages) {
						this.messages = (Set<String>) new CollectionHelper().add(Set.class,this.messages, Boolean.TRUE, messages);
						return this;
					}
					
					@Override
					public Builder addMessages(String...messages) {
						this.messages = (Set<String>) new CollectionHelper().add(Set.class,this.messages, Boolean.TRUE, messages);
						return this;
					}	
					*/
					
					@Override
					public Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass) {
						this.causeClass = causeClass;
						return this;
					}
					
					@Override
					protected Throwable __execute__() {
						Throwable throwable = new Throwable();
						//throwable.setIdentifier(getIdentifier());
						StringBuilder messageBuilder = new StringBuilder();
						//messageBuilder.append(new CollectionHelper().concatenate(getMessages(), Constant.LINE_DELIMITER.toString()));
						messageBuilder.append(new CollectionHelper().concatenate(getParameters(), Constant.LINE_DELIMITER.toString()));
						throwable.setCause(new ClassHelper().instanciate(getCauseClass(), new Object[]{String.class,messageBuilder.toString()}));
						return throwable;
					}
				}
			}
		}
	
		@Override
		public String toString() {
			return cause.toString();
		}
	}
	
	/*@Getter @Setter @Accessors(chain=true)
	public static class Runtime extends java.lang.RuntimeException implements Serializable {

		private static final long serialVersionUID = 108726134018949961L;
		
		protected String identifier;
		protected Set<String> messages = new LinkedHashSet<>();
	    
	    public Runtime(String message) {
	        super(message);
	        messages.add(message);
	    }
	    
	    public Runtime(Set<String> messages) {
	        super(new StringHelper().concatenate(messages,"\r\n"));
	        this.messages.addAll(messages);
	    }

	}*/

}
