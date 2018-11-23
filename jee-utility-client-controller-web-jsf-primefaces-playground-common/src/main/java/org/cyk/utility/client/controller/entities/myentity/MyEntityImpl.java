package org.cyk.utility.client.controller.entities.myentity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataImpl;

public class MyEntityImpl extends AbstractDataImpl implements MyEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne
	@NotNull
	private String name;
	
	@Input @InputString @InputStringLineMany
	@NotNull
	private String description;
	
	@Override
	public MyEntity setIdentifier(Object identifier) {
		return (MyEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public MyEntity setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public MyEntity setDescription(String description) {
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
	
	public static MyEntity getByIdentifier(Object identifier) {
		if(identifier!=null)
			for(MyEntity index : MyEntity.COLLECTION)
				if(index.getIdentifier().equals(identifier.toString()))
					return index;
		return null;
	}
}
