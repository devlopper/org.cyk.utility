package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.helper.StringHelper.CaseType;

import lombok.Getter;

public class SelectItemHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static SelectItemHelper INSTANCE;
	
	public static SelectItemHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new SelectItemHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <T> List<T> get(Collection<T> instances,Boolean nullable){
		return new Builder.Many.Adapter.Default<T>().setNullable(nullable).setInstances(instances).execute();
	}
	
	public <T> List<T> get(Class<T> aClass,Boolean nullable){
		return get(InstanceHelper.getInstance().get(aClass),nullable);
	}
	
	public static interface Builder {
		
		public static interface One<SELECTITEM> extends org.cyk.utility.common.Builder.NullableInput<SELECTITEM>  {
			
			Boolean getIsNull();
			One<SELECTITEM> setIsNull(Boolean isNull);
			
			One<SELECTITEM> setFieldValue(SELECTITEM item,String fieldName,Object value);
			String getFieldValue(Object instance,String fieldName);
			
			String getNullValue(String fieldName);
			
			One<SELECTITEM> setInstance(Object instance);
			
			String LABEL_NULL="selectitem.label.null";
			String DESCRIPTION_NULL="selectitem.description.null";
			
			@Getter
			public static class Adapter<SELECTITEM> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<SELECTITEM> implements One<SELECTITEM>,Serializable {
				private static final long serialVersionUID = 1L;

				protected Boolean isNull;
				
				public Adapter(Class<SELECTITEM> outputClass) {
					super(outputClass);
				}
				
				@Override
				public One<SELECTITEM> setFieldValue(SELECTITEM item, String fieldName, Object value) {
					return null;
				}
				
				@Override
				public String getFieldValue(Object instance, String fieldName) {
					return null;
				}
				
				@Override
				public One<SELECTITEM> setIsNull(Boolean isNull) {
					return null;
				}
				
				@Override
				public String getNullValue(String fieldName) {
					return null;
				}
				
				@Override
				public One<SELECTITEM> setInstance(Object instance) {
					return null;
				}
				
				/**/
				public static class Default<SELECTITEM> extends One.Adapter<SELECTITEM> implements Serializable {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					public static Class<? extends One<?>> DEFAULT_CLASS = (Class<? extends One<?>>) ClassHelper.getInstance().getByName(One.Adapter.Default.class);
					
					public Default(Class<SELECTITEM> outputClass) {
						super(outputClass);
					}
					
					@Override
					protected SELECTITEM __execute__() {
						SELECTITEM item = ClassHelper.getInstance().instanciateOne(getOutputClass());
						if(Boolean.TRUE.equals(getIsNull())){
							setFieldValue(item, FIELD_NAME_VALUE, null);
							setFieldValue(item, FIELD_NAME_LABEL, getNullValue(FIELD_NAME_LABEL));
							setFieldValue(item, FIELD_NAME_DESCRIPTION, getNullValue(FIELD_NAME_DESCRIPTION));
						}else{
							Object instance = getProperty(PROPERTY_NAME_INSTANCE);
							setFieldValue(item, FIELD_NAME_VALUE, instance);
							setFieldValue(item, FIELD_NAME_LABEL, getFieldValue(instance,FIELD_NAME_LABEL));
							setFieldValue(item, FIELD_NAME_DESCRIPTION, getFieldValue(instance,FIELD_NAME_DESCRIPTION));	
						}
						
						return item;
					}
					
					@Override
					public One<SELECTITEM> setInstance(Object instance) {
						setProperty(PROPERTY_NAME_INSTANCE, instance);
						return this;
					}
					
					@Override
					public One<SELECTITEM> setFieldValue(SELECTITEM item, String fieldName, Object value) {
						return this;
					}
					
					@Override
					public String getFieldValue(Object instance, String fieldName) {
						if(FIELD_NAME_LABEL.equals(fieldName))
							return InstanceHelper.getInstance().getLabel(instance);
						if(FIELD_NAME_DESCRIPTION.equals(fieldName))
							return InstanceHelper.getInstance().getDescription(instance);
						return super.getFieldValue(instance, fieldName);
					}
					
					@Override
					public One<SELECTITEM> setIsNull(Boolean isNull) {
						this.isNull = isNull;
						return this;
					}
					
					@Override
					public String getNullValue(String fieldName) {
						if(FIELD_NAME_LABEL.equals(fieldName))
							return StringHelper.getInstance().get(LABEL_NULL,CaseType.NONE,new Object[]{});
						if(FIELD_NAME_DESCRIPTION.equals(fieldName))
							return StringHelper.getInstance().get(DESCRIPTION_NULL,CaseType.NONE,new Object[]{});
						return null;
					}
					
				}
			}
			
			/**/
			
			String FIELD_NAME_VALUE = "VALUE";
			String FIELD_NAME_LABEL = "LABEL";
			String FIELD_NAME_DESCRIPTION = "DESCRIPTION";
			
			public static interface Null extends One<Object> {
				
			}
		}
		
		public static interface Many<SELECTITEM> extends org.cyk.utility.common.Builder.NullableInput<List<SELECTITEM>>  {
			
			Many<SELECTITEM> setOneBuilder(One<SELECTITEM> builder);
			One<SELECTITEM> getOneBuilder();
			
			Many<SELECTITEM> setNullable(Boolean nullable);
			Boolean getNullable();
			
			Many<SELECTITEM> setInstances(Collection<?> instances);
			
			@Getter
			public static class Adapter<SELECTITEM> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<List<SELECTITEM>> implements Many<SELECTITEM>,Serializable {
				private static final long serialVersionUID = 1L;

				protected One<SELECTITEM> oneBuilder;
				protected Boolean nullable;
				
				@SuppressWarnings("unchecked")
				public Adapter() {
					super((Class<List<SELECTITEM>>) ClassHelper.getInstance().getByName(ArrayList.class));
				}
				
				@Override
				public Many<SELECTITEM> setNullable(Boolean nullable) {
					return null;
				}
				
				@Override
				public Many<SELECTITEM> setOneBuilder(One<SELECTITEM> builder) {
					return null;
				}
				
				@Override
				public Many<SELECTITEM> setInstances(Collection<?> instances) {
					return null;
				}
				
				/**/
				public static class Default<SELECTITEM> extends Many.Adapter<SELECTITEM> implements Serializable {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					@Override
					protected List<SELECTITEM> __execute__() {
						Collection<Object> instances = (Collection<Object>) getProperty(PROPERTY_NAME_INSTANCES);
						List<SELECTITEM> items = new ArrayList<>();
						One<SELECTITEM> oneBuilder = getOneBuilder();
						if(oneBuilder==null)
							oneBuilder = (One<SELECTITEM>) ClassHelper.getInstance().instanciateOne(One.Adapter.Default.DEFAULT_CLASS);
						if(Boolean.TRUE.equals(getNullable())){
							SELECTITEM item = oneBuilder.setIsNull(Boolean.TRUE).execute();
							items.add(item);
						}
						for(Object instance : instances){
							SELECTITEM item = oneBuilder.setIsNull(Boolean.FALSE).setProperty(PROPERTY_NAME_INSTANCE, instance).execute();
							items.add(item);
						}
						return items;
					}
					
					@Override
					public Many<SELECTITEM> setNullable(Boolean nullable) {
						this.nullable = nullable;
						return this;
					}
					
					@Override
					public Many<SELECTITEM> setOneBuilder(One<SELECTITEM> builder) {
						this.oneBuilder = builder;
						return this;
					}
					
					@Override
					public Many<SELECTITEM> setInstances(Collection<?> instances) {
						setProperty(PROPERTY_NAME_INSTANCES, instances);
						return this;
					}
					
				}
			}
			
			public static interface Null extends Many<Object> {
				
			}
			
			/**/
			
			String FIELD_NAME_VALUE = "VALUE";
			String FIELD_NAME_LABEL = "LABEL";
			String FIELD_NAME_DESCRIPTION = "DESCRIPTION";
		}
	}
	
}
