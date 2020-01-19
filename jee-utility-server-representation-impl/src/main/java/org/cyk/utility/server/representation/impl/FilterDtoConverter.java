package org.cyk.utility.server.representation.impl;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.ext.ParamConverter;

import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;

public class FilterDtoConverter implements ParamConverter<FilterDto> {

	@Override
	public FilterDto fromString(String string) {
		return JsonbBuilder.create().fromJson(string, FilterDto.class);		
	}

	@Override
	public String toString(FilterDto filterDto) {
		return JsonbBuilder.create().toJson(filterDto);
	}

}