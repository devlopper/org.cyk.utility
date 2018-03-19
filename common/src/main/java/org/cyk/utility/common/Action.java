package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.NotificationHelper.Notification;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Action<INPUT,OUTPUT> {

	@AllArgsConstructor @Getter
	public enum Status{
		SUCCESS(Boolean.TRUE,"command.serve.succeed"),
		FAILURE(Boolean.TRUE,"command.serve.fail"),
		
		;
		
		private Boolean notify;
		private String notificationMessageId;
	};
	
	Collection<ActionListener> getActionListeners();
	Action<INPUT,OUTPUT> setActionListener(Collection<ActionListener> actionListeners);
	Action<INPUT,OUTPUT> addActionListener(Collection<ActionListener> actionListeners);
	Action<INPUT,OUTPUT> addActionListener(ActionListener...actionListeners);
	
	LoggingHelper.Message.Builder getLoggingMessageBuilder();
	Action<INPUT,OUTPUT> setLoggingMessageBuilder(LoggingHelper.Message.Builder loggingMessageBuilder);
	
	@Deprecated LogMessage.Builder getLogMessageBuilder();
	@Deprecated Action<INPUT,OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder);
	
	Status getStatus();
	Action<INPUT,OUTPUT> setStatus(Status status);
	
	Boolean getIsProcessableOnStatus();
	Action<INPUT,OUTPUT> setIsProcessableOnStatus(Boolean isProcessableOnStatus); 
	Action<INPUT,OUTPUT> processOnStatus(Status status);
	
	Boolean getIsNotifiable();
	Action<INPUT,OUTPUT> setIsNotifiable(Boolean isNotifiable); 
	Boolean getIsNotifiableOnStatusSuccess();
	Action<INPUT,OUTPUT> setIsNotifiableOnStatusSuccess(Boolean isNotifiableOnStatusSuccess);
	Boolean getIsNotifiableOnStatusFailure();
	Action<INPUT,OUTPUT> setIsNotifiableOnStatusFailure(Boolean isNotifiableOnStatusFailure);
	java.util.Map<Status,String> getStatusNotificationStringIdentifierMap();
	Action<INPUT,OUTPUT> setStatusNotificationStringIdentifierMap(java.util.Map<Status,String> statusNotificationStringIdentifierMap);
	Action<INPUT,OUTPUT> setStatusNotificationStringIdentifier(Status status,String notificationStringIdentifier);
	Action<INPUT,OUTPUT> notify_();
	
	Boolean getIsInputValidatable();
	Action<INPUT,OUTPUT> setIsInputValidatable(Boolean isInputValidatable);
	Action<INPUT,OUTPUT> validateInput();
	
	java.lang.Throwable getThrowable();
	Action<INPUT,OUTPUT> setThrowable(java.lang.Throwable throwable);
	
	Boolean getAutomaticallyLogMessage();
	Action<INPUT,OUTPUT> setAutomaticallyLogMessage(Boolean value);
	
	String getName();
	Action<INPUT,OUTPUT> setName(String name);
	
	Object getIdentifier();
	Action<INPUT,OUTPUT> setIdentifier(Object identifier);
	
	Class<INPUT> getInputClass();
	Action<INPUT,OUTPUT> setInputClass(Class<INPUT> inputClass);
	
	INPUT getInput();
	Action<INPUT,OUTPUT> setInput(INPUT input);
	
	Boolean getIsInputRequired();
	Action<INPUT,OUTPUT> setIsInputRequired(Boolean isImplemented);
	
	Boolean getIsImplemented();
	Action<INPUT,OUTPUT> setIsImplemented(Boolean isImplemented);
	
	Boolean getIsConfirmable();
	Action<INPUT,OUTPUT> setIsConfirmable(Boolean isConfirmable);
	
	Boolean getIsConfirmed();
	Action<INPUT,OUTPUT> setIsConfirmed(Boolean isConfirmed);
	
	Boolean getExecutable();
	Action<INPUT,OUTPUT> setExecutable(Boolean executable);
	
	Boolean getIsLoggable();
	Action<INPUT,OUTPUT> setIsLoggable(Boolean isLoggable);
	
	Boolean getIsProduceOutputOnly();
	Action<INPUT,OUTPUT> setIsProduceOutputOnly(Boolean isProcuceOutputOnly);
	
	OUTPUT execute();
	OUTPUT execute(INPUT input);
	
	OUTPUT getOutput();
	Action<INPUT,OUTPUT> setOutput(OUTPUT output);
	
	Class<OUTPUT> getOutputClass();
	Action<INPUT,OUTPUT> setOutputClass(Class<OUTPUT> outputClass);
	
	Locale getLocale();
	Action<INPUT, OUTPUT> setLocale(Locale locale);
	
	Action<INPUT,OUTPUT> getParent();
	Action<INPUT,OUTPUT> setParent(Action<INPUT,OUTPUT> parent);
	
	/**
	 * The following will be used to extend the attributes list 
	 **/
	
	org.cyk.utility.common.Properties getPropertiesMap();
	
	Properties getProperties();
	Action<INPUT, OUTPUT> setProperties(Properties properties);
	Action<INPUT, OUTPUT> setProperty(String name,Object value);
	Action<INPUT, OUTPUT> setManyProperties(Object...objects);
	Object getProperty(String name);
	Object getProperty(String name,Object nullValue);
	String getPropertyAsString(String name);
	<T extends Number> T getPropertyAsNumber(Class<T> numberClass,String name,T nullValue);
	<T extends Number> T getPropertyAsNumber(Class<T> numberClass,String name);
	Integer getPropertyAsInteger(String name,Integer nullValue);
	Integer getPropertyAsInteger(String name);
	java.lang.Boolean getPropertyAsBoolean(String name);
	
	Collection<Object> getParameters();
	Action<INPUT, OUTPUT> setParameters(Collection<Object> collection);
	Action<INPUT, OUTPUT> addParameters(Collection<Object> collection);
	Action<INPUT, OUTPUT> addParameters(Object[] objects);
	Action<INPUT, OUTPUT> addManyParameters(Object...objects);
	Action<INPUT, OUTPUT> addNamedParameters(Object...objects);
	
	<T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass,Integer index,T instance);
	<T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass,@SuppressWarnings("unchecked") T...instances);
	
	Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer index,Integer integer);
	Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer...integers);
	
	Action<INPUT, OUTPUT> addParameterArrayElementString(Integer index,String string);
	Action<INPUT, OUTPUT> addParameterArrayElementString(String...strings);
	Action<INPUT, OUTPUT> addParameterArrayElementStringIndexInstance(Object...objects);
	
	Action<INPUT, OUTPUT> clear();
	
	java.lang.String PROPERTY_NAME_INSTANT_INTERVAL = "INSTANT_INTERVAL";
	java.lang.String PROPERTY_NAME_INSTANT = "INSTANT";
	java.lang.String PROPERTY_NAME_INSTANT_1 = "INSTANT_1";
	java.lang.String PROPERTY_NAME_INSTANT_2 = "INSTANT_2";
	java.lang.String PROPERTY_NAME_FIELD_NAME = "fieldName";
	java.lang.String PROPERTY_NAME_IS_SET_EMPTY = "IS_SET_EMPTY";
	java.lang.String PROPERTY_NAME_EMPTY_SET_MEANS_ALL = "EMPTY_SET_MEANS_ALL";
	java.lang.String PROPERTY_NAME_QUERY = "QUERY";
	java.lang.String PROPERTY_NAME_NOT = "NOT";
	java.lang.String PROPERTY_NAME_INDEX = "INDEX";
	java.lang.String PROPERTY_NAME_SUFFIX = "SUFFIX";
	java.lang.String PROPERTY_NAME_SUFFIXES = "SUFFIXES";
	java.lang.String PROPERTY_NAME_BASE_CLASS_PACKAGE_NAME = "BASE_CLASS_PACKAGE_NAME";
	java.lang.String PROPERTY_NAME_PACKAGE_PREFIX = "PACKAGE_PREFIX";
	java.lang.String PROPERTY_NAME_FIELD_VALUE = "fieldValue";
	java.lang.String PROPERTY_NAME_PARAMETER_1 = "parameter1";
	java.lang.String PROPERTY_NAME_PARAMETER_2 = "parameter2";
	java.lang.String PROPERTY_NAME_STRING = "STRING";
	java.lang.String PROPERTY_NAME_LENGTH = "LENGTH";
	java.lang.String PROPERTY_NAME_LIKE = "LIKE";
	java.lang.String PROPERTY_NAME_YEAR="YEAR";
	java.lang.String PROPERTY_NAME_YEAR_1="YEAR1";
	java.lang.String PROPERTY_NAME_YEAR_2="YEAR2";
	java.lang.String PROPERTY_NAME_MONTHOFYEAR="MONTHOFYEAR";
	java.lang.String PROPERTY_NAME_MONTHOFYEAR_1="MONTHOFYEAR1";
	java.lang.String PROPERTY_NAME_MONTHOFYEAR_2="MONTHOFYEAR2";
	java.lang.String PROPERTY_NAME_DAYOFMONTH="DAYOFMONTH";
	java.lang.String PROPERTY_NAME_DAYOFMONTH_1="DAYOFMONTH1";
	java.lang.String PROPERTY_NAME_DAYOFMONTH_2="DAYOFMONTH2";
	java.lang.String PROPERTY_NAME_HOUROFDAY="HOUROFDAY";
	java.lang.String PROPERTY_NAME_HOUROFDAY_1="HOUROFDAY1";
	java.lang.String PROPERTY_NAME_HOUROFDAY_2="HOUROFDAY2";
	java.lang.String PROPERTY_NAME_MINUTEOFHOUR="MINUTEOFHOUR";
	java.lang.String PROPERTY_NAME_MINUTEOFHOUR_1="MINUTEOFHOUR1";
	java.lang.String PROPERTY_NAME_MINUTEOFHOUR_2="MINUTEOFHOUR2";
	java.lang.String PROPERTY_NAME_SECONDOFMINUTE="SECONDOFMINUTE";
	java.lang.String PROPERTY_NAME_MILLISOFSECOND="MILLISOFSECOND";
	java.lang.String PROPERTY_NAME_MEASURE_TYPE_UNIT="MEASURETYPEUNIT";
	java.lang.String PROPERTY_NAME_PLURAL="PLURAL";
	java.lang.String PROPERTY_NAME_GENDER="GENDER";
	java.lang.String PROPERTY_NAME_GENDER_ANY="GENDER_ANY";
	java.lang.String PROPERTY_NAME_WORD_ARTICLE_ALL="WORD_ARTICLE_ALL";
	java.lang.String PROPERTY_NAME_MAP="MAP";
	java.lang.String PROPERTY_NAME_CASE_SENSITIVE="CASE_SENSITIVE";
	java.lang.String PROPERTY_NAME_KEY="KEY";
	java.lang.String PROPERTY_NAME_VALUE="VALUE";
	java.lang.String PROPERTY_NAME_NULL_VALUE="NULL_VALUE";
	java.lang.String PROPERTY_NAME_SKIP_ZERO="SKIP_ZERO";
	java.lang.String PROPERTY_NAME_RUNNABLE="RUNNABLE";
	java.lang.String PROPERTY_NAME_EXPECTED="EXPECTED";
	java.lang.String PROPERTY_NAME_MESSAGE="MESSAGE";
	java.lang.String PROPERTY_NAME_TIME_PART="TIME_PART";
	java.lang.String PROPERTY_NAME_TIME_LENGTH="TIME_LENGTH";
	java.lang.String PROPERTY_NAME_IDENTIFIER="IDENTIFIER";
	java.lang.String PROPERTY_NAME_FROM="FROM";
	java.lang.String PROPERTY_NAME_TO="TO";
	java.lang.String PROPERTY_NAME_DURATION_IN_MILLISECOND="DURATION_IN_MILLISECOND";
	java.lang.String PROPERTY_NAME_PORTION_IN_MILLISECOND="PORTION_IN_MILLISECOND";
	java.lang.String PROPERTY_NAME="NAME";
	java.lang.String PROPERTY_NAME_TYPE="TYPE";
	java.lang.String PROPERTY_NAME_RENDER_TYPE="RENDER_TYPE";
	java.lang.String PROPERTY_NAME_SET="SET";
	java.lang.String PROPERTY_NAME_ICON_MAPPED="ICON_MAPPED";
	java.lang.String PROPERTY_NAME_SEPARATOR="SEPARATOR";
	java.lang.String PROPERTY_NAME_IS_ADD_COUNT_PREFIX = "IS_ADD_COUNT_PREFIX";
	java.lang.String PROPERTY_NAME_ACTION="ACTION";
	java.lang.String PROPERTY_NAME_PARENT="PARENT";
	java.lang.String PROPERTY_NAME_CHARACTER_SET = "CHARACTER_SET";
	java.lang.String PROPERTY_NAME_WIDTH = "WIDTH";
	java.lang.String PROPERTY_NAME_LEFT_PADDING = "LEFT_PADDING";
	java.lang.String PROPERTY_NAME_PERCENTAGE_SYMBOL = "PERCENTAGE_SYMBOL";
	java.lang.String PROPERTY_NAME_CLASS="CLASS";
	java.lang.String PROPERTY_NAME_PACKAGES="PACKAGES";
	java.lang.String PROPERTY_NAME_SYSTEM_IDENTIFIERS="SYSTEM_IDENTIFIERS";
	java.lang.String PROPERTY_NAME_PREFIX="PREFIX";
	java.lang.String PROPERTY_NAME_PREFIXES="PREFIXES";
	java.lang.String PROPERTY_NAME_LOGGER="LOGGER";
	java.lang.String PROPERTY_NAME_LEVEL="LEVEL";
	java.lang.String PROPERTY_NAME_MARKER="MARKER";
	java.lang.String PROPERTY_NAME_INSTANCE="INSTANCE";
	java.lang.String PROPERTY_NAME_FORMAT="FORMAT";
	java.lang.String PROPERTY_NAME_COLLECTION="COLLECTION";
	java.lang.String PROPERTY_NAME_SET_NAME="SET_NAME";
	java.lang.String PROPERTY_NAME_INSTANCES="INSTANCES";
	java.lang.String PROPERTY_NAME_STACK_TRACE_ELEMENT="STACK_TRACE_ELEMENT";
	
	/**/
	
	@Getter
	public static class Adapter<INPUT,OUTPUT> extends BeanListener.Adapter implements Action<INPUT,OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Locale locale;
		protected String name;
		protected Class<INPUT> inputClass;
		protected INPUT input;
		protected OUTPUT output;
		protected Class<OUTPUT> outputClass;
		@Deprecated protected LogMessage.Builder logMessageBuilder;
		protected LoggingHelper.Message.Builder loggingMessageBuilder;
		protected Boolean automaticallyLogMessage = Boolean.TRUE,isInputRequired=Boolean.TRUE,executable,isInputValidatable,isProcessableOnStatus,isConfirmable,isConfirmed
				,isNotifiable,isLoggable,isProduceOutputOnly,isNotifiableOnStatusFailure,isNotifiableOnStatusSuccess,isImplemented;
		protected Properties properties;
		protected Collection<Object> parameters;
		protected Action<INPUT,OUTPUT> parent;
		protected Status status;
		protected java.lang.Throwable throwable;
		protected Collection<ActionListener> actionListeners;
		protected Object identifier;
		protected java.util.Map<Status,String> statusNotificationStringIdentifierMap;
		
		@Deprecated
		public Adapter(String name,Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass,LogMessage.Builder logMessageBuilder) {
			setName(name);
			setInputClass(inputClass);
			setInput(input);
			setOutputClass(outputClass);
			setLogMessageBuilder(logMessageBuilder);
		}
		
		public Adapter(String name,Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass) {
			setName(name);
			setInputClass(inputClass);
			setInput(input);
			setOutputClass(outputClass);
		}
		
		@Override
		public Action<INPUT, OUTPUT> setStatusNotificationStringIdentifierMap(
				Map<org.cyk.utility.common.Action.Status, String> statusNotificationStringIdentifierMap) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsImplemented(Boolean isImplemented) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setStatusNotificationStringIdentifier(org.cyk.utility.common.Action.Status status,
				String notificationStringIdentifier) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsNotifiable(Boolean isNotifiable) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsNotifiableOnStatusFailure(Boolean isNotifiableOnStatusFailure) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsNotifiableOnStatusSuccess(Boolean isNotifiableOnStatusSuccess) {
			return null;
		}
		
		@Override
		public Object getProperty(String name, Object nullValue) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsLoggable(Boolean isLoggable) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsProduceOutputOnly(Boolean isProduceOutputOnly) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> notify_() {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIdentifier(Object identifier) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsConfirmed(Boolean isConfirmed) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setActionListener(Collection<org.cyk.utility.common.Action.ActionListener> actionListeners) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addActionListener(org.cyk.utility.common.Action.ActionListener... actionListeners) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addActionListener(Collection<org.cyk.utility.common.Action.ActionListener> actionListeners) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsConfirmable(Boolean isConfirmable) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setStatus(org.cyk.utility.common.Action.Status status) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsInputValidatable(Boolean isInputValidatable) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsProcessableOnStatus(Boolean isProcessableOnStatus) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> validateInput() {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> processOnStatus(Status status) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setThrowable(Throwable throwable) {
			return null;
		}
		
		@Override
		public String getPropertyAsString(String name) {
			return null;
		}
		
		@Override
		public Boolean getPropertyAsBoolean(String name) {
			return null;
		}
		
		@Override
		public <T extends Number> T getPropertyAsNumber(Class<T> numberClass, String name, T nullValue) {
			return null;
		}
		
		@Override
		public <T extends Number> T getPropertyAsNumber(Class<T> numberClass, String name) {
			return null;
		}
		
		@Override
		public Integer getPropertyAsInteger(String name, Integer nullValue) {
			return null;
		}
		
		@Override
		public Integer getPropertyAsInteger(String name) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setExecutable(Boolean executable) {
			return null;
		}
		
		@Override
		public <T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass, Integer index, T instance) {
			return null;
		}
		
		@Override
		public <T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass, @SuppressWarnings("unchecked") T... instances) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer index, Integer integer) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer... integers) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameterArrayElementString(Integer index, String string) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameterArrayElementString(String... strings) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameterArrayElementStringIndexInstance(Object... objects) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setAutomaticallyLogMessage(Boolean automaticallyLogMessage) {
			this.automaticallyLogMessage = automaticallyLogMessage;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setIsInputRequired(Boolean isInputRequired) {
			this.isInputRequired = isInputRequired;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setLocale(Locale locale) {
			this.locale = locale;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setName(String name) {
			this.name = name;
			return this;
		}
		
		public OUTPUT execute(){
			return null;
		}
		
		@Override
		public OUTPUT execute(INPUT input) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setInputClass(Class<INPUT> inputClass) {
			this.inputClass = inputClass;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setInput(INPUT input) {
			this.input = input;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setOutput(OUTPUT output) {
			this.output = output;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setOutputClass(Class<OUTPUT> outputClass) {
			this.outputClass = outputClass;
			return this;
		}
	
		@Override @Deprecated
		public Action<INPUT, OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder) {
			this.logMessageBuilder = logMessageBuilder;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setLoggingMessageBuilder(LoggingHelper.Message.Builder loggingMessageBuilder) {
			this.loggingMessageBuilder = loggingMessageBuilder;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setProperties(Properties properties) {
			this.properties = properties;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setProperty(String name, Object value) {
			if(name!=null && value!=null)
				getProperties().put(name, value);
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setManyProperties(Object... objects) {
			return null;
		}
		
		@Override
		public Object getProperty(String name) {
			return null;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setParent(Action<INPUT, OUTPUT> parent) {
			this.parent = parent;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setParameters(Collection<Object> parameters) {
			this.parameters = parameters;
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameters(Collection<Object> parameters) {
			if(parameters!=null && parameters.size()>0){
				if(this.parameters==null)
					this.parameters = new ArrayList<>();
				this.parameters.addAll(parameters);
			}
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addParameters(Object[] parameters) {
			if(parameters!=null && parameters.length>0){
				addParameters(Arrays.asList(parameters));
			}
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addManyParameters(Object...parameters) {
			if(parameters!=null && parameters.length>0){
				for(Object parameter : parameters)
					addParameters(Arrays.asList(parameter));
			}
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> addNamedParameters(Object...parameters) {
			Collection<Object> collection = new ArrayList<>();
			if(parameters!=null && parameters.length>0){
				for(int i = 0 ; i< parameters.length ; i = i + 2)
					collection.add(new Object[]{parameters[i],parameters[i+1]});
			}
			addParameters(collection);
			return this;
		}
		
		protected void addLoggingMessageBuilderNamedParameters(Object...parameters){
			LoggingHelper.Message.Builder builder = getLoggingMessageBuilder();
			if(builder==null)
				/*if(this instanceof LoggingHelper.Message.Builder)
					logTrace(StringUtils.join(parameters, LoggingHelper.Message.Builder.PARAMETER_SEPARATOR));
				else*/
					;
			else{
				builder.addNamedParameters(parameters);
			}
		}
		
		protected void addLoggingMessageBuilderParameters(Object...parameters){
			LoggingHelper.Message.Builder builder = getLoggingMessageBuilder();
			if(builder==null)
				/*if(this instanceof LoggingHelper.Message.Builder)
					logTrace(StringUtils.join(parameters, LoggingHelper.Message.Builder.PARAMETER_SEPARATOR));
				else*/
					return;
			//else
				builder.addParameters(parameters);
		}
		
		@Override
		public Action<INPUT, OUTPUT> clear() {
			return null;
		}
		
		/**/
		
		public static class Default<INPUT,OUTPUT> extends Action.Adapter<INPUT,OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final Map<org.cyk.utility.common.Action.Status, String> DEFAULT_STATUS_NOTIFICATION_STRING_IDENTIFIER_MAP
				= MapHelper.getInstance().getByKeyValue(Status.SUCCESS,"notification.operation.executed.success",Status.FAILURE,"notification.operation.executed.fail"); 
			
			@Deprecated
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass, LogMessage.Builder logMessageBuilder) {
				super(name,inputClass,input, outputClass, logMessageBuilder);
			}
			
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass) {
				super(name,inputClass,input, outputClass);
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsImplemented(Boolean isImplemented) {
				this.isImplemented = isImplemented;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setStatusNotificationStringIdentifierMap(
					Map<org.cyk.utility.common.Action.Status, String> statusNotificationStringIdentifierMap) {
				this.statusNotificationStringIdentifierMap = statusNotificationStringIdentifierMap;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setStatusNotificationStringIdentifier(org.cyk.utility.common.Action.Status status,String notificationStringIdentifier) {
				if(this.statusNotificationStringIdentifierMap == null)
					this.statusNotificationStringIdentifierMap = new HashMap<Action.Status, String>();
				this.statusNotificationStringIdentifierMap.put(status, notificationStringIdentifier);
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsLoggable(Boolean isLoggable) {
				this.isLoggable = isLoggable;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsNotifiableOnStatusFailure(Boolean isNotifiableOnStatusFailure) {
				this.isNotifiableOnStatusFailure = isNotifiableOnStatusFailure;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsNotifiableOnStatusSuccess(Boolean isNotifiableOnStatusSuccess) {
				this.isNotifiableOnStatusSuccess = isNotifiableOnStatusSuccess;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsProduceOutputOnly(Boolean isProcuceOutputOnly) {
				this.isProduceOutputOnly = isProcuceOutputOnly;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsNotifiable(Boolean isNotifiable) {
				this.isNotifiable = isNotifiable;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsConfirmable(Boolean isConfirmable) {
				this.isConfirmable = isConfirmable;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsConfirmed(Boolean isConfirmed) {
				this.isConfirmed = isConfirmed;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIdentifier(Object identifier) {
				this.identifier = identifier;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setStatus(org.cyk.utility.common.Action.Status status) {
				this.status = status;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsInputValidatable(Boolean isInputValidatable) {
				this.isInputValidatable = isInputValidatable;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> setIsProcessableOnStatus(Boolean isProcessableOnStatus) {
				this.isProcessableOnStatus = isProcessableOnStatus;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> notify_() {
				Notification notification = getNotification(getStatus());
				if(notification!=null) 
					NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.DEFAULT).execute();
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> validateInput() {
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> processOnStatus(org.cyk.utility.common.Action.Status status) {
				setStatus(status);
				return this;
			}
			
			protected NotificationHelper.Notification getNotification(org.cyk.utility.common.Action.Status status){
				String stringIdentifier = null;
				if(status!=null){
					Map<Status,String> map = InstanceHelper.getInstance().getIfNotNullElseDefault(getStatusNotificationStringIdentifierMap(),DEFAULT_STATUS_NOTIFICATION_STRING_IDENTIFIER_MAP);
					stringIdentifier = map.get(status);
					if(StringHelper.getInstance().isBlank(stringIdentifier))
						stringIdentifier = DEFAULT_STATUS_NOTIFICATION_STRING_IDENTIFIER_MAP.get(status);
				}
				if(Action.Status.SUCCESS.equals(status))
					return NotificationHelper.getInstance().getNotification(stringIdentifier);
				if(Action.Status.FAILURE.equals(status))
					return NotificationHelper.getInstance().getNotification(stringIdentifier
							,new Object[]{ThrowableHelper.getInstance().getInstanceOf(getThrowable(),ThrowableHelper.ThrowableMarkerRunTime.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class
									).getMessage()}).setSeverityType(NotificationHelper.SeverityType.ERROR);
				return null;
			}
			
			@Override
			public Object getProperty(String name) {
				return getProperty(name, null);
			}
			
			@Override
			public Object getProperty(String name, Object nullValue) {
				Properties properties = getProperties();
				Object value = properties == null ? null : properties.get(name);
				return value == null ? nullValue : value;
			}
			
			@Override
			public java.lang.String getPropertyAsString(String name) {
				return (java.lang.String) getProperty(name);
			}
			
			@Override
			public java.lang.Boolean getPropertyAsBoolean(String name) {
				return (Boolean) getProperty(name);
			}
			
			@Override
			public <T extends Number> T getPropertyAsNumber(Class<T> numberClass, String name,T nullValue) {
				return NumberHelper.getInstance().get(numberClass, getProperty(name),nullValue);
			}
			
			@Override
			public <T extends Number> T getPropertyAsNumber(Class<T> numberClass, String name) {
				return getPropertyAsNumber(numberClass, name,null);
			}
			
			@Override
			public Integer getPropertyAsInteger(String name,Integer nullValue) {
				return getPropertyAsNumber(Integer.class, name,nullValue);
			}
			
			@Override
			public Integer getPropertyAsInteger(String name) {
				return getPropertyAsInteger(name,null);
			}
			
			@Override
			public Action<INPUT, OUTPUT> setManyProperties(Object... objects) {
				if(objects!=null){
					for(int i = 0 ; i < objects.length ; i = i + 2)
						setProperty((String)objects[i], objects[i+1]);
				}
				return this;
			}
			
			protected Boolean isInputRequired(){
				return getIsInputRequired()==null || Boolean.TRUE.equals(getIsInputRequired());
			}
			
			@Override
			public Action<INPUT, OUTPUT> setExecutable(Boolean executable) {
				this.executable = executable;
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addParameterArrayElementStringIndexInstance(Object... objects) {
				if(objects!=null)
					for(int i = 0 ; i < objects.length ; i = i + 2){
						addParameterArrayElementString((Integer)objects[i],(String)objects[i+1]);
					}
				return this;
			}
			
			@Override
			public OUTPUT execute(INPUT input) {
				setInput(input);
				return execute();
			}
			
			@Override
			public final OUTPUT execute() {
				Boolean isInputValid = Boolean.TRUE;
				Long millisecond = null;
				if(isProduceOutputOnly == null || !isProduceOutputOnly){
					Boolean executable = getExecutable();
					if(executable!=null && Boolean.FALSE.equals(executable))
						return output;
					
					if(getIsConfirmed() == null){
						Boolean isConfirmable = getIsConfirmable();
						if(Boolean.TRUE.equals(isConfirmable)){
							confirm();
							ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME_CONFIRM,new MethodHelper.Method.Parameter(Action.class, this));
						}else
							setIsConfirmed(Boolean.TRUE);	
					}
					
					if(!Boolean.TRUE.equals(getIsConfirmed()))
						return output;
					
					Collection<ActionListener> actionListeners = getActionListeners();
					ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME_GET_INPUT,new MethodHelper.Method.Parameter(Action.class, this));
					if(Boolean.TRUE.equals(isInputRequired())){
						if(getInputClass() == null)
							throw new RuntimeException("Input class required");	
						if(getInput() == null)
							throw new RuntimeException("Input value required");
					}
					
					if(getOutputClass() == null)
						throw new RuntimeException("Output class required");
					
					LoggingHelper.Message.Builder loggingMessageBuilder = getLoggingMessageBuilder();
					if(loggingMessageBuilder==null && !(this instanceof LoggingHelper.Message.Builder))
						setLoggingMessageBuilder(loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default());
					
					//if(loggingMessageBuilder==null)
					//	setLogMessageBuilder(new LogMessage.Builder(name, getInput() ==null ? Constant.EMPTY_STRING : getInput().getClass().getSimpleName()));
					
					if(Boolean.TRUE.equals(isShowInputLogMessage(getInput())))
						addLoggingMessageBuilderNamedParameters("input",getInputLogMessage(getInput()));
					if(Boolean.TRUE.equals(isShowOuputClassLogMessage(getOutputClass())))
						addLoggingMessageBuilderNamedParameters("output class",getOutputClass().getSimpleName());
					millisecond = System.currentTimeMillis();
					
					isInputValid = Boolean.TRUE;
					if(Boolean.TRUE.equals(getIsInputValidatable())){
						try {
							validateInput();
							ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME_VALIDATE_INPUT,new MethodHelper.Method.Parameter(Action.class, this));
						} catch (Exception exception) {
							isInputValid = Boolean.FALSE;
							processOnFailure(exception);
						}
					}	
				}
				
				if(Boolean.TRUE.equals(isInputValid)){
					if(Boolean.TRUE.equals(getIsProcessableOnStatus())){
						try {
							ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME___EXECUTE_BEFORE__,new MethodHelper.Method.Parameter(Action.class, this));
							output = __execute__();
							ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME___EXECUTE__,new MethodHelper.Method.Parameter(Action.class, this));
							processOnSuccess();
						} catch (Exception exception) {
							processOnFailure(exception);
						}	
					}else{
						output = __execute__();
					}
				}
				
				if(isProduceOutputOnly == null || !isProduceOutputOnly){
					millisecond = System.currentTimeMillis() - millisecond;
					if( Boolean.TRUE.equals(getIsNotifiable()) 
							&& (Status.SUCCESS.equals(getStatus()) && Boolean.TRUE.equals(getIsNotifiableOnStatusSuccess()) 
									|| (Status.FAILURE.equals(getStatus()) && Boolean.TRUE.equals(getIsNotifiableOnStatusFailure())))
							)
						notify_();
					
					if(Boolean.TRUE.equals(isShowOutputLogMessage(output)))
						addLoggingMessageBuilderNamedParameters("output",getOutputLogMessage(output));
					//if(!(this instanceof TimeHelper.Stringifier.Duration.Adapter.Default)){
						addLoggingMessageBuilderNamedParameters("duration",millisecond);
					//}
					if(Boolean.TRUE.equals(getAutomaticallyLogMessage())){
						//LoggingHelper.Message message = loggingMessageBuilder.execute();
						logTrace(this);
					}
				}
				
				return output;
			}
			
			protected OUTPUT __execute__(){
				if(Boolean.TRUE.equals(getIsImplemented()))
					return output;
				throw new RuntimeException("Action <<"+name+">> with input class "+getInputClass()+" and output class "+getOutputClass()+" not yet implemented.");
				//throw new RuntimeException("Action <<"+name+">> with input class "+getInput().getClass()+" and output class "+getOutputClass()+" not yet implemented.");
			}
			
			protected void processOnSuccess(){
				processOnStatus(Status.SUCCESS);
				ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME_PROCESS_ON_STATUS,new MethodHelper.Method.Parameter(Action.class, this));
			}
			
			protected void processOnFailure(Throwable throwable){
				if(throwable!=null){
					System.out
							.println("Action.Adapter.Default.processOnFailure()********************************************************************************");
					throwable.printStackTrace();
				}
				setThrowable(throwable);
				processOnStatus(Status.FAILURE);
				ListenerHelper.getInstance().listen(actionListeners, ActionListener.METHOD_NAME_PROCESS_ON_STATUS,new MethodHelper.Method.Parameter(Action.class, this));
			}
			
			protected void confirm(){
				setIsConfirmed(Boolean.TRUE);
			}
			
			@Override
			public Action<INPUT, OUTPUT> setThrowable(Throwable throwable) {
				this.throwable = throwable;
				return this;
			}
			
			protected Boolean isShowOuputClassLogMessage(Class<OUTPUT> aClass){
				return Boolean.FALSE;
			}
			
			protected Boolean isShowInputLogMessage(INPUT input){
				return Boolean.TRUE;
			}
			
			protected Object getInputLogMessage(INPUT input){
				return input;
			}
			
			protected Object getOutputLogMessage(OUTPUT output){
				return output;
			}
			
			protected Boolean isShowOutputLogMessage(OUTPUT output){
				return Boolean.TRUE;
			}
			
			@Override
			public <T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass, Integer index, T instance) {
				addManyParameters(new ArrayHelper.Element<T>(index, instance));
				return this;
			}
			
			@Override
			public <T> Action<INPUT, OUTPUT> addParameterArrayElement(Class<T> aClass, @SuppressWarnings("unchecked") T... instances) {
				if(instances!=null){
					for(T instance : instances)
						addParameterArrayElement(aClass, CollectionHelper.getInstance().getSize(getParameters()), instance);
				}
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer index, Integer integer) {
				addParameterArrayElement(Integer.class, index, integer);
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addParameterArrayElementInteger(Integer... integers) {
				addParameterArrayElement(Integer.class, integers);
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addParameterArrayElementString(Integer index, String string) {
				addParameterArrayElement(String.class, index, string);
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addParameterArrayElementString(String... strings) {
				addParameterArrayElement(String.class, strings);
				return this;
			}
			
			@Override
			public Properties getProperties() {
				if(properties==null)
					properties = new Properties();
				return properties;
			}
			
			@Override
			public Action<INPUT, OUTPUT> clear() {
				this.output = null;
				this.parameters = null;
				this.properties = null;
				return this;
			}
		
			@Override
			public Action<INPUT, OUTPUT> addActionListener(Collection<ActionListener> actionListeners) {
				if(CollectionHelper.getInstance().isNotEmpty(actionListeners)){
					if(this.actionListeners==null)
						this.actionListeners = new ArrayList<>();
					this.actionListeners.addAll(actionListeners);	
				}
				return this;
			}
			
			@Override
			public Action<INPUT, OUTPUT> addActionListener(ActionListener...actionListeners) {
				if(ArrayHelper.getInstance().isNotEmpty(actionListeners))
					addActionListener(Arrays.asList(actionListeners));
				return this;
			}
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @AllArgsConstructor
	public static class Parameter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Object name;
		protected Object value;
		
		public Parameter(Object value) {
			this(null,value);
		}
		
		@Override
		public String toString() {
			return (name == null ? Constant.EMPTY_STRING : (name+Constant.CHARACTER_COLON.toString()))+value.toString();
		}
	}

	/**/
	
	public static interface ActionListener {

		void getInput(Action<Object,Object> action);
		
		void validateInput(Action<?,?> action);
		
		void __executeBefore__(Action<?,?> action);
		void __execute__(Action<?,?> action);
		
		void processOnStatus(Action<?,?> action);
		
		void confirm(Action<?,?> action);
		
		/*Boolean isNotifiable(Command command,ExecutionState state);
		
		String notifiy(Command command,Object parameter,ExecutionState state);
		*/
		/**/
		
		public static class Adapter extends AbstractBean implements ActionListener,Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public void getInput(Action<Object, Object> action) {}

			@Override
			public void validateInput(Action<?, ?> action) {}

			@Override
			public void __executeBefore__(Action<?, ?> action) {}
			
			@Override
			public void __execute__(Action<?, ?> action) {}

			@Override
			public void processOnStatus(Action<?, ?> action) {}
			
			@Override
			public void confirm(Action<?, ?> action) {}
			
		}
		
		String METHOD_NAME_GET_INPUT = "getInput";
		String METHOD_NAME_VALIDATE_INPUT = "validateInput";
		String METHOD_NAME___EXECUTE__ = "__execute__";
		String METHOD_NAME___EXECUTE_BEFORE__ = "__executeBefore__";
		String METHOD_NAME_PROCESS_ON_STATUS = "processOnStatus";
		String METHOD_NAME_CONFIRM = "confirm";
	}
	
	/**/
	/*
	public static interface GetValue<VALUE> extends Action<Object, VALUE> {
		
		public static class Adapter<VALUE> extends Action.Adapter.Default<Object, VALUE> implements GetValue<VALUE>,Serializable {
			private static final long serialVersionUID = -4167553207734748200L;

			public Adapter(Object input,Class<VALUE> valueClass) {
				super("get value", Object.class, input, valueClass);
			}
			
			public static class Default<VALUE> extends GetValue.Adapter<VALUE> implements Serializable {
				private static final long serialVersionUID = -4167553207734748200L;

				public Default(Object input,Class<VALUE> valueClass) {
					super(input,valueClass);
				}
			}
		}
		
		public static interface GetString extends GetValue<String> {
			
			public static class Adapter extends GetValue.Adapter.Default<String> implements GetValue<String>,Serializable {
				private static final long serialVersionUID = -4167553207734748200L;

				public Adapter(Object input) {
					super(input, String.class);
				}
				
				public static class Default extends GetString.Adapter implements Serializable {
					private static final long serialVersionUID = -4167553207734748200L;

					public Default(Object input) {
						super(input);
					}
					
					public Default() {
						this(null);
					}
				}
			}
		}
	}*/
	
}
