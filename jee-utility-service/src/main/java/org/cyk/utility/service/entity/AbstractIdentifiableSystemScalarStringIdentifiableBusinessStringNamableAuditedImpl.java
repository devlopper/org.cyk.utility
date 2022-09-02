package org.cyk.utility.service.entity;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Namable,AuditableWhoDoneWhatWhen,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	// Audit
	protected String __audit__;
	protected String __auditWho__;	
	protected String __auditWhat__;	
	protected String __auditFunctionality__;
	protected Long __auditWhenAsTimestamp__;
	protected String __auditWhenAsString__;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl(String identifier,String code,String name) {
		super(identifier,code);
		setName(name);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl(String code,String name) {
		super(code);
		setName(name);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl(String name) {
		setName(name);
	}
		
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(code))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
}