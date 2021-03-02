package org.cyk.utility.server.representation.impl;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.ext.ParamConverter;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.persistence.query.Filter;

public class FilterDtoConverter implements ParamConverter<Filter.Dto> {

	@Override
	public Filter.Dto fromString(String string) {
		try {
			return JsonbBuilder.create().fromJson(string, Filter.Dto.class);
		} catch (JsonbException exception) {
			LogHelper.logSevere("FilterDtoConverter.fromString() : We cannot build Filter.Dto from "+string, getClass());
			LogHelper.log(exception, getClass());
			return null;
		}
	}

	@Override
	public String toString(Filter.Dto filterDto) {
		try {
			return JsonbBuilder.create().toJson(filterDto);
		} catch (JsonbException exception) {
			LogHelper.logSevere("FilterDtoConverter.toString() : We cannot build String from filter "+filterDto,getClass());
			LogHelper.log(exception, getClass());
			return null;
		}
	}
}