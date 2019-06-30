package org.cyk.utility.server.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class PersistenceQuery extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//private PersistenceQueryAggregateFunction aggregateFunction;
	
	@Override
	public PersistenceQuery setIdentifier(Object identifier) {
		return (PersistenceQuery) super.setIdentifier(identifier);
	}
	
	public String getValue(){
		return (String) getProperties().getValue();
	}
	
	public PersistenceQuery setValue(String value){
		getProperties().setValue(value);
		return this;
	}
	
	public Class<?> getResultClass(){
		return (Class<?>) getProperties().getClazz();
	}
	
	public PersistenceQuery setResultClass(Class<?> aClass){
		getProperties().setClass(aClass);
		return this;
	}
	
	public PersistenceQuery getQueryDerivedFromQuery(){
		return (PersistenceQuery) getProperties().getFromPath(Properties.FROM);
	}
	
	public PersistenceQuery setQueryDerivedFromQuery(PersistenceQuery query){
		getProperties().setFromPath(new Object[]{Properties.FROM},query);
		return this;
	}
	
	public PersistenceQuery setQueryDerivedFromQueryIdentifier(Object identifier){
		setQueryDerivedFromQuery(__inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier, Boolean.TRUE));
		return this;
	}
	
	public Boolean isQueryDerivedFromQueryIdentifierEqualsTo(Object identifer){
		return getQueryDerivedFromQuery()!=null && getQueryDerivedFromQuery().getIdentifier().equals(identifer);
	}
	
	public Boolean isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(Object value){
		Object identifier = getIdentifier();
		return identifier!=null && (identifier.equals(value) || isQueryDerivedFromQueryIdentifierEqualsTo(value));
	}
	
	/*
	public Boolean isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(Object queryIdentifier,Object value){
		return queryIdentifier.equals(value) || isQueryDerivedFromQueryIdentifierEqualsTo(queryIdentifier);
	}
	*/
	
	@Override
	public int hashCode() {
		Object identifier = getIdentifier();
		return identifier == null ? super.hashCode() : identifier.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		Object identifier1 = getIdentifier();
		Object identifier2 = ((PersistenceQuery)object).getIdentifier();
		return identifier1!=null && identifier2!=null && identifier1.equals(identifier2);
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, PersistenceQuery.class.getSimpleName(),getIdentifier(),getValue(),getResultClass());
	}
	
	private static final String TO_STRING_FORMAT = "%s(%s , %s , %s)";
}
