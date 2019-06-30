package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.sql.builder.QueryParameterNameBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

@Singleton
public class MyEntityIdentifiedByStringPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntityIdentifiedByString> implements MyEntityIdentifiedByStringPersistence,Serializable {
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
	public Collection<MyEntityIdentifiedByString> readByIntegerValue(Integer value) {
		return __readMany__(null,____getQueryParameters____(null,value));
	}
	
	@Override 
	//@Query(value="SELECT COUNT(r) FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=Long.class)
	public Long countByIntegerValue(Integer value) {
		return __count__(null,____getQueryParameters____(null,value));
	}
	
	@Override
	public Long executeIncrementIntegerValue(Integer value) {
		return __modify__(null,____getQueryParameters____(null,value));
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQuery query, Properties properties, Object... objects) {
		if(query.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByIntegerValue))
			return new Object[]{MyEntity.FIELD_INTEGER_VALUE,objects[0]};
		if(executeIncrementIntegerValue.equals(query.getIdentifier()))
			return new Object[]{QueryParameterNameBuilder.VALUE,objects[0]};
		return super.__getQueryParameters__(query, properties, objects);
	}
	
}
