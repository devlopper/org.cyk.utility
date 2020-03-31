package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"department","address"})})
public class DepartmentAddress extends AbstractIdentifiableSystemScalarStringImpl {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department")
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address")
	private Address address;
	
	public DepartmentAddress setDepartmentRefenceFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			this.department = null;
		else
			this.department = PersistenceHelper.getEntityWithItsReferenceOnly(Department.class, identifier);
		return this;
	}
	
	public DepartmentAddress setAddressRefenceFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			this.address = null;
		else
			this.address = PersistenceHelper.getEntityWithItsReferenceOnly(Address.class, identifier);
		return this;
	}
}