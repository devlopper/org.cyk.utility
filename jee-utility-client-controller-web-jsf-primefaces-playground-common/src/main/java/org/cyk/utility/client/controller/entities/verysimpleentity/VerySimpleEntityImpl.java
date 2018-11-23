package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataImpl;

public class VerySimpleEntityImpl extends AbstractDataImpl implements VerySimpleEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne
	@NotNull
	private String name;
	
	@Input @InputString @InputStringLineMany
	@NotNull
	private String description;
	
	@Override
	public VerySimpleEntity setIdentifier(Object identifier) {
		return (VerySimpleEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public VerySimpleEntity setCode(String code) {
		return (VerySimpleEntity) super.setCode(code);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public VerySimpleEntity setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public VerySimpleEntity setDescription(String description) {
		this.description = description;
		return this;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";
	
	/**/
	
	public static VerySimpleEntity getByIdentifier(Object identifier) {
		if(identifier!=null)
			for(VerySimpleEntity index : VerySimpleEntity.COLLECTION)
				if(index.getIdentifier().equals(identifier.toString()))
					return index;
		return null;
	}
}
