package org.cyk.utility.__kernel__.security.keycloak.sigobe;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Actor.TABLE_NAME)
@AttributeOverrides(value= {
		@AttributeOverride(name = Actor.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
		,@AttributeOverride(name = Actor.FIELD_CODE,column = @Column(name="NOM_UTILISATEUR"))		
})
public class Actor extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "ACTEUR";
}