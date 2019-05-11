package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.sql.builder.QueryParameterNameBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.throwable.ThrowableHelper;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue,executeIncrementIntegerValue;
	
	@Override
	public QueryStringBuilderSelect instanciateReadByIntegerValueQueryStringBuilder() {
		return __instanciateQuerySelect__()
				.getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute(MyEntity.FIELD_INTEGER_VALUE,ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);
	}
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		
		addQueryCollectInstances(readByIntegerValue, instanciateReadByIntegerValueQueryStringBuilder());
		
		addQuery(executeIncrementIntegerValue, "UPDATE MyEntity myEntity SET myEntity.integerValue = myEntity.integerValue + :value", null);
	}
	
	@Override 
	//@Query(value="SELECT r FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=MyEntity.class)
	public Collection<MyEntity> readByIntegerValue(Integer value) {
		return __readMany__(null,____getQueryParameters____(null,value));
	}
	
	@Override 
	//@Query(value="SELECT COUNT(r) FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=Long.class)
	public Long countByIntegerValue(Integer value) {
		return __count__(____getQueryParameters____(null,value));
	}
	
	@Override
	public Long executeIncrementIntegerValue(Integer value) {
		return __modify__(____getQueryParameters____(null,value));
	}
	
	protected Object[] __getQueryParameters__(String queryIdentifier,Properties properties,Object...objects){
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
		if(persistenceQuery == null)
			__inject__(ThrowableHelper.class).throwRuntimeException("persistence query with identifier "+queryIdentifier+" not found.");
		if(persistenceQuery.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByIntegerValue,queryIdentifier))
			return new Object[]{MyEntity.FIELD_INTEGER_VALUE,objects[0]};
		if(executeIncrementIntegerValue.equals(queryIdentifier))
			return new Object[]{QueryParameterNameBuilder.VALUE,objects[0]};
		
		return super.__getQueryParameters__(queryIdentifier,properties, objects);
	}
	
	
}
