package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Program.TABLE_NAME)
public class Program extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	@Column(name = COLUMN_BUDGET_CATEGORY_CODE) private String budgetCategoryCode;
	
	@Transient private Collection<AdministrativeUnit> administrativeUnits;
	
	public Program(String code,String name,String sectionCode,String budgetCategoryCode) {
		super(code,name);
		setSectionFromCode(sectionCode);
		this.budgetCategoryCode = budgetCategoryCode;
	}
	
	@Override
	public Program setCode(String code) {
		return (Program) super.setCode(code);
	}
	
	@Override
	public Program setName(String name) {
		return (Program) super.setName(name);
	}
	
	public Program setSectionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.section = null;
		else
			this.section = InstanceGetter.getInstance().getByBusinessIdentifier(Section.class, code);
		return this;
	}
	
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_BUDGET_CATEGORY_CODE = "budgetCategoryCode";
	public static final String FIELD_ADMINISTRATIVE_UNITS = "administrativeUnits";
	
	public static final String COLUMN_SECTION = Section.TABLE_NAME;	
	public static final String COLUMN_BUDGET_CATEGORY_CODE = "CATEGORYBUDGET";	
	
	public static final String TABLE_NAME = "programme";	
}
