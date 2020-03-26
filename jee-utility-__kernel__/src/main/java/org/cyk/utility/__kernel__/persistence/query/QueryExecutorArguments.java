package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class QueryExecutorArguments extends AbstractObject implements Serializable {
	private Query query;
	private Map<Object,Object> parameters;
	private Filter filter;
	private Integer firstTupleIndex;
	private Integer numberOfTuples;
	private Collection<Object> objects;
	private Boolean isTransactional;
	private EntityManager entityManager;
	
	public Map<Object,Object> getParameters(Boolean injectIfNull) {
		if(parameters == null && Boolean.TRUE.equals(injectIfNull))
			parameters = new HashMap<>();
		return parameters;
	}
	
	public QueryExecutorArguments setParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public QueryExecutorArguments addParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public Filter getFilter(Boolean injectIfNull) {
		if(filter == null && Boolean.TRUE.equals(injectIfNull))
			filter = new Filter();
		return filter;
	}
	
	public QueryExecutorArguments addFilterField(String fieldName, Object fieldValue) {
		getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
		return this;
	}
	
	public Collection<Object> getObjects(Boolean injectIfNull) {
		if(objects == null && Boolean.TRUE.equals(injectIfNull))
			objects = new ArrayList<>();
		return objects;
	}
	
	public QueryExecutorArguments addObjects(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return this;
		getObjects(Boolean.TRUE).addAll(objects);
		return this;
	}
	
	public QueryExecutorArguments addObjects(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return this;
		addObjects(CollectionHelper.listOf(objects));
		return this;
	}
}