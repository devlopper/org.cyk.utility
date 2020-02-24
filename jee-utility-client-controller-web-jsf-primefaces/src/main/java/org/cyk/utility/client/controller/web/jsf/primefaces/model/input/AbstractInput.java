package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

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
	
	/**/
	
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
				input.outputLabel = OutputLabel.build(OutputLabel.FIELD_VALUE,"Input Text Label",OutputLabel.FIELD_FOR,input.getIdentifier());
			}
		}		
	}
}
