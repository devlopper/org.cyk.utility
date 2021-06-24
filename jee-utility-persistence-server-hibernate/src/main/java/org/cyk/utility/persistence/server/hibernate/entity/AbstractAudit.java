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

	@Id @Column(name = COLUMN___REVISION__)
	protected Integer __revision__;	

	@Column(name=COLUMN___WHO__)
	protected String __who__;
	
	@Column(name=COLUMN___WHAT__)
	protected String __what__;
	
	@Column(name=COLUMN___FUNCTIONALITY__)
	protected String __functionality__;
	
	@Column(name=COLUMN___WHEN__)
	protected LocalDateTime __when__;
	
	@Transient
	protected Long __whenAsTimestamp__;
	
	@Transient
	protected String __whenAsString__;
	
	/**/
	
	public static final String FIELD___REVISION__ = "__revision__";
	public static final String FIELD___WHO__ = "__who__";
	public static final String FIELD___WHAT__ = "__what__";
	public static final String FIELD___WHEN__ = "__when__";
	public static final String FIELD___WHEN_AS_STRING__ = "__whenAsString__";
	public static final String FIELD___FUNCTIONALITY__ = "__functionality__";
	
	/**/
	
	public static final String COLUMN___REVISION__ = "REV";
	public static final String COLUMN___WHO__ = "audit_event_who";
	public static final String COLUMN___WHAT__ = "audit_event_what";
	public static final String COLUMN___WHEN__ = "audit_event_when";
	public static final String COLUMN___FUNCTIONALITY__ = "audit_event_functionality";
}