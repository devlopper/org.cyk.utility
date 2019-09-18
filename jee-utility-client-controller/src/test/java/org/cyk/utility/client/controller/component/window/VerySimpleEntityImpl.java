package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringAndCodedImpl;

public class VerySimpleEntityImpl extends AbstractDataIdentifiedByStringAndCodedImpl implements VerySimpleEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne @NotNull
	private String name;
	
	@Input @InputString @InputStringLineMany @NotNull
	private String description;
	
	private VerySimpleEntityDetails details;
	
	@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo @NotNull
	private VerySimpleEntityEnum enumeration;
	
	@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox @NotNull
	private Collection<VerySimpleEntityEnum> enumerations;
	
	@Override
	public VerySimpleEntity setIdentifier(String identifier) {
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
	public VerySimpleEntityDetails getDetails() {
		return details;
	}
	
	@Override
	public VerySimpleEntity setDetails(VerySimpleEntityDetails details) {
		this.details = details;
		return this;
	}
	
	@Override
	public VerySimpleEntityEnum getEnumeration() {
		return enumeration;
	}
	
	@Override
	public VerySimpleEntity setEnumeration(VerySimpleEntityEnum enumeration) {
		this.enumeration = enumeration;
		return this;
	}
	
	@Override
	public Collection<VerySimpleEntityEnum> getEnumerations() {
		return enumerations;
	}
	
	@Override
	public VerySimpleEntity setEnumerations(Collection<VerySimpleEntityEnum> enumerations) {
		this.enumerations = enumerations;
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
}
