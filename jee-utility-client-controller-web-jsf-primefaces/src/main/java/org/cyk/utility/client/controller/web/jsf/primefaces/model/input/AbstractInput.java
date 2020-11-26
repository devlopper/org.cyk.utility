package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractInputOutput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.message.Message;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputLabel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInput<VALUE> extends AbstractInputOutput<VALUE> implements Serializable {

	protected Boolean disabled,readOnly;
	protected Boolean required;
	protected String requiredMessage,converterMessage,validatorMessage;
	protected String placeholder;
	
	protected OutputLabel outputLabel;
	protected Message message;
	protected Object converter;
	
	/**/
	
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).validate(context, component, value);
	}
	
	//@SuppressWarnings("unchecked")
	public AbstractInput<VALUE> writeValueToObjectField() {
		if(object == null || field == null) {
			LogHelper.logWarning("value has not been written because object or field might be null", getClass());
			return this;
		}
		//if(value != null && ClassHelper.isInstanceOf(field.getType(), Boolean.class) && value instanceof String)
		//	value = (VALUE) ValueConverter.getInstance().convertToBoolean(value);
		FieldHelper.write(object, field, value);
		return this;
	}
	
	@Override
	public Boolean getIsInput() {
		return Boolean.TRUE;
	}
	
	/**/
	
	public static final String FIELD_DISABLED = "disabled";
	public static final String FIELD_READ_ONLY = "readOnly";
	public static final String FIELD_REQUIRED = "required";
	public static final String FIELD_REQUIRED_MESSAGE = "requiredMessage";
	public static final String FIELD_VALIDATOR = "validator";
	public static final String FIELD_VALIDATOR_MESSAGE = "validatorMessage";
	public static final String FIELD_PLACEHOLDER = "placeholder";
	public static final String FIELD_CONVERTER = "converter";
	
	/**/
	
	public static interface Listener {
		void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException;
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override
			public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {}
			
			protected void throwValidatorException(String summary,String detail) throws ValidatorException{
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,summary,detail));
			}
			
			protected void throwValidatorException(String summary) throws ValidatorException{
				throwValidatorException(summary, summary);
			}
			
			protected void throwValidatorExceptionIf(Boolean condition,String summary) throws ValidatorException{
				if(Boolean.TRUE.equals(condition))
					throwValidatorException(summary);
			}
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				public static final Listener INSTANCE = new DefaultImpl();
			}
		}
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInput<?>> extends AbstractInputOutput.AbstractConfiguratorImpl<INPUT> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			Action action = (Action) MapHelper.readByKey(arguments, FIELD_ACTION);
			Boolean edtitable = action == null ? Boolean.TRUE : (Action.CREATE.equals(action) || Action.UPDATE.equals(action) || Action.EDIT.equals(action));
			if(input.readOnly == null)
				input.readOnly = !Boolean.TRUE.equals(edtitable);
			if(input.disabled == null)
				input.disabled = Boolean.FALSE;
			
			if(input.required == null) {
				if(input.field != null)
					input.required = input.field.isAnnotationPresent(NotNull.class);
			}
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_MESSAGABLE)) || Boolean.TRUE.equals(input.required)) {
				if(input.message == null)
					input.message = Message.build(Map.of(Message.ConfiguratorImpl.FIELD_INPUT,input));
				if(StringHelper.isBlank(input.requiredMessage)) {
					input.requiredMessage = "valeur obligatoire";
				}
			}
			
			if(input.outputLabel == null) {
				String outputLabelValue = (String) MapHelper.readByKey(arguments, FIELD_OUTPUT_LABEL_VALUE);
				if(StringHelper.isBlank(outputLabelValue)) {
					if(input.field == null) {
						outputLabelValue = __getDefaultOutputLabelValue__(input);
					}else {
						outputLabelValue = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(input.field.getName()),null,null,Case.FIRST_CHARACTER_UPPER);
					}	
				}				
				input.outputLabel = OutputLabel.build(OutputLabel.FIELD_VALUE,outputLabelValue,OutputLabel.FIELD_FOR,input.getIdentifier()
						,OutputLabel.FIELD_CARDINAL_POINT_FROM_REFERENCE,MapHelper.readByKey(arguments, FIELD_OUTPUT_LABEL_CARDINAL_POINT));
			}
			
			if(input.placeholder == null) {
				if(input.outputLabel != null)
					input.placeholder = input.outputLabel.getValue();
			}
		}
		
		protected String __getDefaultOutputLabelValue__(INPUT input) {
			return "Input Text Label";
		}
		
		public static final String FIELD_ACTION = "configurator.action";
		public static final String FIELD_OUTPUT_LABEL_VALUE = "outputLabelValue";
		public static final String FIELD_OUTPUT_LABEL_CARDINAL_POINT = "outputLabelCardinalPoint";
		public static final String FIELD_MESSAGABLE = "configurator.messagable";
	}
}
