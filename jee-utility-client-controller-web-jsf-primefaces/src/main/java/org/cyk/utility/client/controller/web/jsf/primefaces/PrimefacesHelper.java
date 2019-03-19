package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

@Singleton @Named
public class PrimefacesHelper extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getScriptInstructionHide(String widgetVar) {
		return String.format(SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT, widgetVar,"hide");
	}

	public String computeAttributeUpdate(Component component,Strings strings) {
		if(strings == null)
			strings = __inject__(Strings.class);
		Objects updatables = component.getUpdatables();
		if(__inject__(CollectionHelper.class).isNotEmpty(updatables))
			for(Object index : updatables.get()) {
				Component indexComponent = null;
				if(index instanceof Component)
					indexComponent = (Component) index;
				else if(index instanceof ComponentBuilder<?>)
					indexComponent = ((ComponentBuilder<?>)index).getComponent();
				
				if(indexComponent instanceof VisibleComponent) {
					String token = null;
					token = (String)((VisibleComponent)indexComponent).getProperties().getIdentifierAsStyleClass();
					if(__inject__(StringHelper.class).isNotBlank(token))
						strings.add("@(."+token+")");		
				}else
					strings.add(index.toString());
				
			}
		return strings.concatenate(CharacterConstant.COMA);
	}
	
	public String computeAttributeUpdate(Component component,String...strings) {
		return computeAttributeUpdate(component, __inject__(Strings.class).add(strings));
	}
	
	public String computeAttributeUpdate(Component component) {
		return computeAttributeUpdate(component,__inject__(Strings.class));
	}
	
	/**/
	
	private static final String SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT = "PF('%s').%s();";
}
