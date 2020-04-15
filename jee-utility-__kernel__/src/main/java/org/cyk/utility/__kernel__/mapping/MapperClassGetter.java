package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperClassGetter {

	Class<?> get(Class<?> klass);

	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements MapperClassGetter,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> get(Class<?> klass) {
			if(klass == null)
				return null;
			return __get__(klass);
		}
		
		protected Class<?> __get__(Class<?> klass) {
			if(klass.equals(org.cyk.utility.__kernel__.field.Field.class) || klass.equals(org.cyk.utility.__kernel__.field.Field.Dto.class))
				return org.cyk.utility.__kernel__.field.Field.Dto.Mapper.class;
			if(klass.equals(Message.class) || klass.equals(Message.Dto.class))
				return Message.Dto.Mapper.class;
			if(klass.equals(RuntimeException.class) || klass.equals(RuntimeException.Dto.class))
				return RuntimeException.Dto.Mapper.class;
			if(klass.equals(QueryExecutorArguments.class) || klass.equals(QueryExecutorArguments.Dto.class))
				return QueryExecutorArguments.Dto.Mapper.class;
			if(klass.equals(Value.class) || klass.equals(Value.Dto.class))
				return Value.Dto.Mapper.class;
			if(klass.equals(Filter.class) || klass.equals(Filter.Dto.class))
				return Filter.Dto.Mapper.class;
			if(klass.equals(org.cyk.utility.__kernel__.persistence.query.filter.Field.class) || klass.equals(org.cyk.utility.__kernel__.persistence.query.filter.Field.Dto.class))
				return org.cyk.utility.__kernel__.persistence.query.filter.Field.Dto.Mapper.class;
			Class<?> result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(MapperClassNameGetter.getInstance().get(klass));
			if(result == null) {
				if(klass.getName().endsWith("Impl"))
					result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(MapperClassNameGetter.getInstance().get(StringUtils.substringBeforeLast(klass.getName(), "Impl")));
			}
			return result;
		}
	}
	
	/**/
	
	static MapperClassGetter getInstance() {
		MapperClassGetter instance = (MapperClassGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MapperClassGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MapperClassGetter.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
}