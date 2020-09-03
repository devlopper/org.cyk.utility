package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl 
	extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements AuditableWhoDoneWhatWhen,Serializable {

	@Column(name="audit_event_who")
	protected String __auditWho__;
	
	@Column(name="audit_event_what")
	protected String __auditWhat__;
	
	@Column(name="audit_event_when")
	protected LocalDateTime __auditWhen__;
	
	/**/
}
