package org.cyk.utility.log.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractLogMessageBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<LogMessage> implements LogMessageBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setTemplateFormat("%s").setParameterSeparator(" , ");
		getProperties().setParameterFormat("%s="+getParameterFormatRightSide());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected LogMessage __execute__() {
		LogMessage message = new LogMessage();
		Collection<Object> parameters = (Collection<Object>) getProperties().getParameters();
		StringBuilder templateStringBuilder = new StringBuilder();
		if(__inject__(CollectionHelper.class).isNotEmpty(parameters)){
			Integer index = 0;
			for(Object parameter : parameters){
				if(templateStringBuilder.length()>0)
					templateStringBuilder.append(__inject__(ValueHelper.class).defaultToIfNull(getProperties().getParameterSeparator(),CharacterConstant.SPACE));
				if(parameter instanceof Object[]){
					templateStringBuilder.append(formatParameter((String)getProperties().getParameterFormat(),index++, ((Object[])parameter)[0]));
					if(message.getArguments()==null)
						message.setArguments(new ArrayList<>());
					message.getArguments().add(((Object[])parameter)[1]);
				}else{
					templateStringBuilder.append(parameter == null ? "null" : parameter.toString());
				}
			}
		}
		message.setTemplate(String.format((String)getProperties().getTemplateFormat(), templateStringBuilder.toString()));
		return message;
	}
	
	protected String formatParameter(String format,Integer index,Object...arguments){
		return String.format(format, ArrayUtils.add(arguments, index));
	}

	protected abstract String getParameterFormatRightSide();
	
	@Override
	public Log getParent() {
		return (Log) super.getParent();
	}
	
	@Override
	public LogMessageBuilder setTemplateFormat(String templateFormat) {
		getProperties().setTemplateFormat(templateFormat);
		return this;
	}
	
	@Override
	public String getTemplateFormat() {
		return (String) getProperties().getTemplateFormat();
	}
	
	@Override
	public String getParameterFormat() {
		return (String) getProperties().getParameterFormat();
	}
	
	@Override
	public LogMessageBuilder setParameterFormat(String parameterFormat) {
		getProperties().setParameterFormat(parameterFormat);
		return this;
	}
	
	@Override
	public String getParameterSeparator() {
		return (String) getProperties().getParameterSeparator();
	}
	
	@Override
	public LogMessageBuilder setParameterSeparator(String parameterSeparator) {
		getProperties().setParameterSeparator(parameterSeparator);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getParameters() {
		return (Collection<Object>) getProperties().getParameters();
	}
	
	@Override
	public LogMessageBuilder setParameters(Collection<Object> parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public LogMessageBuilder addParameter(Object parameter) {
		if(parameter != null){
			Collection<Object> parameters = getParameters();
			if(parameters == null)
				setParameters(parameters = new ArrayList<>());
			parameters.add(parameter);
		}
		return this;
	}
	
	@Override
	public LogMessageBuilder addParameter(Object key, Object value) {
		addParameter(new Object[]{key,value});
		return this;
	}
}
