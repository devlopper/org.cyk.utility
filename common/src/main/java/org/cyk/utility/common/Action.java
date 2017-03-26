package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;

public interface Action<INPUT,OUTPUT> {

	LogMessage.Builder getLogMessageBuilder();
	Action<INPUT,OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder);
	
	Boolean getAutomaticallyLogMessage();
	Action<INPUT,OUTPUT> setAutomaticallyLogMessage(Boolean value);
	
	String getName();
	Action<INPUT,OUTPUT> setName(String name);
	
	Class<INPUT> getInputClass();
	Action<INPUT,OUTPUT> setInputClass(Class<INPUT> inputClass);
	
	INPUT getInput();
	Action<INPUT,OUTPUT> setInput(INPUT input);
	
	OUTPUT execute();
	
	OUTPUT getOutput();
	Action<INPUT,OUTPUT> setOutput(OUTPUT output);
	
	Class<OUTPUT> getOutputClass();
	Action<INPUT,OUTPUT> setOutputClass(Class<OUTPUT> outputClass);
	
	Locale getLocale();
	Action<INPUT, OUTPUT> setLocale(Locale locale);
	
	
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
		protected LogMessage.Builder logMessageBuilder;
		protected Boolean automaticallyLogMessage = Boolean.TRUE;
		
		public Adapter(String name,Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass,LogMessage.Builder logMessageBuilder) {
			setName(name);
			setInputClass(inputClass);
			setInput(input);
			setOutputClass(outputClass);
			setLogMessageBuilder(logMessageBuilder);
		}
		
		@Override
		public Action<INPUT, OUTPUT> setAutomaticallyLogMessage(Boolean automaticallyLogMessage) {
			this.automaticallyLogMessage = automaticallyLogMessage;
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
	
		@Override
		public Action<INPUT, OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder) {
			this.logMessageBuilder = logMessageBuilder;
			return this;
		}
		
		
		/**/
		
		public static class Default<INPUT,OUTPUT> extends Action.Adapter<INPUT,OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass, LogMessage.Builder logMessageBuilder) {
				super(name,inputClass,input, outputClass, logMessageBuilder);
			}
						
			@Override
			public final OUTPUT execute() {
				if(getInputClass() == null)
					throw new RuntimeException("Input class required");
				if(getInput() == null)
					throw new RuntimeException("Input value required");
				if(getOutputClass() == null)
					throw new RuntimeException("Output class required");
				
				if(getLogMessageBuilder()==null)
					setLogMessageBuilder(new LogMessage.Builder(name, getInput().getClass().getSimpleName()));
				if(Boolean.TRUE.equals(isShowInputLogMessage(getInput())))
					addLogMessageBuilderParameters(getLogMessageBuilder(),"input",getInputLogMessage(getInput()));
				if(Boolean.TRUE.equals(isShowOuputClassLogMessage(getOutputClass())))
					addLogMessageBuilderParameters(getLogMessageBuilder(),"output class",getOutputClass().getSimpleName());
				output = __execute__();
				if(Boolean.TRUE.equals(isShowOutputLogMessage(output)))
					addLogMessageBuilderParameters(getLogMessageBuilder(),"output",getOutputLogMessage(output));
				if(Boolean.TRUE.equals(getAutomaticallyLogMessage()))
					logTrace(this);
				return output;
			}
			
			protected OUTPUT __execute__(){
				throw new RuntimeException("Action "+name+" of "+getInput().getClass()+" to "+getOutputClass()+" not yet implemented.");
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
		}
	}
}
