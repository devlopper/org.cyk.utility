package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
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
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}