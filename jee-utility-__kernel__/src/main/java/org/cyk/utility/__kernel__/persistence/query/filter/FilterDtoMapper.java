package org.cyk.utility.__kernel__.persistence.query.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

//@org.mapstruct.Mapper
@Deprecated
public abstract class FilterDtoMapper extends MapperSourceDestination.AbstractImpl<FilterDto, Filter> {
	private static final long serialVersionUID = 1L;
	
	public Class<?> getKlass(String name) {
		return StringHelper.isBlank(name) ? null : ClassHelper.getByName(name);
	}
	
	public String getClassName(Class<?> klass) {
		return klass == null ? null : klass.getName();
	}
	
	public Collection<Field> getFields(ArrayList<Field.Dto> dtos) {
		if(CollectionHelper.isEmpty(dtos))
			return null;
		Collection<Field> fields = new ArrayList<>();
		for(Field.Dto index : dtos)
			fields.add(MappingHelper.getDestination(index, Field.class));
		return fields;
	}
	
	public ArrayList<Field.Dto> getFieldDtos(Collection<Field> fields) {
		if(CollectionHelper.isEmpty(fields))
			return null;
		ArrayList<Field.Dto> dtos = new ArrayList<>();		
		for(Field index : fields)
			dtos.add(MappingHelper.getDestination(index, Field.Dto.class));
		return dtos;
	}
	
	@Override
	protected void __listenGetDestinationAfter__(FilterDto source, Filter destination) {
		
	}
	
}