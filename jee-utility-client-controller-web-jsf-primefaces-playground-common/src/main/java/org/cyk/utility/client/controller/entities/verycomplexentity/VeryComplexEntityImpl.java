package org.cyk.utility.client.controller.entities.verycomplexentity;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputBoolean;
import org.cyk.utility.client.controller.component.annotation.InputBooleanButton;
import org.cyk.utility.client.controller.component.annotation.InputBooleanCheckBox;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneRadio;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataImpl;

public class VeryComplexEntityImpl extends AbstractDataImpl implements VeryComplexEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne @NotNull
	private String firstName;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String lastNames;
	
	@Input @InputString @InputStringLineMany @NotNull
	private String description;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String electronicMailAddress;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String phoneNumber;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String postalBox;
	
	@Input @InputChoice @InputChoiceOne @InputChoiceOneRadio @NotNull
	private VeryComplexEntityEnum01 enumeration01;
	
	@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox @NotNull
	private Collection<VeryComplexEntityEnum01> enumerations01;
	
	@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo @NotNull
	private VeryComplexEntityEnum02 enumeration02;
	
	@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox @NotNull
	private Collection<VeryComplexEntityEnum02> enumerations02;
	
	@Input @InputBoolean @InputBooleanButton @NotNull
	private Boolean booleanValueCheckBox;
	
	@Input @InputBoolean @InputBooleanCheckBox @NotNull
	private Boolean booleanValueButton;
	
	@Override
	public VeryComplexEntity setIdentifier(Object identifier) {
		return (VeryComplexEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public VeryComplexEntity setCode(String code) {
		return (VeryComplexEntity) super.setCode(code);
	}
	
	@Override
	public Boolean getBooleanValueCheckBox() {
		return booleanValueCheckBox;
	}
	
	@Override
	public VeryComplexEntity setBooleanValueCheckBox(Boolean booleanValueCheckBox) {
		this.booleanValueCheckBox = booleanValueCheckBox;
		return this;
	}
	
	@Override
	public Boolean getBooleanValueButton() {
		return booleanValueButton;
	}
	
	@Override
	public VeryComplexEntity setBooleanValueButton(Boolean booleanValueButton) {
		this.booleanValueButton = booleanValueButton;
		return this;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public VeryComplexEntity setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public VeryComplexEntity setDescription(String description) {
		this.description = description;
		return this;
	}
	
	@Override
	public String getLastNames() {
		return lastNames;
	}

	@Override
	public VeryComplexEntity setLastNames(String lastNames) {
		this.lastNames = lastNames;
		return this;
	}

	@Override
	public String getElectronicMailAddress() {
		return electronicMailAddress;
	}

	@Override
	public VeryComplexEntity setElectronicMailAddress(String electronicMailAddress) {
		this.electronicMailAddress = electronicMailAddress;
		return this;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public VeryComplexEntity setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	@Override
	public String getPostalBox() {
		return postalBox;
	}

	@Override
	public VeryComplexEntity setPostalBox(String postalBox) {
		this.postalBox = postalBox;
		return this;
	}

	@Override
	public VeryComplexEntityEnum01 getEnumeration01() {
		return enumeration01;
	}

	@Override
	public VeryComplexEntity setEnumeration01(VeryComplexEntityEnum01 enumeration01) {
		this.enumeration01 = enumeration01;
		return this;
	}

	@Override
	public Collection<VeryComplexEntityEnum01> getEnumerations01() {
		return enumerations01;
	}

	@Override
	public VeryComplexEntity setEnumerations01(Collection<VeryComplexEntityEnum01> enumerations01) {
		this.enumerations01 = enumerations01;
		return this;
	}

	@Override
	public VeryComplexEntityEnum02 getEnumeration02() {
		return enumeration02;
	}

	@Override
	public VeryComplexEntity setEnumeration02(VeryComplexEntityEnum02 enumeration02) {
		this.enumeration02 = enumeration02;
		return this;
	}

	@Override
	public Collection<VeryComplexEntityEnum02> getEnumerations02() {
		return enumerations02;
	}

	@Override
	public VeryComplexEntity setEnumerations02(Collection<VeryComplexEntityEnum02> enumerations02) {
		this.enumerations02 =enumerations02;
		return this;
	}	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	/**/

	
	/**/
}
