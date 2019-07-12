package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityIdentifiedByStringImpl;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.sql.builder.QueryParameterNameBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

@ApplicationScoped
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityIdentifiedByStringImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue,countByIntegerValue,executeIncrementIntegerValue;
	
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
		Properties properties = new Properties().setQueryIdentifier(readByIntegerValue);
		return __readMany__(properties,____getQueryParameters____(properties,value));
	}
	
	@Override 
	//@Query(value="SELECT COUNT(r) FROM MyEntity r WHERE r.integerValue = :"+MyEntity.FIELD_INTEGER_VALUE,resultClass=Long.class)
	public Long countByIntegerValue(Integer value) {
		Properties properties = new Properties().setQueryIdentifier(countByIntegerValue);
		return __count__(properties,____getQueryParameters____(properties,value));
	}
	
	@Override
	public Long executeIncrementIntegerValue(Integer value) {
		Properties properties = new Properties().setQueryIdentifier(executeIncrementIntegerValue);
		return __modify__(properties,____getQueryParameters____(properties,value));
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
