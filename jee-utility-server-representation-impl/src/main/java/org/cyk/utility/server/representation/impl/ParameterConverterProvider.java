package org.cyk.utility.server.representation.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.server.persistence.query.filter.FilterDto;

@Provider
public class ParameterConverterProvider  implements ParamConverterProvider {

	private static final FilterDtoConverter FILTER_DTO_CONVERTER = new FilterDtoConverter();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if(FilterDto.class.equals(rawType))
			return (ParamConverter<T>) FILTER_DTO_CONVERTER;
		return null;
	}

}