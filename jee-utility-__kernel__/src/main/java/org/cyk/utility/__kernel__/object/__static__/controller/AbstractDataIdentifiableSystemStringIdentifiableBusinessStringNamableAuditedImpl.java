package org.cyk.utility.__kernel__.object.__static__.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Column;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter /*@Accessors(chain=true)*/
public abstract class AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableAuditedImpl extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl implements Namable,AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @NotNull
	@Column
	protected String name;

	// Audit
	protected String __auditIdentifier__;
	protected String __auditWho__;	
	protected String __auditWhat__;	
	protected String __auditFunctionality__;
	protected LocalDateTime __auditWhen__;
	protected Long __auditWhenAsTimestamp__;
	protected String __auditWhenAsString__;
	
	public AuditableWhoDoneWhatWhen set__auditIdentifier__(String identifier) {
		this.__auditIdentifier__ = identifier;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditWhenAsString__(String whenAsString) {
		this.__auditWhenAsString__ = whenAsString;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditWhenAsTimestamp__(Long whenAsTimestamp) {
		this.__auditWhenAsTimestamp__ = whenAsTimestamp;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditWhen__(LocalDateTime when) {
		this.__auditWhen__ = when;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditFunctionality__(String functionality) {
		this.__auditFunctionality__ = functionality;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditWhat__(String what) {
		this.__auditWhat__ = what;
		return this;
	}
	
	public AuditableWhoDoneWhatWhen set__auditWho__(String who) {
		this.__auditWho__ = who;
		return this;
	}
	
	public Namable setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String toString() {
		String string = getName();
		if(StringHelper.isBlank(string))
			string = super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
	
	public static final String FIELD___AUDIT_WHO__ = "__auditWho__";
	public static final String FIELD___AUDIT_WHAT__ = "__auditWhat__";
	public static final String FIELD___AUDIT_FUNCTIONALITY__ = "__auditFunctionality__";
	public static final String FIELD___AUDIT_WHEN__ = "__auditWhen__";
	public static final String FIELD___AUDIT_WHEN_AS_TIMESTAMP__ = "__auditWhenAsTimestamp__";
	public static final String FIELD___AUDIT_WHEN_AS_STRING__ = "__auditWhenAsString__";
}