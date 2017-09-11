package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.MessageInterpolator;

import lombok.Getter;

public class ValidationHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ValidationHelper INSTANCE;
	
	public static ValidationHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ValidationHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public static interface Validate extends org.cyk.utility.common.Action<Object,Collection<String>> {
		
		Listener getListener();
		Validate setListener(Listener listener);
		
		Boolean getIsAutomaticallyThrowMessages();
		Validate setIsAutomaticallyThrowMessages(Boolean isAutomaticallyThrowMessages);
		
		Class<? extends java.lang.Throwable> getThrowableClass();
		Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass);
		
		LayerHelper.Type getLayerType();
		Validate setLayerType(LayerHelper.Type layerType);
		
		Collection<Class<?>> getGroups();
		Validate setGroups(Collection<Class<?>> groups);
		Validate addGroup(Class<?>...groups);
		Validate addGroups(Collection<Class<?>> groups);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.Action.Adapter.Default<Object,Collection<String>> implements Validate,Serializable {
			private static final long serialVersionUID = 1L;

			protected Listener listener;
			protected LayerHelper.Type layerType;
			protected Collection<Class<?>> groups;
			protected Boolean isAutomaticallyThrowMessages;
			protected Class<? extends java.lang.Throwable> throwableClass;
			
			@SuppressWarnings("unchecked")
			public Adapter(Object input) {
				super("validate", Object.class, input, (Class<Collection<String>>) ClassHelper.getInstance().getByName(Collection.class));
			}
			
			@Override
			public Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass) {
				return null;
			}
			
			@Override
			public Validate setIsAutomaticallyThrowMessages(Boolean isAutomaticallyThrowMessages) {
				return null;
			}
			
			@Override
			public Validate setListener(Listener listener) {
				return null;
			}
			
			@Override
			public Validate setLayerType(LayerHelper.Type layerType) {
				return null;
			}
			
			@Override
			public Validate setGroups(Collection<Class<?>> groups) {
				return null;
			}
			
			@Override
			public Validate addGroups(Collection<Class<?>> groups) {
				return null;
			}
			
			@Override
			public Validate addGroup(Class<?>...groups) {
				return null;
			}
			
			/**/
			
			public static class Default extends Validate.Adapter implements Serializable {

				private static final long serialVersionUID = 1L;

				public Default(Object input) {
					super(input);
				}
				
				@Override
				public Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass) {
					this.throwableClass = throwableClass;
					return this;
				}
				
				@Override
				public Validate setIsAutomaticallyThrowMessages(Boolean isAutomaticallyThrowMessages) {
					this.isAutomaticallyThrowMessages = isAutomaticallyThrowMessages;
					return this;
				}
				
				@Override
				public Validate setListener(Listener listener) {
					this.listener = listener;
					return this;
				}
				
				@Override
				public Validate setLayerType(LayerHelper.Type layerType) {
					this.layerType = layerType;
					return this;
				}
				
				@Override
				public Validate setGroups(Collection<Class<?>> groups) {
					this.groups = groups;
					return this;
				}
				
				@Override
				public Validate addGroups(Collection<Class<?>> groups) {
					if(this.groups == null)
						this.groups = new ArrayList<>();
					this.groups.addAll(groups);
					return this;
				}
				
				@Override
				public Validate addGroup(Class<?>...groups) {
					if(ArrayHelper.getInstance().isNotEmpty(groups))
						addGroups(Arrays.asList(groups));
					return this;
				}
				
				@Override
				protected Collection<String> __execute__() {
					Collection<String> messages = new ArrayList<>();
					//logTrace("Validating object {}", object);
					Object object = getInput();
					Collection<Class<?>> groups = getGroups();
					Listener listener = getListener();
					if(listener==null)
						listener = new Listener.Adapter.Default();
					LayerHelper.Type layerType = InstanceHelper.getInstance().getIfNotNullElseDefault(getLayerType(),LayerHelper.Type.DEFAULT);
					object = listener.getObjectToValidate(object, layerType);
					Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(MessageInterpolator.getInstance()).getValidator();  //getValidator();
					Set<ConstraintViolation<Object>> constraintViolationsModel = groups==null || groups.isEmpty()?validator.validate(object):validator.validate(object,groups.toArray(new Class<?>[]{}));
					if(!constraintViolationsModel.isEmpty())
			        	for(ConstraintViolation<?> violation : constraintViolationsModel){
			        		//logWarning("Constraint Violation : {}.{} : {} ",aObject.getClass().getName(),violation.getPropertyPath(),violation.getMessage());
			        		messages.add(formatMessage(object,violation));
			        		//messages.add(String.format("Constraint Violation : %s.%s : %s ",aObject.getClass().getName(),violation.getPropertyPath(),violation.getMessage()));
			        	}
					Boolean isAutomaticallyThrowMessages = getIsAutomaticallyThrowMessages();
					if(Boolean.TRUE.equals(isAutomaticallyThrowMessages)){
						Class<? extends java.lang.Throwable> throwableClass = InstanceHelper.getInstance().getIfNotNullElseDefault(getThrowableClass(),RuntimeException.class);
						ThrowableHelper.getInstance().throw_(messages, throwableClass);
					}
					return messages;
				}
				
				protected String formatMessage(Object anObject,ConstraintViolation<?> constraintViolation){
					Class<?> clazz = anObject.getClass();
					Field field = null;
					if(StringUtils.contains(constraintViolation.getPropertyPath().toString(), Constant.CHARACTER_DOT.toString())){
						for(String fieldName : StringUtils.split(constraintViolation.getPropertyPath().toString(), Constant.CHARACTER_DOT.toString())){
							field = FieldHelper.getInstance().get(clazz, fieldName);
							clazz = field.getType();
						}	
					}else{
						field = FieldHelper.getInstance().get(clazz, constraintViolation.getPropertyPath().toString());
					}
					
					return StringHelper.getInstance().getField(field.getName())+Constant.CHARACTER_SPACE+constraintViolation.getMessage();
				}
			}
		}
		
		/**/
		
	}
	
	/**/
	
	public interface Listener {
		
		Object getObjectToValidate(Object object,LayerHelper.Type layerType);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObjectToValidate(Object object,LayerHelper.Type layerType) {
				return null;
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object getObjectToValidate(Object object,LayerHelper.Type layerType) {
					return object;
				}
				
			}
			
		}
		
	}
	
}
