package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;

public interface Action<INPUT,OUTPUT> {

	LogMessage.Builder getLogMessageBuilder();
	Action<INPUT,OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder);
	
	String getName();
	Action<INPUT,OUTPUT> setName(String name);
	
	Class<INPUT> getInputClass();
	Action<INPUT,OUTPUT> setInputClass(Class<INPUT> inputClass);
	
	INPUT getInput();
	Action<INPUT,OUTPUT> setInput(INPUT input);
	
	OUTPUT execute();
	
	Class<OUTPUT> getOutputClass();
	Action<INPUT,OUTPUT> setOutputClass(Class<OUTPUT> outputClass);
	
	Locale getLocale();
	Action<INPUT, OUTPUT> setLocale(Locale locale);
	
	java.lang.String getText(java.lang.String identifier);
	
	/**/
	
	@Getter
	public static class Adapter<INPUT,OUTPUT> extends BeanAdapter implements Action<INPUT,OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Locale locale;
		protected String name;
		protected Class<INPUT> inputClass;
		protected INPUT input;
		protected Class<OUTPUT> outputClass;
		protected LogMessage.Builder logMessageBuilder;
		
		public Adapter(String name,Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass,LogMessage.Builder logMessageBuilder) {
			setName(name);
			setInputClass(inputClass);
			setInput(input);
			setOutputClass(outputClass);
			setLogMessageBuilder(logMessageBuilder);
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
		public Action<INPUT, OUTPUT> setOutputClass(Class<OUTPUT> outputClass) {
			this.outputClass = outputClass;
			return this;
		}
	
		@Override
		public Action<INPUT, OUTPUT> setLogMessageBuilder(LogMessage.Builder logMessageBuilder) {
			this.logMessageBuilder = logMessageBuilder;
			return this;
		}
		
		@Override
		public String getText(String identifier) {
			return null;
		}
		
		/**/
		
		public static class Default<INPUT,OUTPUT> extends Action.Adapter<INPUT,OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(String name,Class<INPUT> inputClass,INPUT input, Class<OUTPUT> outputClass, LogMessage.Builder logMessageBuilder) {
				super(name,inputClass,input, outputClass, logMessageBuilder);
			}
						
			@Override
			public OUTPUT execute() {
				if(getInput() == null)
					return null;
				if(getLogMessageBuilder()==null)
					setLogMessageBuilder(new LogMessage.Builder(name, getInput().getClass().getSimpleName()));
				addLogMessageBuilderParameters(getLogMessageBuilder(),"output class",getOutputClass().getSimpleName());
				OUTPUT output = __execute__();
				addLogMessageBuilderParameters(getLogMessageBuilder(),"output",getOutputLogMessage(output));
				return output;
			}
			
			protected OUTPUT __execute__(){
				throw new RuntimeException("Action "+name+" of "+getInput().getClass()+" to "+getOutputClass()+" not yet implemented.");
			}
			
			protected Object getOutputLogMessage(OUTPUT output){
				return output;
			}
		}
	}
}
