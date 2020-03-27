package org.cyk.utility.__kernel__.persistence.query;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.mapstruct.Mapper;

@Mapper
public abstract class QueryExecutorArgumentsDtoMapper extends MapperSourceDestination.AbstractImpl<QueryExecutorArgumentsDto, QueryExecutorArguments> {

	@Override
	protected void __listenGetDestinationAfter__(QueryExecutorArgumentsDto source, QueryExecutorArguments destination) {
		super.__listenGetDestinationAfter__(source, destination);
		if(StringHelper.isNotBlank(source.getQueryIdentifier()))
			destination.setQuery(QueryGetter.getInstance().get(source.getQueryIdentifier()));
		if(destination.getQuery() == null)
			destination.setQuery(new Query().setIdentifier(source.getQueryIdentifier()));
	}
	
	public FilterDto getFilterDto(Filter filter) {
		if(filter == null)
			return null;
		return MappingHelper.getSource(filter, FilterDto.class);
	}
	
	public Filter getFilter(FilterDto filter) {
		if(filter == null)
			return null;
		return MappingHelper.getDestination(filter, Filter.class);
	}
}