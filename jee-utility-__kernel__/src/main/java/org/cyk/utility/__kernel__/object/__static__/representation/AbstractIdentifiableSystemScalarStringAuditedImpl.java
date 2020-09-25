package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper = false,of = "identifier") @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringAuditedImpl extends AbstractIdentifiableSystemScalarImpl<String> implements AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String identifier;

	// Audit
	protected String __auditWho__;	
	protected String __auditWhat__;	
	protected String __auditFunctionality__;
	protected LocalDateTime __auditWhen__;
	protected Long __auditWhenAsTimestamp__;
	protected String __auditWhenAsString__;
	
	public AbstractIdentifiableSystemScalarStringAuditedImpl(String identifier) {
		setIdentifier(identifier);
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public AbstractIdentifiableSystemScalarStringAuditedImpl setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public LocalDateTime get__auditWhen__() {
		return null;
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public AuditableWhoDoneWhatWhen set__auditWhen__(LocalDateTime when) {
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