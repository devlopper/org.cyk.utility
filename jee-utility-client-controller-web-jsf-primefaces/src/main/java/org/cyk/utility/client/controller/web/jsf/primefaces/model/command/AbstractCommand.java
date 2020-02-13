package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.icon.IconIdentifierGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractCommand extends AbstractAction implements Serializable {

	protected String value,title,icon;
	
	public AbstractCommand setIcon(Icon icon) {
		if(icon == null)
			this.icon = null;
		else
			this.icon = IconIdentifierGetter.FONT_AWSOME.get(icon);
		return this;
	}
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_ICON = "icon";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COMMAND extends AbstractCommand> extends AbstractAction.AbstractConfiguratorImpl<COMMAND> implements Serializable {

		@Override
		public void configure(COMMAND command, Map<Object, Object> arguments) {
			super.configure(command, arguments);
			if(StringHelper.isBlank(command.getValue()))
				//FIXME handle empty value
				command.setValue(ConstantEmpty.STRING);
			if(StringHelper.isBlank(command.getTitle()))
				if(StringHelper.isBlank(command.getValue()))
					//FIXME handle empty value	
					command.setTitle(".");
				else
					command.setTitle(command.getValue());
		}
	}
}