package org.cyk.utility.service.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.object.__static__.representation.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl extends AbstractIdentifiableSystemScalarStringImpl implements IdentifiableBusiness<String>,AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String code;
	
	// Audit
	protected String __auditWho__;	
	protected String __auditWhat__;	
	protected String __auditFunctionality__;	
	protected Long __auditWhenAsTimestamp__;
	protected String __auditWhenAsString__;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl(String identifier,String code) {
		super(identifier);
		setCode(code);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl(String code) {
		this(null,code);
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public String getBusinessIdentifier() {
		return getCode();
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl setBusinessIdentifier(String identifier) {
		setCode(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(code))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
}