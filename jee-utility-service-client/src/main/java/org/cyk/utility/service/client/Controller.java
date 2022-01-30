package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.rest.ResponseHelper;
import org.cyk.utility.service.FilterFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Controller {

	public static class AbstractImpl extends AbstractObject implements Controller,Serializable {
	
		@Inject
		protected SpecificServiceGetter specificServiceGetter;
		
		protected Response serve(Service service) {
			try {
				return service.execute();
			} catch (WebApplicationException exception) {
				throw new RuntimeException(ResponseHelper.getEntity(String.class, exception.getResponse()));
			}
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class GetArguments extends AbstractObject implements Serializable {
		private Filter.Dto filter;
		private String filterAsString;
		private FilterFormat filterFormat;
		private List<String> projections;
		private Boolean countable;
		private Boolean pageable;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		
		public String buildFilterAsString() {
			if(StringHelper.isBlank(filterAsString) && filter != null)
				filterAsString = JsonbBuilder.create().toJson(filter);
			return filterAsString;
		}
		
		public FilterFormat buildFilterFormat() {
			if(filter != null)
				return FilterFormat.JSON;
			if(StringHelper.isNotBlank(filterAsString))
				return FilterFormat.PLAIN;
			return null;
		}
		
		public List<String> getProjections(Boolean instantiateIfNull) {
			if(projections == null && Boolean.TRUE.equals(instantiateIfNull))
				projections = new ArrayList<>();
			return projections;
		}
		
		public GetArguments projections(Collection<String> projections) {
			if(CollectionHelper.isEmpty(projections))
				return this;
			getProjections(Boolean.TRUE).addAll(projections);
			return this;
		}
		
		public GetArguments projections(String...projections) {
			if(ArrayHelper.isEmpty(projections))
				return this;
			return projections(CollectionHelper.listOf(Boolean.TRUE,projections));
		}
	}

	public static interface Service {
		Response execute();
	}
}