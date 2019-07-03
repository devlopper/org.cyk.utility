package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.bean.AbstractPropertyValueGetterImpl;
import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.ComponentBuilderHelper;

@Primefaces
public class PropertyValueGetterImpl extends AbstractPropertyValueGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __derive__(Property property) {
		if(property.getValue() instanceof Component) {
			Component component = (Component) property.getValue();
			//DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
			Object object = __inject__(ComponentBuilderHelper.class).build(component);
			//__logInfo__("Component : <<"+object.getClass().getSimpleName()+">>. Duration : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndNow()).execute().getOutput());
			return object;
		}
		return super.__derive__(property);
	}
	
}
