package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=ServiceGroup.TABLE_NAME)
public class ServiceGroup extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ServiceGroup setIdentifier(String identifier) {
		return (ServiceGroup) super.setIdentifier(identifier);
	}
	
	@Override
	public ServiceGroup setCode(String code) {
		return (ServiceGroup) super.setCode(code);
	}
	
	@Override
	public ServiceGroup setName(String name) {
		return (ServiceGroup) super.setName(name);
	}
	
	public static final String TABLE_NAME = "groupe_service";
	
	public static final String CODE_NOT_SET = "NON_DEFINI";
}
