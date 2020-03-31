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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"department","employee"})})
public class DepartmentEmployee extends AbstractIdentifiableSystemScalarStringImpl {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department")
	private Department depatrment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee")
	private Employee employee;
	
	@Override
	public DepartmentEmployee setIdentifier(String identifier) {
		return (DepartmentEmployee) super.setIdentifier(identifier);
	}
	
	public DepartmentEmployee setDepartmentRefenceFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			this.depatrment = null;
		else
			this.depatrment = PersistenceHelper.getEntityWithItsReferenceOnly(Department.class, identifier);
		return this;
	}
	
	public DepartmentEmployee setEmployeeRefenceFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			this.employee = null;
		else
			this.employee = PersistenceHelper.getEntityWithItsReferenceOnly(Employee.class, identifier);
		return this;
	}
}
