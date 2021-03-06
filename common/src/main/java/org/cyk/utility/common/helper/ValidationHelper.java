package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.ClassRepository;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.MessageInterpolator;

import lombok.Getter;

public class ValidationHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
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
	
	public Boolean isElectronicMailAddress(String string){
		try {
			new InternetAddress(string).validate();
			return Boolean.TRUE;
		} catch (AddressException e) {
			return Boolean.FALSE;
		}
	}
	
	public Validate getValidateBuilder(Object object,Constant.Action action,Boolean isThrowMessages){
		return new Validate.Adapter.Default(object).setAction(action).setIsThrowMessages(isThrowMessages);
	}
	
	public Validate getValidateBuilder(Object object,Constant.Action action){
		return getValidateBuilder(object, action, Boolean.FALSE);
	}
	
	public Validate getValidateBuilder(Object object,Boolean isThrowMessages){
		return getValidateBuilder(object, (Constant.Action)null, isThrowMessages);
	}
	
	public Validate getValidateBuilder(Object object){
		return getValidateBuilder(object, (Constant.Action)null);
	}
	
	public Collection<String> validate(Object object,Constant.Action action,Boolean isThrowMessages){
		return getValidateBuilder(object, action, isThrowMessages).execute();
	}
	
	public Collection<String> validate(Object object,Constant.Action action){
		return validate(object, action, Boolean.FALSE);
	}
	
	public Collection<String> validate(Object object,Boolean isThrowMessages){
		return validate(object, (Constant.Action)null, isThrowMessages);
	}
	
	public Collection<String> validate(Object object){
		return validate(object, (Constant.Action)null);
	}
	
	/**/
	
	public static interface Validate extends org.cyk.utility.common.Action<Object,Collection<String>> {
		
		Listener getListener();
		Validate setListener(Listener listener);
		
		Boolean getIsThrowMessages();
		Validate setIsThrowMessages(Boolean isThrowMessages);
		
		Boolean getIsFieldNameIncludedInMessage();
		Validate setIsFieldNameIncludedInMessage(Boolean isFieldNameIncludedInMessage);
		
		Class<? extends java.lang.Throwable> getThrowableClass();
		Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass);
		
		Constant.Action getAction();
		Validate setAction(Constant.Action action);
		
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
			protected Boolean isThrowMessages,isFieldNameIncludedInMessage;
			protected Class<? extends java.lang.Throwable> throwableClass;
			protected Constant.Action action;
			
			@SuppressWarnings("unchecked")
			public Adapter(Object input) {
				super("validate", Object.class, input, (Class<Collection<String>>) ClassHelper.getInstance().getByName(Collection.class));
			}
			
			@Override
			public Validate setIsFieldNameIncludedInMessage(Boolean isFieldNameIncludedInMessage) {
				return null;
			}
			
			@Override
			public Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass) {
				return null;
			}
			
			@Override
			public Validate setIsThrowMessages(Boolean isThrowMessages) {
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
			
			@Override
			public Validate setAction(Action action) {
				return null;
			}
			
			/**/
			
			public static class Default extends Validate.Adapter implements Serializable {

				private static final long serialVersionUID = 1L;

				public Default(Object input) {
					super(input);
				}
				
				@Override
				public Validate setAction(Action action) {
					this.action = action;
					return this;
				}
				
				@Override
				public Validate setIsFieldNameIncludedInMessage(Boolean isFieldNameIncludedInMessage) {
					this.isFieldNameIncludedInMessage = isFieldNameIncludedInMessage;
					return this;
				}
				
				@Override
				public Validate setThrowableClass(Class<? extends java.lang.Throwable> throwableClass) {
					this.throwableClass = throwableClass;
					return this;
				}
				
				@Override
				public Validate setIsThrowMessages(Boolean isThrowMessages) {
					this.isThrowMessages = isThrowMessages;
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
					//LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
					//loggingMessageBuilder.addManyParameters("validate");
					Object object = getInput();
					Collection<Class<?>> groups = getGroups();
					Listener listener = getListener();
					if(listener==null)
						listener = new Listener.Adapter.Default();
					LayerHelper.Type layerType = InstanceHelper.getInstance().getIfNotNullElseDefault(getLayerType(),LayerHelper.Type.DEFAULT);
					Boolean isFieldNameIncludedInMessage = InstanceHelper.getInstance().getIfNotNullElseDefault(getIsFieldNameIncludedInMessage(),Boolean.TRUE);
					String messageFormat = listener.getMessageFormat(layerType, isFieldNameIncludedInMessage);
					object = listener.getObjectToValidate(object, layerType);
					//1 - validate based on java constraints
					Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(MessageInterpolator.getInstance()).getValidator();  //getValidator();
					Set<ConstraintViolation<Object>> constraintViolationsModel = groups==null || groups.isEmpty()?validator.validate(object):validator.validate(object,groups.toArray(new Class<?>[]{}));
					if(!constraintViolationsModel.isEmpty()){
						List<ConstraintViolation<Object>> list = new ArrayList<>(constraintViolationsModel);
						Collection<Field> fields = ClassRepository.getInstance().get(object.getClass()).getFields(); 
						Collections.sort(list, new ConstraintViolationComparator<Object>(FieldHelper.getInstance().getNames(fields)));
						
			        	for(ConstraintViolation<?> violation : list){
			        		Class<?> clazz = object.getClass();
							Field field = null;
							if(StringUtils.contains(violation.getPropertyPath().toString(), Constant.CHARACTER_DOT.toString())){
								for(String fieldName : StringUtils.split(violation.getPropertyPath().toString(), Constant.CHARACTER_DOT.toString())){
									field = FieldHelper.getInstance().get(clazz, fieldName);
									clazz = field.getType();
								}	
							}else{
								field = FieldHelper.getInstance().get(clazz, violation.getPropertyPath().toString());
							}
							Collection<Object> arguments = new ArrayList<>();
							if(Boolean.TRUE.equals(isFieldNameIncludedInMessage))
								arguments.add(StringHelper.getInstance().getField(field.getName()));
							arguments.add(violation.getMessage());
							//addLoggingMessageBuilderNamedParameters("validmess",clazz+" ::: "+CollectionHelper.getInstance().concatenate(arguments, " | "));
							addMessage(messages, String.format(messageFormat, arguments.toArray()));
			        	}
					}
					//2 - validate based on cyk constraints
					Constant.Action action = getAction();
					if(Constant.Action.CREATE.equals(action)){
						if(ClassHelper.getInstance().isIdentified(object.getClass(), ClassHelper.Listener.IdentifierType.SYSTEM)){
							addMessage(messages, ConditionHelper.Condition.getBuilderNull(object,ClassHelper.getInstance().getIdentifierFieldName(object.getClass()
									, ClassHelper.Listener.IdentifierType.SYSTEM)).setIsNegateConditionValue(Boolean.TRUE));
						}
						
			            for(FieldHelper.Field index : FieldHelper.Field.get(object.getClass()))
			            	if(index.getConstraints().getIsNullable() != null && Boolean.FALSE.equals(index.getConstraints().getIsNullable())){
			            		addMessage(messages, ConditionHelper.Condition.getBuilderNull(object,index.getName()));	
			            	}
					}
					
					Boolean isThrowMessages = getIsThrowMessages();
					loggingMessageBuilder.addNamedParameters("object to validate",object);
					if(Boolean.TRUE.equals(isThrowMessages)){
						Class<? extends java.lang.Throwable> throwableClass = InstanceHelper.getInstance().getIfNotNullElseDefault(getThrowableClass(),ThrowableHelper.ThrowableMarkerCompileTime.class);
						loggingMessageBuilder.addNamedParameters("throwable class",throwableClass);
						ThrowableHelper.getInstance().throw_(messages, throwableClass);
					}else
						;
					return messages;
				}
				
				protected void addMessage(Collection<String> messages,String message){
					messages.add(message);
				}
				
				protected void addMessage(Collection<String> messages,ConditionHelper.Condition condition){
					if(Boolean.TRUE.equals(condition.getValue()))
						addMessage(messages, condition.getMessage());
				}
				
				protected void addMessage(Collection<String> messages,ConditionHelper.Condition.Builder conditionBuilder){
					addMessage(messages, conditionBuilder.execute());
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
		
		String getMessageFormat(LayerHelper.Type layerType,Boolean isFieldNameIncludedInMessage);
		String getMessageFormat(LayerHelper.Type layerType);
		
		void validate(Object object,Constant.Action action);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;

			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public static String MESSAGE_WITHOUT_FIELD_NAME_FORMAT = "%s";
				public static String MESSAGE_WITH_FIELD_NAME_FORMAT = "%s : "+MESSAGE_WITHOUT_FIELD_NAME_FORMAT;
				
				@Override
				public void validate(Object object, Constant.Action action) {
					if(Constant.Action.CREATE.equals(action)){
						if(ClassHelper.getInstance().isIdentified(object.getClass(), ClassHelper.Listener.IdentifierType.SYSTEM))
							throw__(ConditionHelper.Condition.getBuilderNull(object,ClassHelper.getInstance().getIdentifierFieldName(object.getClass()
								, ClassHelper.Listener.IdentifierType.SYSTEM)).setIsNegateConditionValue(Boolean.TRUE));
						
			            for(FieldHelper.Field index : FieldHelper.Field.get(object.getClass()))
			            	if(index.getConstraints().getIsNullable() != null && Boolean.FALSE.equals(index.getConstraints().getIsNullable()))
			            		throw__(ConditionHelper.Condition.getBuilderNull(object,index.getName()));	
			            
					}
				}
				
				@Override
				public Object getObjectToValidate(Object object,LayerHelper.Type layerType) {
					return object;
				}
				
				@Override
				public String getMessageFormat(LayerHelper.Type layerType,Boolean isFieldNameIncludedInMessage) {
					if(isFieldNameIncludedInMessage == null || Boolean.TRUE.equals(isFieldNameIncludedInMessage))
						return MESSAGE_WITH_FIELD_NAME_FORMAT;
					return MESSAGE_WITHOUT_FIELD_NAME_FORMAT;
				}
				
				@Override
				public String getMessageFormat(LayerHelper.Type layerType) {
					return getMessageFormat(layerType, !LayerHelper.Type.VIEW.equals(layerType));
				}
				
			}
			
			@Override
			public void validate(Object object, Action action) {}
			
			@Override
			public Object getObjectToValidate(Object object,LayerHelper.Type layerType) {
				return null;
			}
			
			@Override
			public String getMessageFormat(LayerHelper.Type layerType,Boolean isFieldNameIncludedInMessage) {
				return null;
			}
			
			@Override
			public String getMessageFormat(LayerHelper.Type layerType) {
				return null;
			}
			
			/**/
			
			
			
		}
		
	}
	
	/**/
	
	public static class ConstraintViolationComparator<T> implements Comparator<ConstraintViolation<T>>,Serializable {
		private static final long serialVersionUID = 1L;

		private List<String> fieldNames;
		
		public ConstraintViolationComparator(List<String> fieldNames) {
			this.fieldNames = fieldNames;
		}
		
		@Override
		public int compare(ConstraintViolation<T> o1, ConstraintViolation<T> o2) {
			if(fieldNames==null)
				return o1.getPropertyPath().toString().compareTo(o2.getPropertyPath().toString());
			return new Integer(fieldNames.indexOf(o1.getPropertyPath().toString())).compareTo(new Integer(fieldNames.indexOf(o2.getPropertyPath().toString())));
		}
		
		
		
	}
}
