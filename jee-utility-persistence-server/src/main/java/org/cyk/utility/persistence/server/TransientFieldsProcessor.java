package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface TransientFieldsProcessor {

	void process(Arguments arguments);	
	void process(Collection<?> objects,Collection<String> fieldsNames);
	void process(Collection<?> objects,String...fieldsNames);
	
	public static abstract class AbstractImpl extends AbstractObject implements TransientFieldsProcessor,Serializable {
		
		@Override
		public void process(Arguments arguments) {
			if(arguments == null || CollectionHelper.isEmpty(arguments.fieldsNames))
				return;
			__process__(arguments.klass, arguments.objects,arguments.fieldsNames);
		}
		
		protected void __process__(Class<?> klass,Collection<?> objects,Collection<String> fieldsNames) {
			for(String fieldName : fieldsNames) {
				if(StringHelper.isBlank(fieldName))
					continue;
				Boolean processed = __process__(klass,objects,fieldName);
				if(!Boolean.TRUE.equals(processed))
					logFieldNameHasNotBeenSet(klass, fieldName);
			}
		}
		
		protected Boolean __process__(Class<?> klass,Collection<?> objects,String fieldName) {
			return Boolean.TRUE;
		}
		
		protected void logFieldNameHasNotBeenSet(Class<?> klass,String fieldName) {
			LogHelper.logWarning(String.format("Transient field name <<%s.%s>> has not been processed", klass.getName(),fieldName), getClass());
		}
		
		@Override
		public void process(Collection<?> objects,Collection<String> fieldsNames) {
			if(CollectionHelper.isEmpty(objects) || CollectionHelper.isEmpty(fieldsNames))
				return;
			process(new Arguments().setKlass(objects.iterator().next().getClass()).setObjects(objects).setFieldsNames(fieldsNames));
		}
		
		@Override
		public void process(Collection<?> objects,String... fieldsNames) {
			if(ArrayHelper.isEmpty(fieldsNames))
				return;
			process(objects,CollectionHelper.listOf(fieldsNames));
		}
		
		protected String ifTrueYesElseNo(Class<?> klass,String fieldName,Boolean value) {
			return org.cyk.utility.persistence.server.Helper.ifTrueYesElseNo(value);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Class<?> klass;
		private Collection<?> objects;
		private Collection<String> fieldsNames;
	}
	
	/**/
	
	static TransientFieldsProcessor getInstance() {
		return Helper.getInstance(TransientFieldsProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();		
}