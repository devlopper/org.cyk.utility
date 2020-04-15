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
@Entity @Table(name=FunctionalClassification.TABLE_NAME)
public class FunctionalClassification extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionalClassification setIdentifier(String identifier) {
		return (FunctionalClassification) super.setIdentifier(identifier);
	}
	
	@Override
	public FunctionalClassification setCode(String code) {
		return (FunctionalClassification) super.setCode(code);
	}
	
	@Override
	public FunctionalClassification setName(String name) {
		return (FunctionalClassification) super.setName(name);
	}
	
	public static final String TABLE_NAME = "cfap";
	
	public static final String CODE_NOT_SET = "NON_DEFINI";
}
