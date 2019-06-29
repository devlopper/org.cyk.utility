package org.cyk.utility.client.controller.web.jsf.primefaces;
import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.event.AbstractEventBuilderImpl;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;

@Dependent @Primefaces
public class EventBuilderImpl extends AbstractEventBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Event __execute__() throws Exception {
		Event event = super.__execute__();
		event.setProperty(Properties.UPDATE, __inject__(PrimefacesHelper.class).computeAttributeUpdate(event, __inject__(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers()));
		return event;
	}
	
}
