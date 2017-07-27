package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper.ToStringMapping;

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
	
	public <T extends java.lang.Throwable> void throw_(ConditionHelper.Condition condition,Class<T> causeClass){
		if(Boolean.TRUE.equals(condition.getValue()))
			throw_(new Throwable.Builder.Adapter.Default<T>(causeClass).addManyParameters(condition.getMessage()).execute());
	}
	
	public <T extends java.lang.Throwable> void throw_(ConditionHelper.Condition.Builder builder,Class<T> causeClass){
		throw_(builder.execute(),causeClass);
	}
	
	public void throwNotYetImplemented(){
		throw new RuntimeException("not yet implemented.");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Throwable implements Serializable  {
		private static final long serialVersionUID = 1L;
		
		protected String identifier;
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
						//throwable.setIdentifier(getIdentifier());
						StringBuilder messageBuilder = new StringBuilder();
						//messageBuilder.append(CollectionHelper.getInstance().concatenate(getMessages(), Constant.LINE_DELIMITER.toString()));
						StringHelper.ToStringMapping messageMapping = getMessageMapping();
						if(messageMapping!=null)
							addManyParameters(messageMapping.execute());
						messageBuilder.append(CollectionHelper.getInstance().concatenate(getParameters(), Constant.LINE_DELIMITER.toString()));
						throwable.setCause(ClassHelper.getInstance().instanciate(getCauseClass(), new Object[]{String.class,messageBuilder.toString()}));
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
	
}
