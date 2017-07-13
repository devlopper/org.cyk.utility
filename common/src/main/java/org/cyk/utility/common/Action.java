package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.LoggingHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Action<INPUT,OUTPUT> {

	LoggingHelper.Message.Builder getLoggingMessageBuilder();
	Action<INPUT,OUTPUT> setLoggingMessageBuilder(LoggingHelper.Message.Builder loggingMessageBuilder);
	
	@Deprecated LogMessage.Builder getLogMessageBuilder();
	@Deprecated Action<INPUT,OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder);
	
	Boolean getAutomaticallyLogMessage();
	Action<INPUT,OUTPUT> setAutomaticallyLogMessage(Boolean value);
	
	String getName();
	Action<INPUT,OUTPUT> setName(String name);
	
	Class<INPUT> getInputClass();
	Action<INPUT,OUTPUT> setInputClass(Class<INPUT> inputClass);
	
	INPUT getInput();
	Action<INPUT,OUTPUT> setInput(INPUT input);
	
	Boolean getIsInputRequired();
	Action<INPUT,OUTPUT> setIsInputRequired(Boolean isInputRequired);
	
	Boolean getExecutable();
	Action<INPUT,OUTPUT> setExecutable(Boolean executable);
	
	OUTPUT execute();
	
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
	
	Properties getProperties();
	Action<INPUT, OUTPUT> setProperties(Properties properties);
	Action<INPUT, OUTPUT> setProperty(String name,Object value);
	Action<INPUT, OUTPUT> setManyProperties(Object...objects);
	
	Collection<Object> getParameters();
	Action<INPUT, OUTPUT> setParameters(Collection<Object> collection);
	Action<INPUT, OUTPUT> addParameters(Collection<Object> collection);
	Action<INPUT, OUTPUT> addParameters(Object[] objects);
	Action<INPUT, OUTPUT> addManyParameters(Object...objects);
	Action<INPUT, OUTPUT> addNamedParameters(Object...objects);
	
	Action<INPUT, OUTPUT> clear();
	
	/**/
	
	@Getter
	public static class Adapter<INPUT,OUTPUT> extends BeanAdapter implements Action<INPUT,OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Locale locale;
		protected String name;
		protected Class<INPUT> inputClass;
		protected INPUT input;
		protected OUTPUT output;
		protected Class<OUTPUT> outputClass;
		@Deprecated protected LogMessage.Builder logMessageBuilder;
		protected LoggingHelper.Message.Builder loggingMessageBuilder;
		protected Boolean automaticallyLogMessage = Boolean.TRUE,isInputRequired=Boolean.TRUE,executable;
		protected Properties properties;
		protected Collection<Object> parameters;
		protected Action<INPUT,OUTPUT> parent;
		
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
		public Action<INPUT, OUTPUT> setExecutable(Boolean executable) {
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
			getProperties().put(name, value);
			return this;
		}
		
		@Override
		public Action<INPUT, OUTPUT> setManyProperties(Object... objects) {
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
			
			@Deprecated
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass, LogMessage.Builder logMessageBuilder) {
				super(name,inputClass,input, outputClass, logMessageBuilder);
			}
			
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass) {
				super(name,inputClass,input, outputClass);
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
			public final OUTPUT execute() {
				Boolean executable = getExecutable();
				if(executable!=null && Boolean.FALSE.equals(executable))
					return output;
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
				output = __execute__();
				if(Boolean.TRUE.equals(isShowOutputLogMessage(output)))
					addLoggingMessageBuilderNamedParameters("output",getOutputLogMessage(output));
				if(Boolean.TRUE.equals(getAutomaticallyLogMessage())){
					//LoggingHelper.Message message = loggingMessageBuilder.execute();
					logTrace(this);
				}
				return output;
			}
			
			protected OUTPUT __execute__(){
				throw new RuntimeException("Action <<"+name+">> with input class "+getInput().getClass()+" and output class "+getOutputClass()+" not yet implemented.");
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
			public Properties getProperties() {
				if(properties==null)
					properties = new Properties();
				return properties;
			}
			
			@Override
			public Action<INPUT, OUTPUT> clear() {
				this.output = null;
				this.parameters = null;
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
}
