package org.cyk.utility.__kernel__.security.keycloak.sigobe;

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
@Entity @Table(name=ScopeType.TABLE_NAME)
@AttributeOverrides(value = {
		@AttributeOverride(name = Scope.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
		,@AttributeOverride(name = Scope.FIELD_CODE,column = @Column(name = "CODE"))
		,@AttributeOverride(name = Scope.FIELD_NAME,column = @Column(name = "LIBELLE"))
	})
public class ScopeType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public ScopeType setIdentifier(String identifier) {
		return (ScopeType) super.setIdentifier(identifier);
	}
	
	@Override
	public ScopeType setCode(String code) {
		return (ScopeType) super.setCode(code);
	}
	
	@Override
	public ScopeType setName(String name) {
		return (ScopeType) super.setName(name);
	}
	
	public static final String TABLE_NAME = "TYPE_DOMAINE";
}