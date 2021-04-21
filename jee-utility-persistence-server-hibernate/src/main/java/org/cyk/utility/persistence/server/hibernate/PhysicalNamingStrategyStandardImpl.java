package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PhysicalNamingStrategyStandardImpl extends org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl {

	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		if(AbstractIdentifiableSystemScalarStringImpl.FIELD_IDENTIFIER.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN_IDENTIFIER);
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN_NAME);
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHO__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN___AUDIT_WHO__);
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHAT__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN___AUDIT_WHAT__);
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHEN__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN___AUDIT_FUNCTIONALITY__);
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_FUNCTIONALITY__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier(COLUMN___AUDIT_WHEN__);
		return super.toPhysicalColumnName(identifier, jdbcEnvironment);
	}
	
	/**/
	
	public static final String COLUMN_IDENTIFIER = "identifiant";
	public static final String COLUMN_NAME = "libelle";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN___AUDIT_WHO__ = "audit_acteur";
	public static final String COLUMN___AUDIT_WHAT__ = "audit_action";
	public static final String COLUMN___AUDIT_FUNCTIONALITY__ = "audit_date";
	public static final String COLUMN___AUDIT_WHEN__ = "audit_fonctionalite";
}