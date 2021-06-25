package org.cyk.utility.persistence.server.hibernate.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass @Getter @Setter @Accessors(chain=true)
public abstract class AbstractAudit implements Serializable {

	@Id @Column(name = COLUMN___AUDIT_REVISION__)
	protected Integer __auditRevision__;	

	@Column(name=COLUMN___AUDIT_WHO__)
	protected String __auditWho__;
	
	@Column(name=COLUMN___AUDIT_WHAT__)
	protected String __auditWhat__;
	
	@Column(name=COLUMN___AUDIT_FUNCTIONALITY__)
	protected String __auditFunctionality__;
	
	@Column(name=COLUMN___AUDIT_WHEN__)
	protected LocalDateTime __auditWhen__;
	
	@Transient
	protected Long __auditWhenAsTimestamp__;
	
	@Transient
	protected String __auditWhenAsString__;
	
	/**/
	
	public static final String FIELD___AUDIT_REVISION__ = "__auditRevision__";
	public static final String FIELD___AUDIT_WHO__ = "__auditWho__";
	public static final String FIELD___AUDIT_WHAT__ = "__auditWhat__";
	public static final String FIELD___AUDIT_WHEN__ = "__auditWhen__";
	public static final String FIELD___AUDIT_WHEN_AS_STRING__ = "__auditWhenAsString__";
	public static final String FIELD___AUDIT_FUNCTIONALITY__ = "__auditFunctionality__";
	
	/**/
	
	public static final String COLUMN___AUDIT_REVISION__ = "REV";
	public static final String COLUMN___AUDIT_WHO__ = "audit_event_who";
	public static final String COLUMN___AUDIT_WHAT__ = "audit_event_what";
	public static final String COLUMN___AUDIT_WHEN__ = "audit_event_when";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "audit_event_functionality";
}