package org.cyk.utility.security.keycloak.server.sigobe;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Section.TABLE_NAME)
@AttributeOverrides(value = {
	@AttributeOverride(name = Section.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
	,@AttributeOverride(name = Section.FIELD_CODE,column = @Column(name = "CODE"))
	,@AttributeOverride(name = Section.FIELD_NAME,column = @Column(name = "LIBELLE"))
})
public class Section extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Section setIdentifier(String identifier) {
		return (Section) super.setIdentifier(identifier);
	}
	
	public static final String TABLE_NAME = "VM_APP_SECTION";	
}