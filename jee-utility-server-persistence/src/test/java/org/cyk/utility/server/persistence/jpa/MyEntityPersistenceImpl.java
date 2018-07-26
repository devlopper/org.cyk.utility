package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.sql.jpql.builder.QueryStringBuilderSelectJpql;
import org.cyk.utility.sql.jpql.builder.QueryWherePredicateStringBuilderEqualJpql;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		
		Tuple tuple = new Tuple().setName(getEntityClass().getSimpleName());
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
				.addOperandBuilderByAttribute(MyEntity.FIELD_INTEGER_VALUE,tuple);
		QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple).where(predicateBuilder);
		addQueryCollectInstances(readByIntegerValue, queryBuilder.execute().getOutput());
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
