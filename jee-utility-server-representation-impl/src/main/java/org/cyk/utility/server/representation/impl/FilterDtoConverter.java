package org.cyk.utility.server.representation.impl;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.ext.ParamConverter;

import org.cyk.utility.__kernel__.persistence.query.filter.Filter;

public class FilterDtoConverter implements ParamConverter<Filter.Dto> {

	@Override
	public Filter.Dto fromString(String string) {
		return JsonbBuilder.create().fromJson(string, Filter.Dto.class);		
	}

	@Override
	public String toString(Filter.Dto filterDto) {
		return JsonbBuilder.create().toJson(filterDto);
	}

}