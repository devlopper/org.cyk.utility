package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=AdministrativeUnitActivity.TABLE_NAME)
public class AdministrativeUnitActivity extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) private AdministrativeUnit administrativeUnit;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTIVITY,unique = true) private Activity activity;
	@ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT_BENEFICIAIRE) private AdministrativeUnit administrativeUnitBeneficiaire;
	
	//@NotNull @ManyToOne @JoinColumn(name = COLUMN_TYPE) private AdministrativeUnitActivityType type;

	public AdministrativeUnitActivity(AdministrativeUnit administrativeUnit,Activity activity) {
		this.administrativeUnit = administrativeUnit;
		this.activity = activity;
		//this.type = type;
	}
	
	public AdministrativeUnitActivity(String administrativeUnitCode,String activityCode) {
		setAdministrativeUnitFromCode(administrativeUnitCode);
		setActivityFromCode(activityCode);
	}
	
	public AdministrativeUnitActivity setAdministrativeUnitFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.administrativeUnit = null;
		else
			this.administrativeUnit = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public AdministrativeUnitActivity setActivityFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.activity = null;
		else
			this.activity = InstanceGetter.getInstance().getByBusinessIdentifier(Activity.class, code);
		return this;
	}
	
	public AdministrativeUnitActivity setAdministrativeUnitBeneficiaireFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.administrativeUnitBeneficiaire = null;
		else
			this.administrativeUnitBeneficiaire = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_ACTIVITY = "activity";
	public static final String FIELD_ADMINISTRATIVE_UNIT_BENEFICIAIRE = "administrativeUnitBeneficiaire";
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_ADMINISTRATIVE_UNIT = "gestionnaire";
	public static final String COLUMN_ACTIVITY = Activity.TABLE_NAME;
	public static final String COLUMN_ADMINISTRATIVE_UNIT_BENEFICIAIRE = "beneficiaire";
	public static final String COLUMN_TYPE = "type";
	
	public static final String TABLE_NAME = "unite_administrative_activite";
	
}
