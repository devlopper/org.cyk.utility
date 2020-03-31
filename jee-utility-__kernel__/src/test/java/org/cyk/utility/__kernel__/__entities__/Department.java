package org.cyk.utility.__kernel__.__entities__;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Department extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "department")
	private Collection<DepartmentAddress> departmentAddresses;
	
	@Override
	public Department setIdentifier(String identifier) {
		return (Department) super.setIdentifier(identifier);
	}
	
	@Override
	public Department setCode(String code) {
		return (Department) super.setCode(code);
	}
	
	@Override
	public Department setName(String name) {
		return (Department) super.setName(name);
	}
	
	public Collection<DepartmentAddress> getDepartmentAddresses(Boolean injectIfNull) {
		if(departmentAddresses == null && Boolean.TRUE.equals(injectIfNull))
			departmentAddresses = new ArrayList<>();
		return departmentAddresses;
	}
	
	public Department addDepartmentAddresses(Collection<DepartmentAddress> departmentAddresses) {
		if(CollectionHelper.isEmpty(departmentAddresses))
			return this;
		departmentAddresses.forEach(departmentAddress -> {
			departmentAddress.setDepartment(this);
			if(StringHelper.isBlank(departmentAddress.getIdentifier()))
				departmentAddress.setIdentifier(UUID.randomUUID().toString());
		});
		getDepartmentAddresses(Boolean.TRUE).addAll(departmentAddresses);
		return this;
	}
	
	public Department addDepartmentAddresses(DepartmentAddress...departmentAddresses) {
		if(ArrayHelper.isEmpty(departmentAddresses))
			return this;
		return addDepartmentAddresses(CollectionHelper.listOf(departmentAddresses));
	}
}
