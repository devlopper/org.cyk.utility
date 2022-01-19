package org.cyk.utility.service.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.object.__static__.representation.AuditableWhoDoneWhatWhen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper = false,of = "identifier") @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringAuditedImpl extends AbstractIdentifiableSystemScalarImpl<String> implements AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String identifier;

	protected String __audit__;
	protected String __auditWho__;
	protected String __auditWhat__;
	protected String __auditFunctionality__;
	protected Long __auditWhenAsTimestamp__;
	protected String __auditWhenAsString__;
	
	public AbstractIdentifiableSystemScalarStringAuditedImpl(String identifier) {
		setIdentifier(identifier);
	}
	
	@XmlTransient
	@JsonbTransient
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@XmlTransient
	@JsonbTransient
	public AbstractIdentifiableSystemScalarStringAuditedImpl setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
		
	@Override
	public String toString() {
		String identifier = getIdentifier();
		if(identifier == null)
			return super.toString();
		return identifier.toString();
	}	
}