package org.cyk.utility.__kernel__.__entities__;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class EmployeeDto extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	private ArrayList<DepartmentEmployeeDto> departmentEmployees;
	
	@Override
	public EmployeeDto setIdentifier(String identifier) {
		return (EmployeeDto) super.setIdentifier(identifier);
	}
	
	@Override
	public EmployeeDto setCode(String code) {
		return (EmployeeDto) super.setCode(code);
	}
	
	@Override
	public EmployeeDto setName(String name) {
		return (EmployeeDto) super.setName(name);
	}
	
	/**/
	
	public static EmployeeDto instantiat(Employee employee) {
		if(employee == null)
			return null;
		return new EmployeeDto().setIdentifier(employee.getIdentifier()).setCode(employee.getCode()).setName(employee.getName());
	}
	
	public static EmployeeDto instantiateOneRandomlyByIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			return null;
		return new EmployeeDto().setIdentifier(identifier).setCode(identifier).setName("name");
	}
	
	public static Collection<EmployeeDto> instantiateManyRandomlyByIdentifiers(Collection<String> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return identifiers.stream().map(identifier -> instantiateOneRandomlyByIdentifier(identifier)).collect(Collectors.toList());
	}
	
	public static Collection<EmployeeDto> instantiateManyRandomlyByIdentifiers(String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return instantiateManyRandomlyByIdentifiers(CollectionHelper.listOf(identifiers));
	}
	
	
}