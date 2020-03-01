package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractInputOutput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.message.Message;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputLabel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInput<VALUE> extends AbstractInputOutput<VALUE> implements Serializable {

	protected Boolean required;
	protected String requiredMessage;
	protected String placeholder;
	
	protected OutputLabel outputLabel;
	protected Message message;
	protected Object converter;
	
	/**/
	
	public AbstractInput<VALUE> writeValueToObjectField() {
		if(object == null || field == null) {
			LogHelper.logWarning("value has not been written because object or field might be null", getClass());
			return this;
		}
		FieldHelper.write(object, field, value);
		return this;
	}
	
	@Override
	public Boolean getIsInput() {
		return Boolean.TRUE;
	}
	
	/**/
	
	public static final String FIELD_REQUIRED = "required";
	public static final String FIELD_REQUIRED_MESSAGE = "requiredMessage";
	public static final String FIELD_PLACEHOLDER = "placeholder";
	
	/**/
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInput<?>> extends AbstractInputOutput.AbstractConfiguratorImpl<INPUT> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			if(Boolean.TRUE.equals(input.required)) {
				if(input.message == null)
					input.message = Message.build(Map.of(Message.ConfiguratorImpl.FIELD_INPUT,input));
				if(StringHelper.isBlank(input.requiredMessage)) {
					input.requiredMessage = "valeur obligatoire";
				}
			}
			
			if(input.outputLabel == null) {
				String outputLabelValue = null;
				if(input.field == null) {
					outputLabelValue = "Input Text Label";
				}else {
					outputLabelValue = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(input.field.getName()),null,null,Case.FIRST_CHARACTER_UPPER);
				}
				input.outputLabel = OutputLabel.build(OutputLabel.FIELD_VALUE,outputLabelValue,OutputLabel.FIELD_FOR,input.getIdentifier());
			}
		}		
	}
}
