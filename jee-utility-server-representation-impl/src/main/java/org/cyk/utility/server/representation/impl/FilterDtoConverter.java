package org.cyk.utility.server.representation.impl;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.ext.ParamConverter;

import org.cyk.utility.__kernel__.persistence.query.filter.Filter;

public class FilterDtoConverter implements ParamConverter<Filter.Dto> {

	@Override
	public Filter.Dto fromString(String string) {
		try {
			return JsonbBuilder.create().fromJson(string, Filter.Dto.class);
		} catch (JsonbException exception) {
			System.out.println("FilterDtoConverter.fromString() : We cannot build Filter.Dto from "+string);
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString(Filter.Dto filterDto) {
		try {
			return JsonbBuilder.create().toJson(filterDto);
		} catch (JsonbException exception) {
			System.out.println("FilterDtoConverter.toString() : We cannot build String from filter "+filterDto);
			exception.printStackTrace();
			return null;
		}
	}

}