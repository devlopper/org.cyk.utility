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
@Entity @Table(name=Localisation.TABLE_NAME)
public class Localisation extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Localisation setIdentifier(String identifier) {
		return (Localisation) super.setIdentifier(identifier);
	}
	
	@Override
	public Localisation setCode(String code) {
		return (Localisation) super.setCode(code);
	}
	
	@Override
	public Localisation setName(String name) {
		return (Localisation) super.setName(name);
	}
	
	public static final String TABLE_NAME = "localisation_geographique";	
	
	public static final String CODE_NOT_SET = "NON_DEFINI";
}
