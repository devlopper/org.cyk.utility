package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityEditPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeEditPage extends AbstractEntityEditPageContainerManagedImpl<PersonType> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Form form02;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		form02 = Form.build(Form.FIELD_ENTITY_CLASS,PersonType.class,Form.FIELD_ENTITY,new PersonType(),Form.ConfiguratorImpl.FIELD_INPUTS_FIELDS_NAMES
				,CollectionHelper.listOf(PersonType.FIELD_CODE,PersonType.FIELD_NAME)
				,Form.ConfiguratorImpl.FIELD_LISTENER,new Form.ConfiguratorImpl.Listener.AbstractImpl() {
					@Override
					public Map<Object, Object> getInputArguments(Form form, String fieldName) {
						Map<Object, Object> map = super.getInputArguments(form, fieldName);
						if(PersonType.FIELD_NAME.equals(fieldName))
							map.put(AbstractInput.AbstractConfiguratorImpl.FIELD_OUTPUT_LABEL_VALUE, "Libellé");
						return map;
					}
				});
		
	}
	
}
