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
@Entity @Table(name=Section.TABLE_NAME)
public class Section extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	public Section(String code,String name) {
		super(code,name);
	}
	
	@Override
	public Section setIdentifier(String identifier) {
		return (Section) super.setIdentifier(identifier);
	}
	
	@Override
	public Section setCode(String code) {
		return (Section) super.setCode(code);
	}
	
	@Override
	public Section setName(String name) {
		return (Section) super.setName(name);
	}
	
	public static final String TABLE_NAME = "section";	
}
