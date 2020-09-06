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

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="audit_event_who")
	protected String __auditWho__;
	
	@Column(name="audit_event_what")
	protected String __auditWhat__;
	
	@Column(name="audit_event_functionality")
	protected String __auditFunctionality__;
	
	@Column(name="audit_event_when")
	protected LocalDateTime __auditWhen__;
	
	public static final String FIELD___AUDIT_WHO__ = "__auditWho__";
	public static final String FIELD___AUDIT_WHAT__ = "__auditWhat__";
	public static final String FIELD___AUDIT_FUNCTIONALITY__ = "__auditFunctionality__";
	public static final String FIELD___AUDIT_WHEN__ = "__auditWhen__";
}
