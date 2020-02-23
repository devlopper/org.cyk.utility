package org.cyk.utility.server.persistence.impl.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PhysicalNamingStrategyStandardImpl extends org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl {

	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		if("identifier".equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("identifiant");
		if("name".equals(identifier.getCanonicalName()))
			return Identifier.toIdentifier("libelle");
		return super.toPhysicalColumnName(identifier, jdbcEnvironment);
	}
    
}