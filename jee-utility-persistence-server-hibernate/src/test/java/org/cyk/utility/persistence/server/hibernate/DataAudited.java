package org.cyk.utility.persistence.server.hibernate;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name = DataAudited.TABLE_NAME) @Getter @Setter @Accessors(chain=true)
@AttributeOverrides(value= {
		@AttributeOverride(name = DataAudited.FIELD___AUDIT_WHO__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHO__))
		,@AttributeOverride(name = DataAudited.FIELD___AUDIT_WHAT__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHAT__))
		,@AttributeOverride(name = DataAudited.FIELD___AUDIT_WHEN__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHEN__))
		,@AttributeOverride(name = DataAudited.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=DataAudited.COLUMN___AUDIT_FUNCTIONALITY__))
})
@AuditOverrides(value = {
	@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl.class)
	,@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.class)
	,@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.class)
	//,@AuditOverride(forClass = AbstractIdentifiableSystemScalarStringImpl.class)
})
@Audited @AuditTable(value = DataAudited.AUDIT_TABLE_NAME)
public class DataAudited extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableAuditedImpl {

	@Transient private Collection<DataAudited> __auditRecords__;
	
	@Override
	public DataAudited setIdentifier(String identifier) {
		return (DataAudited) super.setIdentifier(identifier);
	}
	
	@Override
	public DataAudited setCode(String code) {
		return (DataAudited) super.setCode(code);
	}
	
	@Override
	public DataAudited setName(String name) {
		return (DataAudited) super.setName(name);
	}
	
	public static final String TABLE_NAME = "DATAAUDITED";
	public static final String AUDIT_TABLE_NAME = TABLE_NAME+"_AUD";
	
	public static final String COLUMN_IDENTIFIER = "IDENTIFIER";
	public static final String COLUMN___AUDIT_WHO__ = "AUDIT_ACTEUR";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "AUDIT_FONCTIONNALITE";
	public static final String COLUMN___AUDIT_WHAT__ = "AUDIT_ACTION";
	public static final String COLUMN___AUDIT_WHEN__ = "AUDIT_DATE";
	
}