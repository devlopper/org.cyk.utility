package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringAuditedImpl extends AbstractIdentifiableSystemScalarStringImpl implements AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="audit_event_who")
	protected String __auditWho__;
	
	@Column(name="audit_event_what")
	protected String __auditWhat__;
	
	@Column(name="audit_event_functionality")
	protected String __auditFunctionality__;
	
	@Column(name="audit_event_when")
	protected LocalDateTime __auditWhen__;
	
	@Transient protected Integer __auditRevision__;
	@Transient protected Long __auditWhenAsTimestamp__;
	@Transient protected String __auditWhenAsString__;
	
	public static final String FIELD___AUDIT_WHO__ = "__auditWho__";
	public static final String FIELD___AUDIT_WHAT__ = "__auditWhat__";
	public static final String FIELD___AUDIT_FUNCTIONALITY__ = "__auditFunctionality__";
	public static final String FIELD___AUDIT_WHEN__ = "__auditWhen__";
	public static final String FIELD___AUDIT_WHEN_AS_TIMESTAMP__ = "__auditWhenAsTimestamp__";
	public static final String FIELD___AUDIT_WHEN_AS_STRING__ = "__auditWhenAsString__";
}