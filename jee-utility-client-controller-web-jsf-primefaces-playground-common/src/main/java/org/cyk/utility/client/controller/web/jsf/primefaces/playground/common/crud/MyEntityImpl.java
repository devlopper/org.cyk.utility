package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MyEntityImpl extends AbstractDataImpl implements MyEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne
	@NotNull
	private String code;
	
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";
	
	/**/
	
	public static MyEntity getByIdentifier(Object identifier) {
		for(MyEntity index : MyEntity.COLLECTION)
			if(index.getIdentifier().equals(identifier))
				return index;
		return null;
	}
}
