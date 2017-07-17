package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Message extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;

		private String template;
		private List<Object> arguments;
		
		@Override
		public String toString() {
			return template+(CollectionHelper.getInstance().isEmpty(arguments) ? Constant.EMPTY_STRING : arguments);
		}
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Message> {
			
			String PARAMETER_SEPARATOR = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_COMA+Constant.CHARACTER_SPACE;
			String PARAMETER_NAME_VALUE_SEPARATOR = Constant.CHARACTER_EQUAL.toString();
			
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Message> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter() {
					super(Message.class);
				}
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					private static final String FORMAT = "%s";
					private static final String PARAMETER_FORMAT = "%s={}";
					
					public Default() {
						setAutomaticallyLogMessage(Boolean.FALSE);
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
									templateStringBuilder.append(parameter.toString());
								}
							}
						message.template = String.format(FORMAT, templateStringBuilder.toString());
						return message;
					}
					
				}
				
			}
			
		}
		
	}

}
