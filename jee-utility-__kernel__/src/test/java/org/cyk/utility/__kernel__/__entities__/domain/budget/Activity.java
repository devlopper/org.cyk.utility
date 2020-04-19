package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Entity @Table(name=Activity.TABLE_NAME)
public class Activity extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTION) private Action action;
	
	@Column(name = COLUMN_YEAR) private Integer year;
	@Column(name = COLUMN_AMOUNT_AE) private Long amountAE;
	@Column(name = COLUMN_AMOUNT_CP) private Long amountCP;
	@Column(name = COLUMN_NAT_DEP_CODE) private String natDepCode;
	@Column(name = COLUMN_CAT_USB_CODE) private String catUsbCode;
	@Column(name = COLUMN_CAT_ATV_CODE) private String catAtvCode;
	
	@Column(name = COLUMN_CREATION_DATE) private LocalDateTime creationDate;
	@Column(name = COLUMN_MODIFICATION_DATE) private LocalDateTime modificationDate;
	
	@Transient private Section section;
	@Transient private Program program;
	@Transient private AdministrativeUnit administrativeUnit;
	@Transient private AdministrativeUnit administrativeUnitGestionnaire;
	@Transient private AdministrativeUnit administrativeUnitBeneficiaire;
	@Transient private AdministrativeUnit administrativeUnitGestionnaireOrBeneficiaire;
	@Transient private String activity;
	@Transient private Collection<CostUnit> costUnits;
	@Transient private Integer numberOfCostUnits;
	@Transient private Boolean isGestionnaire;
	@Transient private Boolean isBeneficiaire;
	
	/* As String */	
	@Transient private String sectionAsString,programAsString,actionAsString,functionTypeAsString,managerAsString,beneficiaryAsString;
	
	public Activity(String code,String name,String actionCode) {
		super(code,name);
		setActionFromCode(actionCode);
	}
	
	@Override
	public Activity setIdentifier(String identifier) {
		return (Activity) super.setIdentifier(identifier);
	}
	
	@Override
	public Activity setCode(String code) {
		return (Activity) super.setCode(code);
	}
	
	@Override
	public Activity setName(String name) {
		return (Activity) super.setName(name);
	}
	
	public Activity setActionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.action = null;
		else
			this.action = InstanceGetter.getInstance().getByBusinessIdentifier(Action.class, code);
		return this;
	}
	
	public Activity setAdministrativeUnitFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.administrativeUnit = null;
		else
			this.administrativeUnit = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public Activity setAdministrativeUnitBeneficiaireFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.administrativeUnitBeneficiaire = null;
		else
			this.administrativeUnitBeneficiaire = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_FUNCTION_TYPE = "functionType";
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_AMOUNT_AE = "amountAE";
	public static final String FIELD_AMOUNT_CP = "amountCP";
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_PROGRAM = "program";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE = "administrativeUnitGestionnaire";
	public static final String FIELD_ADMINISTRATIVE_UNIT_BENEFICIAIRE = "administrativeUnitBeneficiaire";
	public static final String FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE_OR_BENEFICIAIRE = "administrativeUnitGestionnaireOrBeneficiaire";
	public static final String FIELD_DESTINATIONS = "destinations";
	public static final String FIELD_CAT_ATV_CODE = "catAtvCode";
	public static final String FIELD_ACTIVITY = "activity";
	public static final String FIELD_COST_UNITS = "costUnits";
	public static final String FIELD_NUMBER_OF_COST_UNITS = "numberOfCostUnits";
	public static final String FIELD_IS_GESTIONNAIRE = "isGestionnaire";
	public static final String FIELD_IS_BENEFICIAIRE = "isBeneficiaire";
	public static final String FIELD_CREATION_DATE = "creationDate";
	public static final String FIELD_MODIFICATION_DATE = "modificationDate";
	
	public static final String COLUMN_ACTION = FIELD_ACTION;
	public static final String COLUMN_FUNCTION_TYPE = "typfonc";
	public static final String COLUMN_YEAR = "ANNEE";
	public static final String COLUMN_AMOUNT_AE = "MONTANT_AE";
	public static final String COLUMN_AMOUNT_CP = "MONTANT_CP";
	public static final String COLUMN_NAT_DEP_CODE = "NAT_DEP_CODE";
	public static final String COLUMN_CAT_USB_CODE = "CAT_USB_CODE";
	public static final String COLUMN_CAT_ATV_CODE = "CAT_ATV_CODE";
	public static final String COLUMN_CREATION_DATE = "date_creation";
	public static final String COLUMN_MODIFICATION_DATE = "date_modification";
	
	public static final String TABLE_NAME = "activite";	
	
	public static final String CODE_NEW_PREFIX = "NA";
}
