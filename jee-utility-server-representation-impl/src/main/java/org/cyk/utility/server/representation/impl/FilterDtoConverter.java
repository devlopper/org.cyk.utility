package org.cyk.utility.server.representation.impl;

import javax.ws.rs.ext.ParamConverter;

import org.cyk.utility.server.persistence.query.filter.FilterDto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FilterDtoConverter implements ParamConverter<FilterDto> {

	@Override
	public FilterDto fromString(String string) {
		try {
			return new ObjectMapper().readValue(string, FilterDto.class);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public String toString(FilterDto filterDto) {
		try {
			return new ObjectMapper().writeValueAsString(filterDto);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

        

}