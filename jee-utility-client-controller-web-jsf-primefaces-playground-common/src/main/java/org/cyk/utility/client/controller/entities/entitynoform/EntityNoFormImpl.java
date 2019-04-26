package org.cyk.utility.client.controller.entities.entitynoform;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringAndCodedImpl;

public class EntityNoFormImpl extends AbstractDataIdentifiedByStringAndCodedImpl implements EntityNoForm,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne @NotNull
	private String name;
	
	@Input @InputString @InputStringLineMany @NotNull
	private String description;
	
	@Override
	public EntityNoForm setIdentifier(Object identifier) {
		return (EntityNoForm) super.setIdentifier(identifier);
	}
	
	@Override
	public EntityNoForm setCode(String code) {
		return (EntityNoForm) super.setCode(code);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public EntityNoForm setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public EntityNoForm setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/**/
}
