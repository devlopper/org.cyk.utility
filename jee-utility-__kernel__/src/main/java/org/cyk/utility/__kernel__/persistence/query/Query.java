package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of = {"identifier"},callSuper = false)
public class Query extends AbstractObject implements Serializable {

	private Class<?> tupleClass;
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
	
	/**/
	
	public static final String FIELD_TUPLE_CLASS = "tupleClass";
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_RESULT_CLASS = "resultClass";
	
	/**/
	
	public static class ConfiguratorImpl extends Configurator.AbstractImpl<Query> implements Serializable {
		
		@Override
		public void configure(Query query, Map<Object, Object> arguments) {
			super.configure(query, arguments);
			if(query.identifier == null) {
				if(query.tupleClass != null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_NAME))) {
					query.identifier = QueryIdentifierBuilder.getInstance().build(query.tupleClass, (String) MapHelper.readByKey(arguments, FIELD_NAME));
				}
			}
			if(query.resultClass == null) {
				if(StringHelper.isNotBlank(query.value) && query.value.toLowerCase().startsWith("select"))
					query.resultClass = query.tupleClass;
			}
		}
		
		@Override
		protected Class<Query> __getClass__() {
			return Query.class;
		}
		
		public static final String FIELD_NAME = "name";
	}
	
	public static Query build(Map<Object,Object> arguments) {
		return Builder.build(Query.class,arguments);
	}
	
	public static Query build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Query.class, new ConfiguratorImpl());
	}
	
	private static final String TO_STRING_FORMAT = "%s(%s , %s , %s)";
}
