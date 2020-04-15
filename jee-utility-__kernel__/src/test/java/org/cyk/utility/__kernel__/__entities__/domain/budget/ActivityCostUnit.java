package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=ActivityCostUnit.TABLE_NAME)
public class ActivityCostUnit extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTIVITY) private Activity activity;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_COST_UNIT) private CostUnit costUnit;
	@Column(name = COLUMN_FINANCEMENT_AE) private Long financementAE;
	@Column(name = COLUMN_FINANCEMENT_CP) private Long financementCP;
	@Column(name = COLUMN_ARBITRAGE_AE) private Long arbitrageAE;
	@Column(name = COLUMN_ARBITRAGE_CP) private Long arbitrageCP;
	@Column(name = COLUMN_BUDGET_AE) private Long budgetAE;
	@Column(name = COLUMN_BUDGET_CP) private Long budgetCP;
	@Column(name = COLUMN_PROCEDURE) private String procedure;
	@Column(name = COLUMN_EXEMPTED) private String exempted;
	
	@Override
	public ActivityCostUnit setIdentifier(String identifier) {
		return (ActivityCostUnit) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_ACTIVITY = "activity";
	public static final String FIELD_COST_UNIT = "costUnit";
	public static final String FIELD_FINANCEMENT_CP = "financementCP";
	public static final String FIELD_FINANCEMENT_AE = "financementAE";
	public static final String FIELD_ARBITRAGE_CP = "arbitrageCP";
	public static final String FIELD_ARBITRAGE_AE = "arbitrageAE";
	public static final String FIELD_BUDGET_CP = "budgetCP";
	public static final String FIELD_BUDGET_AE = "budgetAE";
	public static final String FIELD_PROCEDURE = "procedure";
	public static final String FIELD_EXEMPTED = "exempted";
	
	public static final String COLUMN_ACTIVITY = "ACTIVITE";
	public static final String COLUMN_COST_UNIT = "UNITE_COUT";
	public static final String COLUMN_FINANCEMENT_CP = "PREVISION_CP";
	public static final String COLUMN_FINANCEMENT_AE = "PREVISION_AE";
	public static final String COLUMN_ARBITRAGE_CP = "ARBITRAGE_CP";
	public static final String COLUMN_ARBITRAGE_AE = "ARBITRAGE_AE";
	public static final String COLUMN_BUDGET_CP = "BUDGET_CP";
	public static final String COLUMN_BUDGET_AE = "BUDGET_AE";
	public static final String COLUMN_PROCEDURE = "PROCEDURE_LIGNE";
	public static final String COLUMN_EXEMPTED = "CARACTERE_EXEMPTION";
	
	public static final String TABLE_NAME = "LIGNE_IMPUTATION";	
}