package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of = {"identifier"},callSuper = false)
public class Query extends AbstractObject implements Serializable {

	private Object identifier;
	private String value;
	private Class<?> resultClass;
	private Query queryDerivedFromQuery;
	
	public Query setQueryDerivedFromQueryIdentifier(Object identifier){
		setQueryDerivedFromQuery(QueryHelper.getQueries().getBySystemIdentifier(identifier, Boolean.TRUE));
		return this;
	}
	
	public Boolean isQueryDerivedFromQueryIdentifierEqualsTo(Object identifer){
		return getQueryDerivedFromQuery()!=null && getQueryDerivedFromQuery().getIdentifier().equals(identifer);
	}
	
	public Boolean isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(Object value){
		Object identifier = getIdentifier();
		return identifier!=null && (identifier.equals(value) || isQueryDerivedFromQueryIdentifierEqualsTo(value));
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, Query.class.getSimpleName(),getIdentifier(),getValue(),getResultClass());
	}
	
	private static final String TO_STRING_FORMAT = "%s(%s , %s , %s)";
	
}
