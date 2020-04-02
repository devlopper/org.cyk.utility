package org.cyk.utility.__kernel__.__entities__;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "employee")
	private Collection<DepartmentEmployee> departmentEmployees;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL},mappedBy = "employee")
	private Collection<Mark> marks;
	
	public Employee(String identifier,String code,String name) {
		super(identifier,code,name);
	}
	
	public Employee(String code,String name) {
		super(code,name);
	}
	
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
	
	/**/
	
	public static Employee instantiateOneRandomlyByIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			return null;
		return new Employee(identifier,identifier,"name");
	}
	
	public static Collection<Employee> instantiateManyRandomlyByIdentifiers(Collection<String> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return identifiers.stream().map(identifier -> instantiateOneRandomlyByIdentifier(identifier)).collect(Collectors.toList());
	}
	
	public static Collection<Employee> instantiateManyRandomlyByIdentifiers(String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return instantiateManyRandomlyByIdentifiers(CollectionHelper.listOf(identifiers));
	}
}