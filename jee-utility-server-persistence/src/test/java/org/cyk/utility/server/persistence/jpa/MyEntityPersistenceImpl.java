package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceQueryIdentifierStringBuilder;
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
		return __count__(__getQueryParameters__(readByIntegerValue,value));
	}
	
	protected Object[] __getQueryParameters__(String queryIdentifier,Object...objects){
		if(readByIntegerValue.equals(queryIdentifier))
			return new Object[]{MyEntity.FIELD_INTEGER_VALUE,objects[0]};
		//TODO log warning
		return null;
	}
	
	protected Object[] __getQueryParameters__(Object...objects){
		String queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(getEntityClass())
				.setName(__inject__(StackTraceHelper.class).getAt(3).getMethodName()).execute().getOutput();
		return __getQueryParameters__(queryIdentifier, objects);
	}
	
}
