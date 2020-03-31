package org.cyk.utility.__kernel__.__entities__;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
public class Employee extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {
	
	@Transient
	//@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	//@JoinColumn(name = "identifier")
	private Collection<EmployeeAddress> employeeAddresses;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "employee")
	private Collection<DepartmentEmployee> departmentEmployees;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL},orphanRemoval = true,mappedBy = "employee")
	private Collection<Mark> marks;
	
	@Override
	public Employee setIdentifier(String identifier) {
		return (Employee) super.setIdentifier(identifier);
	}
	
	@Override
	public Employee setCode(String code) {
		return (Employee) super.setCode(code);
	}
	
	@Override
	public Employee setName(String name) {
		return (Employee) super.setName(name);
	}
	
	public Collection<DepartmentEmployee> getDepartmentEmployees(Boolean injectIfNull) {
		if(departmentEmployees == null && Boolean.TRUE.equals(injectIfNull))
			departmentEmployees = new ArrayList<>();
		return departmentEmployees;
	}
	
	public Employee addDepartmentEmployees(Collection<DepartmentEmployee> departmentEmployees) {
		if(CollectionHelper.isEmpty(departmentEmployees))
			return this;
		departmentEmployees.forEach(departmentEmployee -> {
			departmentEmployee.setEmployee(this);
			if(StringHelper.isBlank(departmentEmployee.getIdentifier()))
				departmentEmployee.setIdentifier(UUID.randomUUID().toString());
		});
		getDepartmentEmployees(Boolean.TRUE).addAll(departmentEmployees);
		return this;
	}
	
	public Employee addDepartmentEmployees(DepartmentEmployee...departmentEmployees) {
		if(ArrayHelper.isEmpty(departmentEmployees))
			return this;
		return addDepartmentEmployees(CollectionHelper.listOf(departmentEmployees));
	}
	
	public Employee addMarks(Mark...marks) {
		if(this.marks == null)
			this.marks = new ArrayList<>();
		for(Mark mark : marks)
			mark.setEmployee(this);
		this.marks.addAll(CollectionHelper.listOf(marks));
		return this;
	}
}