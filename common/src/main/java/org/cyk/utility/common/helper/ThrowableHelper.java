package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper.ToStringMapping;
import org.cyk.utility.common.userinterface.ContentType;

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
		List<Class<?>> list = new ArrayList<>();
		list.add(aClass);
		if(ArrayHelper.getInstance().isNotEmpty(classes))
			list.addAll(Arrays.asList(classes));
		for(Class<?> index : list){
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
		//throw (RuntimeException)throwable.getCause();
		throw new RuntimeException(throwable.getCause());
	}
	
	public void throw_(String message){
		throw_(Arrays.asList(message), ThrowableMarkerCompileTime.class);
	}
	
	public <T extends java.lang.Throwable> void throw_(Collection<String> messages,Class<T> causeClass){
		if(CollectionHelper.getInstance().isNotEmpty(messages)){
			String message = new StringHelper.Concatenate.Adapter.Default(messages).setSeparator(Constant.LINE_DELIMITER).setIsAddCountPrefix(messages.size()>1).execute();
			throw_(new Throwable.Builder.Adapter.Default<T>(causeClass).addManyParameters(message).execute());	
		}
	}
	
	public <T extends java.lang.Throwable> void throw_(ConditionHelper.Condition condition,Class<T> causeClass){
		if(Boolean.TRUE.equals(condition.getValue()))
			throw_(new Throwable.Builder.Adapter.Default<T>(causeClass).setIdentifier(condition.getIdentifier()).addManyParameters(condition.getMessage()).execute());
	}
	
	public <T extends java.lang.Throwable> void throw_(ConditionHelper.Condition.Builder builder,Class<T> causeClass){
		throw_(builder.execute(),causeClass);
	}
	
	public void throwNotYetImplemented(String action){
		throw new RuntimeException((action == null ? Constant.EMPTY_STRING : (action+" ")) +"not yet implemented.");
	}
	
	public void throwNotYetImplemented(){
		throwNotYetImplemented(null);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Throwable implements Serializable  {
		private static final long serialVersionUID = 1L;
		
		protected Object identifier;
		protected Set<String> messages;
		protected java.lang.Throwable cause;
		
		public static interface Builder<CAUSE extends java.lang.Throwable> extends org.cyk.utility.common.Builder.NullableInput<Throwable> {
			
			Class<CAUSE> getCauseClass();
			Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass);

			StringHelper.ToStringMapping getMessageMapping();
			Builder<CAUSE> setMessageMapping(StringHelper.ToStringMapping messageMapping);
			
			@Getter @Setter 
			public static class Adapter<CAUSE extends java.lang.Throwable> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Throwable> implements Builder<CAUSE>,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Class<CAUSE> causeClass;
				protected StringHelper.ToStringMapping messageMapping;
				
				public Adapter(Class<CAUSE> causeClass) {
					super(Throwable.class);
					this.causeClass = causeClass;
				}
				
				@Override
				public Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass) {
					return null;
				}
				
				@Override
				public Builder<CAUSE> setMessageMapping(ToStringMapping stringMapping) {
					return null;
				}
				
				public static class Default<CAUSE extends java.lang.Throwable> extends Builder.Adapter<CAUSE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<CAUSE> causeClass) {
						super(causeClass);
					}
					
					@Override
					public Builder<CAUSE> setCauseClass(Class<CAUSE> causeClass) {
						this.causeClass = causeClass;
						return this;
					}
					
					@Override
					public Builder<CAUSE> setMessageMapping(ToStringMapping messageMapping) {
						this.messageMapping = messageMapping;
						return this;
					}
					
					@Override
					protected Throwable __execute__() {
						Throwable throwable = new Throwable();
						throwable.setIdentifier(getIdentifier());
						StringBuilder messageBuilder = new StringBuilder();
						//messageBuilder.append(CollectionHelper.getInstance().concatenate(getMessages(), Constant.LINE_DELIMITER.toString()));
						StringHelper.ToStringMapping messageMapping = getMessageMapping();
						if(messageMapping!=null)
							addManyParameters(messageMapping.execute());
						messageBuilder.append(CollectionHelper.getInstance().concatenate(getParameters(), Constant.LINE_DELIMITER.toString()));
						throwable.setCause(ClassHelper.getInstance().instanciate(getCauseClass(), new Object[]{String.class,messageBuilder.toString()}));
						if(throwable.getCause() instanceof ThrowableMarkerCompileTime) {
							((ThrowableMarkerCompileTime)throwable.getCause()).getFields().setIdentifier(getIdentifier());
						}else if(throwable.getCause() instanceof ThrowableMarkerRunTime) {
							((ThrowableMarkerRunTime)throwable.getCause()).getFields().setIdentifier(getIdentifier());
						}
						System.out.println("ThrowableHelper.Throwable.Builder.Adapter.Default.__execute__() : "+throwable.getIdentifier());
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
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class ThrowableMarkerCompileTime extends java.lang.Throwable {
		private static final long serialVersionUID = 1L;

		protected ThrowableMarkerFields fields = new ThrowableMarkerFields();
		
		public ThrowableMarkerCompileTime() {
			super();
		}

		public ThrowableMarkerCompileTime(String message, java.lang.Throwable cause, boolean enableSuppression,boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			getFields().addMessages(message);
		}

		public ThrowableMarkerCompileTime(String message, java.lang.Throwable cause) {
			super(message, cause);
		}

		public ThrowableMarkerCompileTime(String message) {
			this(new HashSet<>(Arrays.asList(message)));
		}

		public ThrowableMarkerCompileTime(Set<String> messages) {
	        super(StringUtils.join(messages,ContentType.TEXT.getNewLineMarker()));
	        getFields().addMessages(messages);
	    }
		
		public ThrowableMarkerCompileTime(java.lang.Throwable cause) {
			super(cause);
		}
		
		public ThrowableMarkerCompileTime setIdentifier(Object identifier) {
			getFields().setIdentifier(identifier);
			return this;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ThrowableMarkerRunTime extends java.lang.RuntimeException {
		private static final long serialVersionUID = 1L;

		protected ThrowableMarkerFields fields = new ThrowableMarkerFields();
		
		public ThrowableMarkerRunTime() {
			super();
		}

		public ThrowableMarkerRunTime(String message, java.lang.Throwable cause, boolean enableSuppression,boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			getFields().addMessages(message);
		}

		public ThrowableMarkerRunTime(String message, java.lang.Throwable cause) {
			super(message, cause);
		}

		public ThrowableMarkerRunTime(String message) {
			this(new HashSet<>(Arrays.asList(message)));
		}

		public ThrowableMarkerRunTime(Set<String> messages) {
	        super(StringUtils.join(messages,ContentType.TEXT.getNewLineMarker()));
	        getFields().addMessages(messages);
	    }
		
		public ThrowableMarkerRunTime(java.lang.Throwable cause) {
			super(cause);
		}
		
		public ThrowableMarkerRunTime setIdentifier(Object identifier) {
			getFields().setIdentifier(identifier);
			return this;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ThrowableMarkerFields implements Serializable {			
		private static final long serialVersionUID = 1L;
		
		protected Object identifier;
		protected Set<String> messages = new LinkedHashSet<>();
	
		public ThrowableMarkerFields addMessages(Collection<String> messages) {
			if(CollectionHelper.getInstance().isNotEmpty(messages))
				this.messages.addAll(messages);
			return this;
		}
		
		public ThrowableMarkerFields addMessages(String...messages) {
			if(ArrayHelper.getInstance().isNotEmpty(messages))
				addMessages(Arrays.asList(messages));
			return this;
		}
	}
}
