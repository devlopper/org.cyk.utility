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
import org.cyk.utility.persistence.query.Filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface TransientFieldsProcessor {

	void process(Arguments arguments);
	void process(Collection<?> objects,Filter filter,Collection<String> fieldsNames);
	void process(Collection<?> objects,Filter filter,String...fieldsNames);
	
	public static abstract class AbstractImpl extends AbstractObject implements TransientFieldsProcessor,Serializable {
		
		@Override
		public void process(Arguments arguments) {
			if(arguments == null || CollectionHelper.isEmpty(arguments.fieldsNames))
				return;
			try {
				__process__(arguments.klass, arguments.objects,arguments.filter,arguments.fieldsNames);
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
			}
		}
		
		protected void __process__(Class<?> klass,Collection<?> objects,Filter filter,Collection<String> fieldsNames) {
			for(String fieldName : fieldsNames) {
				if(StringHelper.isBlank(fieldName))
					continue;
				Boolean processed = __process__(klass,objects,filter,fieldName);
				if(!Boolean.TRUE.equals(processed))
					logFieldNameHasNotBeenSet(klass, fieldName);
			}
		}
		
		protected Boolean __process__(Class<?> klass,Collection<?> objects,Filter filter,String fieldName) {
			return Boolean.TRUE;
		}
		
		protected void logFieldNameHasNotBeenSet(Class<?> klass,String fieldName) {
			LogHelper.logWarning(String.format("Transient field name <<%s.%s>> has not been processed", klass.getName(),fieldName), getClass());
		}
		
		@Override
		public void process(Collection<?> objects,Filter filter,Collection<String> fieldsNames) {
			if(CollectionHelper.isEmpty(objects) || CollectionHelper.isEmpty(fieldsNames))
				return;
			process(new Arguments().setKlass(objects.iterator().next().getClass()).setObjects(objects).setFilter(filter).setFieldsNames(fieldsNames));
		}
		
		@Override
		public void process(Collection<?> objects,Filter filter,String... fieldsNames) {
			if(ArrayHelper.isEmpty(fieldsNames))
				return;
			process(objects,filter,CollectionHelper.listOf(fieldsNames));
		}
		
		protected String ifTrueYesElseNo(Class<?> klass,String fieldName,Boolean value) {
			return org.cyk.utility.persistence.server.Helper.ifTrueYesElseNo(value);
		}
		
		protected String ifTrueYesElseNo(Boolean value) {
			return org.cyk.utility.persistence.server.Helper.ifTrueYesElseNo(value);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Class<?> klass;
		private Collection<?> objects;
		private Collection<String> fieldsNames;
		private Filter filter;
	}
	
	/**/
	
	static TransientFieldsProcessor getInstance() {
		return Helper.getInstance(TransientFieldsProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();		
}