package org.cyk.utility.server.representation.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.server.representation.RepresentationEntity;

@Provider
public class ParameterConverterProvider  implements ParamConverterProvider {

	private static final FilterDtoConverter FILTER_DTO_CONVERTER = new FilterDtoConverter();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if(Filter.Dto.class.equals(rawType))
			return (ParamConverter<T>) FILTER_DTO_CONVERTER;	
		if(annotations!=null && annotations.length==1 && annotations[0] instanceof QueryParam) {
			QueryParam queryParam = (QueryParam) annotations[0];
			if(queryParam != null && RepresentationEntity.PARAMETER_FILTERS.equals(queryParam.value())) {
				return (ParamConverter<T>) FILTER_DTO_CONVERTER;
			}
		}
		return null;
	}

}