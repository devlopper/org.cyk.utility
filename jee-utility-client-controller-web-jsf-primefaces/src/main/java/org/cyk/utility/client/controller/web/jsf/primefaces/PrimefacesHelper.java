package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.file.File;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Singleton @Named
public class PrimefacesHelper extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getScriptInstructionHide(String widgetVar) {
		return String.format(SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT, widgetVar,"hide");
	}

	public String computeAttributeUpdate(Objects updatables,Strings strings) {
		if(strings == null)
			strings = __inject__(Strings.class);
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
		return strings.concatenate(ConstantCharacter.COMA);
	}
	
	public String computeAttributeUpdate(Component component,Strings strings) {
		return computeAttributeUpdate(component.getUpdatables(), strings);
	}
	
	public String computeAttributeUpdate(Component component,String...strings) {
		return computeAttributeUpdate(component, __inject__(Strings.class).add(strings));
	}
	
	public String computeAttributeUpdate(Component component) {
		return computeAttributeUpdate(component,__inject__(Strings.class));
	}
	
	public String computeAttributeUpdate(Event event,Strings strings) {
		return computeAttributeUpdate(event.getUpdatables(), strings);
	}
	
	public String computeAttributeUpdate(Event event,String...strings) {
		return computeAttributeUpdate(event, __inject__(Strings.class).add(strings));
	}
	
	public String computeAttributeUpdate(Event event) {
		return computeAttributeUpdate(event,__inject__(Strings.class));
	}
	
	public StreamedContent computeStreamedContent(File file) {
		return new DefaultStreamedContent(new ByteArrayInputStream(file.getBytes()), file.getMimeType());
	}
	
	public String getMediaPlayerFromMimeType(String mimeType) {
		String player = null;
		if(StringUtils.endsWithIgnoreCase(mimeType, "/pdf"))
			player = "pdf";
		else if(StringUtils.startsWithIgnoreCase(mimeType, "audio/"))
			player = "quicktime";
		else if(StringUtils.startsWithIgnoreCase(mimeType, "video/"))
			player = "quicktime";
		return player;
	}
	
	/**/
	
	private static final String SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT = "PF('%s').%s();";
}
