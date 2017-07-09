package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Deprecated
public class LogMessage extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4892967981949897943L;

	private String template;
	private List<Object> arguments;
	
	public Object[] getArgumentsArray(){
		return arguments == null ? null : arguments.toArray();
	}
		
	/**/
	
	public static LogMessage build(String action,String subject,Object...parameters){
		return new LogMessage.Builder().set(action,subject,parameters).build();
	}
	
	/**/
	@Deprecated
	public static class Builder extends AbstractBuilder<LogMessage> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final String FORMAT = "%s %s. %s";
		private static final String PARAMETER_FORMAT = "%s {}";
		private static final String PARAMETER_SEPARATOR = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_COMA+Constant.CHARACTER_SPACE;
		
		private String action,subject;
		private List<Object> parameters;
		
		public Builder(Object action,Object subject) {
			super(LogMessage.class);
			this.action = action ==null ? null : action.toString();
			this.subject = subject == null ? null : subject instanceof Class ? ((Class<?>)subject).getSimpleName() : subject.toString();
		}
				
		public Builder() {
			this(Constant.EMPTY_STRING,Constant.EMPTY_STRING);
		}
		
		public Builder set(String action,String subject,Object...parameters) {
			setAction(action);
			setSubject(subject);
			addParameters(parameters);
			return this;
		}
		
		public Builder setAction(String action) {
			this.action = action;
			return this;
		}
		
		public Builder setSubject(String subject) {
			this.subject = subject;
			return this;
		}
		
		public Builder addParameters(Object...objects){
			if(objects!=null){
				if(parameters==null)
					parameters=new ArrayList<>();
				parameters.addAll(Arrays.asList(objects));
			}
			return this;
		}
		
		@Override
		public LogMessage build() {
			LogMessage logMessage = new LogMessage();
			Collection<String> parameterCollection = new ArrayList<>();
			if(parameters!=null)
				for(int i = 0; i< parameters.size();i=i+2){
					parameterCollection.add(String.format(PARAMETER_FORMAT, parameters.get(i)));
					if(logMessage.arguments==null)
						logMessage.arguments=new ArrayList<>();
					logMessage.arguments.add(parameters.get(i+1));
				}
			logMessage.template = String.format(FORMAT, action,subject,StringUtils.join(parameterCollection,PARAMETER_SEPARATOR));
			return logMessage;
		}
		
	}
}
