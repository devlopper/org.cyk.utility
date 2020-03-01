package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.icon.IconIdentifierGetter;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Confirm;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractCommand extends AbstractAction implements Serializable {

	protected Confirm confirm;
	protected String value,title,icon;
	
	protected String outcome;
	protected Map<String,String> parameters;
	
	public AbstractCommand setIcon(Icon icon) {
		if(icon == null)
			this.icon = null;
		else
			this.icon = IconIdentifierGetter.FONT_AWSOME.get(icon);
		return this;
	}
	
	public AbstractCommand applyValue(String value,Boolean isUpdateTitle) {
		this.value = value;
		if(StringHelper.isBlank(this.value))
			//FIXME handle empty value
			this.value = ConstantEmpty.STRING;
		if(Boolean.TRUE.equals(isUpdateTitle))
			setTitle(null).applyTitle(this.value);
		return this;
	}
	
	public AbstractCommand applyTitle(String title) {
		this.title = title;
		if(StringHelper.isBlank(this.title))
			if(StringHelper.isBlank(this.value))
				//FIXME handle empty value	
				this.title = ".";
			else
				this.title = this.value;
		return this;
	}
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_ICON = "icon";
	
	public static final String FIELD_OUTCOME = "outcome";
	public static final String FIELD_PARAMETERS = "parameters";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COMMAND extends AbstractCommand> extends AbstractAction.AbstractConfiguratorImpl<COMMAND> implements Serializable {

		@Override
		public void configure(COMMAND command, Map<Object, Object> arguments) {
			super.configure(command, arguments);
			String value = command.getValue();
			if(StringHelper.isBlank(value)) {
				String methodName = (String) MapHelper.readByKey(arguments, FIELD_METHOD_NAME);
				if(StringHelper.isNotBlank(methodName))
					value = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(methodName,InternationalizationKeyStringType.VERB), null, null, Case.FIRST_CHARACTER_UPPER);					
			}
			command.applyValue(value,Boolean.FALSE);
			command.applyTitle(command.getTitle());
			
			if(command.confirm == null && Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_CONFIRMABLE))) {
				command.confirm = Confirm.build();
			}
		}
		
		public static final String FIELD_CONFIRMABLE = "confirmable";
	}
}