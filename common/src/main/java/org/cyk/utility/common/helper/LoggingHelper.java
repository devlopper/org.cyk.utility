package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.LoggingHelper.Message.Builder;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class LoggingHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static LoggingHelper INSTANCE;
	
	public static LoggingHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new LoggingHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Logger<?, ?, ?> getLogger(){
		return ClassHelper.getInstance().instanciateOne(Logger.Adapter.Default.DEFAULT_CLASS);
	}
	
	public String getMarkerName(Object...names){
		return StringHelper.getInstance().concatenate(names, Constant.CHARACTER_UNDESCORE);
	}
	
	/**/
	
	/**/
	
	//@Getter @Setter @Accessors(chain=true) //TODO getting symbol not found when comoiling with maven
	public static class Message extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;

		private String template;
		private List<Object> arguments;
		
		public String getTemplate() {
			return template;
		}
		
		public List<Object> getArguments() {
			return arguments;
		}
		
		@Override
		public String toString() {
			return template+(CollectionHelper.getInstance().isEmpty(arguments) ? Constant.EMPTY_STRING : arguments);
		}
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Message> {
			
			String PARAMETER_SEPARATOR = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_COMA+Constant.CHARACTER_SPACE;
			String PARAMETER_NAME_VALUE_SEPARATOR = Constant.CHARACTER_EQUAL.toString();
			
			Builder setLogger(Logger<?,?,?> logger);
			Logger<?,?,?> getLogger();
			
			@Override Builder addManyParameters(Object... objects);
			
			@Getter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Message> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Logger<?,?,?> logger;
				
				public Adapter() {
					super(Message.class);
				}
				
				@Override
				public Builder setLogger(Logger<?, ?, ?> logger) {
					return null;
				}
				
				@Override
				public Builder addManyParameters(Object... parameters) {
					return (Builder) super.addManyParameters(parameters);
				}
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					private static final String FORMAT = "%s";
					private static final String PARAMETER_FORMAT = "%s={}";
					
					public Default(String message) {
						if(!StringHelper.getInstance().isBlank(message))
							addManyParameters(message);
						setAutomaticallyLogMessage(Boolean.FALSE);
					}
					
					public Default() {
						this(null);
					}
					
					@Override
					public Builder setLogger(Logger<?, ?, ?> logger) {
						this.logger = logger;
						return this;
					}
					
					@Override
					protected Message __execute__() {
						Message message = new Message();
						Collection<Object> parameters = getParameters();
						StringBuilder templateStringBuilder = new StringBuilder();
						if(parameters!=null)
							for(Object parameter : parameters){
								if(templateStringBuilder.length()>0)
									templateStringBuilder.append(PARAMETER_SEPARATOR);
								if(parameter instanceof Object[]){
									templateStringBuilder.append(String.format(PARAMETER_FORMAT, ((Object[])parameter)[0]));
									if(message.arguments==null)
										message.arguments = new ArrayList<>();
									message.arguments.add(((Object[])parameter)[1]);
								}else{
									templateStringBuilder.append(parameter == null ? "null" : parameter.toString());
								}
							}
						message.template = String.format(FORMAT, templateStringBuilder.toString());
						return message;
					}
					
				}
				
			}
		}
	}

	/**/
	
	public static interface Logger<LOGGER,LEVEL,MARKER> extends Action<Message, Void> {
		
		public static enum Level {ALL,TRACE,DEBUG,INFO,WARN,ERROR,FATAL,OFF}
		
		Message.Builder getMessageBuilder();
		Message.Builder getMessageBuilder(Boolean createIfNull);
		Logger<LOGGER,LEVEL,MARKER> setClass(Class<?> aClass);
		Logger<LOGGER,LEVEL,MARKER> setLogger(LOGGER logger);
		Logger<LOGGER,LEVEL,MARKER> setLevel(LEVEL level);
		Logger<LOGGER,LEVEL,MARKER> setLevel(Level level);
		Logger<LOGGER,LEVEL,MARKER> setMarker(MARKER marker);
		Logger<LOGGER,LEVEL,MARKER> setMarker(String marker);
		Logger<LOGGER,LEVEL,MARKER> setStackTraceElement(StackTraceElement stackTraceElement);
		
		MARKER createMarker(String marker);
		MARKER createMarker(String marker,MARKER parent);
		Logger<LOGGER,LEVEL,MARKER> setMarkerParent(MARKER marker,MARKER parent);
		
		LOGGER instanciateLogger(Class<?> aClass);
		
		Void execute(Class<?> aClass,Level level,String marker);
		
		@Getter @Setter
		public static class Adapter<LOGGER,LEVEL,MARKER> extends Action.Adapter.Default<Message, Void> implements Logger<LOGGER,LEVEL,MARKER>,Serializable {
			private static final long serialVersionUID = 1L;

			protected Message.Builder messageBuilder;
			
			public Adapter(Message input) {
				super("log", Message.class, input, Void.class);
			}
			
			@Override
			public Builder getMessageBuilder(Boolean createIfNull) {
				return null;
			}
				
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setClass(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setLogger(LOGGER logger) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setLevel(LEVEL level) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setMarker(MARKER marker) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setLevel(Level level) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setMarker(String marker) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setStackTraceElement(StackTraceElement stackTraceElement) {
				return null;
			}
			
			@Override
			public LOGGER instanciateLogger(Class<?> aClass) {
				return null;
			}
			
			@Override
			public MARKER createMarker(String marker) {
				return null;
			}
			
			@Override
			public MARKER createMarker(String marker, MARKER parent) {
				return null;
			}
			
			@Override
			public Logger<LOGGER, LEVEL, MARKER> setMarkerParent(MARKER marker,MARKER parent) {
				return null;
			}
			
			@Override
			public Void execute(Class<?> aClass, org.cyk.utility.common.helper.LoggingHelper.Logger.Level level,String marker) {
				return null;
			}
			
			public static class Default<LOGGER,LEVEL,MARKER> extends Logger.Adapter<LOGGER,LEVEL,MARKER> implements Serializable {
				private static final long serialVersionUID = 1L;

				public static Class<? extends Logger<?,?,?>> DEFAULT_CLASS = Log4j.Adapter.Default.class;
				
				public Default(Message input) {
					super(input);
					setIsInputRequired(Boolean.FALSE);
				}
				
				public Default(String message) {
					super(new Message.Builder.Adapter.Default(message).execute());
				}
				
				@Override
				public Builder getMessageBuilder(Boolean createIfNull) {
					if(messageBuilder==null)
						if(Boolean.TRUE.equals(createIfNull)){
							messageBuilder = new Message.Builder.Adapter.Default();
							messageBuilder.setLogger(this);
						}
					return messageBuilder;
				}
				
				@Override
				public Logger<LOGGER, LEVEL, MARKER> setClass(Class<?> aClass) {
					setProperty(PROPERTY_NAME_CLASS, aClass);
					return this;
				}
				
				@Override
				public Logger<LOGGER, LEVEL, MARKER> setLogger(LOGGER logger) {
					setProperty(PROPERTY_NAME_LOGGER, logger);
					return this;
				}
				
				@Override
				public Logger<LOGGER, LEVEL, MARKER> setLevel(LEVEL level) {
					setProperty(PROPERTY_NAME_LEVEL, level);
					return this;
				}
				
				@Override
				public Logger<LOGGER, LEVEL, MARKER> setMarker(MARKER marker) {
					setProperty(PROPERTY_NAME_MARKER, marker);
					return this;
				}
				
				@Override
				public Logger<LOGGER, LEVEL, MARKER> setStackTraceElement(StackTraceElement stackTraceElement) {
					setProperty(PROPERTY_NAME_STACK_TRACE_ELEMENT, stackTraceElement);
					return this;
				}
				
				@Override
				public MARKER createMarker(String marker, MARKER parent) {
					MARKER m = createMarker(marker);
					setMarkerParent(m, parent);
					return m;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				protected Void __execute__() {
					Message message = getInput();
					if(message==null){
						Message.Builder messageBuilder = getMessageBuilder();
						if(messageBuilder!=null)
							message = messageBuilder.execute();
					}
					Class<?> aClass = (Class<?>) getProperty(PROPERTY_NAME_CLASS);
					LOGGER logger = (LOGGER) getProperty(PROPERTY_NAME_LOGGER);
					if(logger==null)
						logger = instanciateLogger(aClass);
					LEVEL level = (LEVEL) getProperty(PROPERTY_NAME_LEVEL);
					MARKER marker = (MARKER) getProperty(PROPERTY_NAME_MARKER);
					StackTraceElement stackTraceElement = (StackTraceElement) getProperty(PROPERTY_NAME_STACK_TRACE_ELEMENT);
					
					if(stackTraceElement==null){
						//stackTraceElement = StackTraceHelper.getInstance().getAt(8);
					}
					
					if(stackTraceElement!=null){
						Class<?> stackTraceElementClass = ClassHelper.getInstance().getByName(stackTraceElement.getClassName());
						MARKER packageMarker = createMarker((aClass == null ? stackTraceElementClass.getPackage() : aClass.getPackage()).getName());
						MARKER classMarker = createMarker(aClass.getName(),packageMarker);
						classMarker = createMarker(aClass.getSimpleName(),classMarker);
						for(String conainerClassName : ClassHelper.getInstance().getContainerNames(aClass)){
							classMarker = createMarker(conainerClassName,classMarker);
						}
						//MARKER classMarker = createMarker(/*aClass == null ?*/ aClass.getName() /*: aClass.getSimpleName()*/,packageMarker);
						MARKER lineNumberMarker = createMarker(String.valueOf(stackTraceElement.getLineNumber()),classMarker);
						MARKER methodMarker = createMarker(stackTraceElement.getMethodName(),lineNumberMarker);
						if(marker!=null)
							setMarkerParent(marker,methodMarker);
					}
					
					__log__(message, aClass, logger, level, marker);
					return Constant.VOID;
				}
				
				@Override
				public Void execute(Class<?> aClass, org.cyk.utility.common.helper.LoggingHelper.Logger.Level level,String marker) {
					return setClass(aClass).setLevel(level).setMarker(marker).execute();
				}
				
				protected void __log__(Message message,Class<?> aClass,LOGGER logger,LEVEL level,MARKER marker){
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
			}
		}
		
		/**/
		
		public static interface Log4j extends Logger<org.apache.logging.log4j.Logger,org.apache.logging.log4j.Level,org.apache.logging.log4j.Marker> {
			
			public static class Adapter extends Logger.Adapter.Default<org.apache.logging.log4j.Logger,org.apache.logging.log4j.Level,org.apache.logging.log4j.Marker> implements Log4j,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Message input) {
					super(input);
				}
				
				public Adapter(String message) {
					super(message);
				}
				
				public static class Default extends Log4j.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Message input) {
						super(input);
					}
					
					public Default(String message) {
						super(message);
					}
					
					public Default() {
						super((Message)null);
					}
					
					@Override
					public org.apache.logging.log4j.Logger instanciateLogger(Class<?> aClass) {
						return org.apache.logging.log4j.LogManager.getLogger(aClass);
					}
					
					@Override
					public Logger<org.apache.logging.log4j.Logger, org.apache.logging.log4j.Level, Marker> setMarkerParent(org.apache.logging.log4j.Marker marker, org.apache.logging.log4j.Marker parent) {
						marker.setParents(parent);
						return this;
					}
					
					@Override
					public org.apache.logging.log4j.Marker createMarker(String marker) {
						return MarkerManager.getMarker(marker);
					}
					
					@Override
					public Logger<org.apache.logging.log4j.Logger, org.apache.logging.log4j.Level, org.apache.logging.log4j.Marker> setLevel(Level level) {
						switch(level){
						case ALL: return setLevel(org.apache.logging.log4j.Level.ALL);
						case TRACE: return setLevel(org.apache.logging.log4j.Level.TRACE);
						case DEBUG: return setLevel(org.apache.logging.log4j.Level.DEBUG);
						case INFO: return setLevel(org.apache.logging.log4j.Level.INFO);
						case WARN: return setLevel(org.apache.logging.log4j.Level.WARN);
						case ERROR: return setLevel(org.apache.logging.log4j.Level.ERROR);
						case FATAL: return setLevel(org.apache.logging.log4j.Level.FATAL);
						case OFF: return setLevel(org.apache.logging.log4j.Level.OFF);
						}
						return this;
					}
					
					@Override
					public Logger<org.apache.logging.log4j.Logger, org.apache.logging.log4j.Level, org.apache.logging.log4j.Marker> setMarker(String marker) {
						setMarker(StringHelper.getInstance().isBlank(marker) ? null : MarkerManager.getMarker(marker));
						return this;
					}
					
					@Override
					protected void __log__(Message message,Class<?> aClass, org.apache.logging.log4j.Logger logger, org.apache.logging.log4j.Level level,org.apache.logging.log4j.Marker marker) {
						if(marker==null)
							logger.log(level, message.getTemplate(),ArrayHelper.getInstance().get(Object.class, message.getArguments()));
						else
							logger.log(level, marker, message.getTemplate(),ArrayHelper.getInstance().get(Object.class, message.getArguments()));
					}
				
					/**/
					
					public static void log(Message.Builder messageBuilder,Class<?> aClass, org.apache.logging.log4j.Level level,org.apache.logging.log4j.Marker marker,StackTraceElement stackTraceElement){
						new LoggingHelper.Logger.Log4j.Adapter.Default(messageBuilder.execute()).setClass(aClass).setLevel(level).setMarker(marker)
							.setStackTraceElement(stackTraceElement).execute();
					}
					
					public static void log(Message.Builder messageBuilder,Class<?> aClass, Level level,String marker,StackTraceElement stackTraceElement){
						new LoggingHelper.Logger.Log4j.Adapter.Default(messageBuilder.execute()).setClass(aClass).setLevel(level).setMarker(marker)
							.setStackTraceElement(stackTraceElement).execute();
					}
					
					public static void log(Message.Builder messageBuilder,Class<?> aClass, Level level,String marker){
						log(messageBuilder, aClass, level, marker,null);
					}
					
					public static void log(String message,Class<?> aClass, org.apache.logging.log4j.Level level,org.apache.logging.log4j.Marker marker){
						log(new Message.Builder.Adapter.Default(message), aClass, level, marker,null);
					}
					
					public static void log(String message,Class<?> aClass, Level level,String marker,StackTraceElement stackTraceElement){
						log(new Message.Builder.Adapter.Default(message), aClass, level, marker,stackTraceElement);
					}
					
					public static void log(String message,Class<?> aClass, Level level,String marker){
						log(new Message.Builder.Adapter.Default(message), aClass, level, marker,StackTraceHelper.getInstance().getAt(3));
					}
				}
			}
			
		}
		
	}

	/**/
	
	public static interface Run {
		
		String BEFORE = "BEFORE";
		String AFTER = "AFTER";
		String RUNNING = "RUNNING...";
		String DONE = "DONE.";
		
		StackTraceElement getStackTraceElement();
		Run setStackTraceElement(StackTraceElement stackTraceElement);
		Class<?> getClazz();
		Run setClazz(Class<?> clazz);
		void log(Boolean before);
		void addParameters(LoggingHelper.Message.Builder builder,Boolean before);
		String getMarker(Boolean before);
		
		String getName();
		
		Object __execute__();
		
		Object execute();
		
		@Getter
		public static class Adapter implements Run,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected StackTraceElement stackTraceElement;
			protected Class<?> clazz;
			
			@Override
			public void log(Boolean before) {}
			
			@Override
			public String getName() {
				return null;
			}
			
			@Override
			public Run setStackTraceElement(StackTraceElement stackTraceElement) {
				return null;
			}
			
			@Override
			public Run setClazz(Class<?> clazz) {
				return null;
			}
			
			@Override
			public String getMarker(Boolean before) {
				return null;
			}
			
			@Override
			public Object execute() {
				return null;
			}
			
			@Override
			public Object __execute__() {
				return null;
			}
			
			@Override
			public void addParameters(LoggingHelper.Message.Builder builder,Boolean before) {}
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Long millisecond;
				
				public Default(StackTraceElement stackTraceElement,Class<?> aClass) {
					setStackTraceElement(stackTraceElement);
					setClazz(aClass);
				}
				
				public Default(StackTraceElement stackTraceElement) {
					this(stackTraceElement,ClassHelper.getInstance().getByName(stackTraceElement.getClassName()));
				}
				
				@Override
				public String getName() {
					StackTraceElement stackTraceElement = getStackTraceElement();
					return stackTraceElement == null ? Constant.EMPTY_STRING : stackTraceElement.getMethodName();
				}
				
				@Override
				public Run setStackTraceElement(StackTraceElement stackTraceElement) {
					this.stackTraceElement = stackTraceElement;
					return this;
				}
				
				@Override
				public Run setClazz(Class<?> clazz) {
					this.clazz=clazz;
					return this;
				}
				
				@Override
				public void addParameters(org.cyk.utility.common.helper.LoggingHelper.Message.Builder builder, Boolean before) {
					builder.addManyParameters((getName()+Constant.CHARACTER_SPACE) + (before ? RUNNING : DONE));
				}
				
				@Override
				public String getMarker(Boolean before) {
					return LoggingHelper.getInstance().getMarkerName(Boolean.TRUE.equals(before) ? BEFORE : AFTER);
				}
				
				@Override
				public void log(Boolean before) {
					LoggingHelper.Logger<?, ?, ?> logger = LoggingHelper.getInstance().getLogger();
					StackTraceElement stackTraceElement = getStackTraceElement();
					//if(stackTraceElement!=null)
					//	logger.getMessageBuilder(Boolean.TRUE).addNamedParameters("action",stackTraceElement.getMethodName());
					addParameters(logger.getMessageBuilder(Boolean.TRUE),before); 
					if(!Boolean.TRUE.equals(before)){
						String duration = new TimeHelper.Stringifier.Duration.Adapter.Default(System.currentTimeMillis()-millisecond).execute();
						logger.getMessageBuilder().addNamedParameters("duration",InstanceHelper.getInstance().getIfNotNullElseDefault(duration,"0"));
					}
					logger.setStackTraceElement(stackTraceElement)
						.execute(getClazz(),Boolean.TRUE.equals(before) ? LoggingHelper.Logger.Level.TRACE : LoggingHelper.Logger.Level.DEBUG,getMarker(before));
				}
				
				@Override
				public Object __execute__() {
					ThrowableHelper.getInstance().throwNotYetImplemented();
				    return null;
				}
				
				@Override
				public Object execute() {
					log(Boolean.TRUE);
					millisecond = System.currentTimeMillis();
					Object result = __execute__();
					log(Boolean.FALSE);
				    return result;
				}
				
			}
			
		}
		
	}
	
}
