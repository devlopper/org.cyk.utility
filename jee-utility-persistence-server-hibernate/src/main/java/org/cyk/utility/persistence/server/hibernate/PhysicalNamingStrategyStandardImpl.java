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
			return Identifier.toIdentifier("identifiant");
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("libelle");
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHO__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("audit_acteur");
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHAT__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("audit_action");
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_WHEN__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("audit_date");
		if(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringAuditedImpl.FIELD___AUDIT_FUNCTIONALITY__.equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("audit_fonctionalite");
		return super.toPhysicalColumnName(identifier, jdbcEnvironment);
	}   
}