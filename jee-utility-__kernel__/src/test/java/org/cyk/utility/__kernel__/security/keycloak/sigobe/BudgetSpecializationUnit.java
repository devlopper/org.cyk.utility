package org.cyk.utility.__kernel__.security.keycloak.sigobe;

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
import org.cyk.utility.__kernel__.persistence.query.EntityFinder;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=BudgetSpecializationUnit.TABLE_NAME)
@AttributeOverrides(value = {
		@AttributeOverride(name = BudgetSpecializationUnit.FIELD_IDENTIFIER,column = @Column(name = "IDENTIFIANT"))
		,@AttributeOverride(name = BudgetSpecializationUnit.FIELD_CODE,column = @Column(name = "CODE"))
		,@AttributeOverride(name = BudgetSpecializationUnit.FIELD_NAME,column = @Column(name = "LIBELLE"))
	})
public class BudgetSpecializationUnit extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name = COLUMN_SECTION) @NotNull private Section section;
	
	@Override
	public BudgetSpecializationUnit setIdentifier(String identifier) {
		return (BudgetSpecializationUnit) super.setIdentifier(identifier);
	}
	
	@Override
	public BudgetSpecializationUnit setCode(String code) {
		return (BudgetSpecializationUnit) super.setCode(code);
	}
	
	public BudgetSpecializationUnit setSectionFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			setSection(null);
		else
			setSection(EntityFinder.getInstance().find(Section.class, identifier));
		return this;
	}
	
	public static final String FIELD_SECTION = "section";
	
	public static final String COLUMN_SECTION = "SECTION";
	
	public static final String TABLE_NAME = "VM_APP_USB";
}