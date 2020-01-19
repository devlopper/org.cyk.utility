package org.cyk.utility.__kernel__.persistence.query.filter;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.mapstruct.Mapper;

@Mapper
public abstract class FilterDtoMapper extends AbstractMapperSourceDestinationImpl<FilterDto, Filter> {
	private static final long serialVersionUID = 1L;
	
	public Class<?> getKlass(String name) {
		return StringHelper.isBlank(name) ? null : ClassHelper.getByName(name);
	}
	
	public String getClassName(Class<?> klass) {
		return klass == null ? null : klass.getName();
	}
	
	public Fields getFields(FieldDtoCollection fieldDtoCollection) {
		Fields fields = null;
		if(fieldDtoCollection != null && fieldDtoCollection.getCollection()!=null && !fieldDtoCollection.getCollection().isEmpty()) {
			fields = DependencyInjection.inject(Fields.class);
			for(FieldDto index : fieldDtoCollection.getCollection())
				fields.add(MappingHelper.getDestination(index, Field.class));
		}
		return fields;
	}
	
	public FieldDtoCollection getFieldDtoCollection(Fields fields) {
		FieldDtoCollection fieldDtoCollection = null;
		if(fields != null && fields.get()!=null && !fields.get().isEmpty()) {
			fieldDtoCollection = new FieldDtoCollection();
			for(Field index : fields.get())
				fieldDtoCollection.add(MappingHelper.getDestination(index, FieldDto.class));
		}
		return fieldDtoCollection;
	}
	
	@Override
	protected void __listenGetDestinationAfter__(FilterDto source, Filter destination) {
		
	}
	
}