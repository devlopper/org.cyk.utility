package org.cyk.utility.__kernel__.__entities__;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.__static__.controller.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class EmployeeData extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl {
	
	@Override
	public void setIdentifier(String identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	public void setCode(String code) {
		super.setCode(code);
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
	}
	
	/**/
	
	public static EmployeeData instantiat(Employee employee) {
		if(employee == null)
			return null;
		EmployeeData employeeData = new EmployeeData();
		employeeData.setIdentifier(employee.getIdentifier());
		employeeData.setCode(employee.getCode());
		employeeData.setName(employee.getName());
		return employeeData;
	}
	
	public static EmployeeData instantiateOneRandomlyByIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			return null;
		EmployeeData employeeData = new EmployeeData();
		employeeData.setIdentifier(identifier);
		employeeData.setCode(identifier);
		employeeData.setName("name");
		return employeeData;
	}
	
	public static Collection<EmployeeData> instantiateManyRandomlyByIdentifiers(Collection<String> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return identifiers.stream().map(identifier -> instantiateOneRandomlyByIdentifier(identifier)).collect(Collectors.toList());
	}
	
	public static Collection<EmployeeData> instantiateManyRandomlyByIdentifiers(String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return instantiateManyRandomlyByIdentifiers(CollectionHelper.listOf(identifiers));
	}
}