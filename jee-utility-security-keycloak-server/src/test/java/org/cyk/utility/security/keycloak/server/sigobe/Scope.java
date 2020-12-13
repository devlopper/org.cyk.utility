package org.cyk.utility.security.keycloak.server.sigobe;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Scope.TABLE_NAME)
@AttributeOverrides(value = {
		@AttributeOverride(name = Scope.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
		,@AttributeOverride(name = Scope.FIELD_CODE,column = @Column(name = "CODE"))
		,@AttributeOverride(name = Scope.FIELD_NAME,column = @Column(name = "LIBELLE"))
	})
public class Scope extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name = "type") @NotNull private ScopeType type;
	
	@Override
	public Scope setIdentifier(String identifier) {
		return (Scope) super.setIdentifier(identifier);
	}
	
	@Override
	public Scope setCode(String code) {
		return (Scope) super.setCode(code);
	}
	
	@Override
	public Scope setName(String name) {
		return (Scope) super.setName(name);
	}
	
	public static final String FIELD_SECTION_AS_STRING = "sectionAsString";
	public static final String FIELD_ACTION_AS_STRING = "actionAsString";
	public static final String FIELD_BUDGET_SPECIALIZATION_UNIT_AS_STRING = "budgetSpecializationUnitAsString";
	
	public static final String TABLE_NAME = "VM_APP_DOMAINE";
}