package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		
		QueryStringBuilderSelect queryBuilder = __instanciateQuerySelect__()
				.getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute(MyEntity.FIELD_INTEGER_VALUE)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);

		addQueryCollectInstances(readByIntegerValue, queryBuilder);
	}
	
	@Override 
	//@Query(value="SELECT r FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=MyEntity.class)
	public Collection<MyEntity> readByIntegerValue(Integer value) {
		return __readMany__(__getQueryParameters__(value));
	}
	
	@Override 
	//@Query(value="SELECT COUNT(r) FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=Long.class)
	public Long countByIntegerValue(Integer value) {
		return __count__(__getQueryParameters__(value));
	}
	
	protected Object[] __getQueryParameters__(String queryIdentifier,Object...objects){
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
		if(persistenceQuery.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByIntegerValue,queryIdentifier))
			return new Object[]{MyEntity.FIELD_INTEGER_VALUE,objects[0]};
		return super.__getQueryParameters__(queryIdentifier, objects);
	}
	
	
}
