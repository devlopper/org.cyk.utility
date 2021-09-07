package org.cyk.utility.persistence.server;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.annotation.Persistence;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.server.query.RuntimeQueryBuilder;

@ApplicationScoped @Persistence
public class QueryManagerImpl extends org.cyk.utility.persistence.query.QueryManagerImpl {

	@Override
	protected void __register__(Collection<Query> queries) {
		super.__register__(queries);
		queries.stream().filter(query -> Boolean.FALSE.equals(query.getRegisterableToEntityManager())).forEach(query -> {
			RuntimeQueryBuilder.BUILDABLES.add(query.getIdentifier());
		});
	}
}