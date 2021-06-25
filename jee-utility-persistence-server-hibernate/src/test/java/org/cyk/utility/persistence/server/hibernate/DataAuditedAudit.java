package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@AttributeOverrides(value= {
		@AttributeOverride(name = DataAuditedAudit.FIELD___AUDIT_WHO__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHO__))
		,@AttributeOverride(name = DataAuditedAudit.FIELD___AUDIT_WHAT__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHAT__))
		,@AttributeOverride(name = DataAuditedAudit.FIELD___AUDIT_WHEN__,column = @Column(name=DataAudited.COLUMN___AUDIT_WHEN__))
		,@AttributeOverride(name = DataAuditedAudit.FIELD___AUDIT_FUNCTIONALITY__,column = @Column(name=DataAudited.COLUMN___AUDIT_FUNCTIONALITY__))
})
@Entity @Table(name = DataAuditedAudit.TABLE_NAME) @Getter @Setter @Accessors(chain=true)
public class DataAuditedAudit extends AbstractAuditIdentifiedByString implements Serializable {

	private String code;	
	private String name;
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	
	public static final String TABLE_NAME = DataAudited.AUDIT_TABLE_NAME;
}