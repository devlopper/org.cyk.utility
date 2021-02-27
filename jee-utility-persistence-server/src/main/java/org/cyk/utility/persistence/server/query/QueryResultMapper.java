package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Tuple;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryResultMapper {

	<T> Collection<T> map(Class<T> resultClass,Arguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryResultMapper,Serializable {
		
		@Override
		public <T> Collection<T> map(Class<T> resultClass,Arguments arguments) {
			if(resultClass == null)
				throw new RuntimeException("Result class is required");
			if(arguments == null)
				throw new RuntimeException("Arguments are required");
			if(CollectionHelper.isNotEmpty(arguments.objects) && CollectionHelper.isNotEmpty(arguments.tuples))
				throw new RuntimeException("Collection element must be either object[] or tuple but not both");
			if(CollectionHelper.isEmpty(arguments.objects) && CollectionHelper.isEmpty(arguments.tuples))
				return null;
			arguments.prepare();
			Collection<T> collection = new ArrayList<>();
			if(CollectionHelper.isNotEmpty(arguments.objects)) {
				if(ClassHelper.isBelongsToJavaPackages(resultClass)) {
					for(Object result : arguments.objects)
						collection.add((T) result);
				}else {
					if(Boolean.TRUE.equals(QueryExecutor.AbstractImpl.LOGGABLE))
						LogHelper.log(String.format("Instantiating %s by setting fields names %s",resultClass,arguments.query.getTupleFieldsNamesIndexes()), QueryExecutor.AbstractImpl.LOG_LEVEL, getClass());
					arguments.objects.forEach(array -> {
						collection.add(instantiate(resultClass, arguments.query.getTupleFieldsNamesIndexes(), array));
					});
				}
			}else if(CollectionHelper.isNotEmpty(arguments.tuples)){
				arguments.tuples.forEach(tuple -> {
					
				});
			}
			arguments.finalise();
			return collection;
		}
		
		protected <T> T instantiate(Class<T> klass,Map<String,Integer> fieldsNamesIndexes,Object[] array) {
			if(Boolean.TRUE.equals(QueryExecutor.AbstractImpl.LOGGABLE))
				LogHelper.log(String.format("\tvalues %s",Arrays.toString(array)), QueryExecutor.AbstractImpl.LOG_LEVEL, getClass());
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("fieldsNamesIndexes", fieldsNamesIndexes);
			T instance = ClassHelper.instanciate(klass);
			fieldsNamesIndexes.forEach( (fieldName,index) -> {
				try {
					write(instance, fieldName, array[index]);
				} catch (Exception exception) {
					LogHelper.log(new RuntimeException(String.format("cannot write field %s.%s at index %s with value %s taken from values %s", instance.getClass().getName()
							,fieldName,index,index < ArrayHelper.getSize(array) ? ArrayHelper.getElementAt(array, index) : "Bad index "+index
									,Arrays.toString(array)), exception), getClass());
				}
			});
			return instance;
		}
		
		protected <T> void write(T instance,String fieldName,Object value) {
			FieldHelper.write(instance, fieldName, value);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain = true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Query query;
		private Collection<Object[]> objects;
		private Collection<Tuple> tuples;
		
		public Arguments prepare() {
			if(query == null)
				throw new RuntimeException("Query is required");
			if(StringHelper.isEmpty(query.getValue()))
				throw new RuntimeException("Query value is required");
			
			return this;
		}
		
		public Arguments finalise() {
			
			return this;
		}
	}
	
	/**/
	
	static QueryResultMapper getInstance() {
		return Helper.getInstance(QueryResultMapper.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}