package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.input.Input;

public class Output extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	public static interface BuilderBase<OUTPUT extends Output> extends Control.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Output> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Output> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Output> {

		public static class Adapter extends BuilderBase.Adapter.Default<Output> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Output.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

	/**/

	public Output setFieldFromName(String name){
		super.setFieldFromName(name);
		return this;
	}
	
	public Output setField(Field field){
		this.field = field;
		if(this.field == null){
			
		}else{
			read();
		}
		return this;
	}
	
	public Output _setField(Object object,String fieldName){
		super._setField(object, fieldName);
		return this;
	}
	
	@Override
	public Output setWidth(Number width) {
		super.setWidth(width);
		return this;
	}
	
	@Override
	public Output setLength(Number length) {
		super.setLength(length);
		return this;
	}
	
	public Output read(){
		getListener().read(this);
		return this;
	}
	
	public static Output get(Form.Detail detail,Object object,java.lang.reflect.Field field){
		return getListener().get(detail,object, field);
	}
	
	public static java.util.List<Output> get(Form.Detail detail,Object object){
		return getListener().get(detail,object);
	}
	
	public static Listener getListener(){
		return ClassHelper.getInstance().instanciateOne(Listener.class);
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<String> getFieldNames(Form.Detail form,Object object);
		Collection<String> getExcludedFieldNames(Form.Detail form,Object object);
		Boolean isInputable(Form.Detail form,Object object,java.lang.reflect.Field field);
		java.util.Collection<Field> getFields(Form.Detail form,Object object);
		void sortFields(Form.Detail form,Object object,java.util.List<Field> fields);
		
		Class<? extends Output> getClass(Form.Detail form,Object object,java.lang.reflect.Field field);
		
		Output get(Form.Detail form,Object object,java.lang.reflect.Field field);
		void listenGet(Output output);
		java.util.List<Output> get(Form.Detail form,Object object);
		
		void read(Output output);
		
		Object getReadableValue(Output output);
		
		public static class Adapter extends AbstractBean implements Listener {
			private static final long serialVersionUID = 1L;

			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Boolean isInputable(Form.Detail form,Object object, Field field) {
					return field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)!=null 
							|| CollectionHelper.getInstance().contains(getFieldNames(form, object), field.getName());
				}
				
				@Override
				public Collection<Field> getFields(final Form.Detail detail,final Object object) {
					final Collection<String> fieldNames = getFieldNames(detail, object);
					final Collection<String> excludedFieldNames = getExcludedFieldNames(detail, object);
					final List<Field> fields = new ArrayList<Field>();
					new CollectionHelper.Iterator.Adapter.Default<Field>(FieldHelper.getInstance().get(object.getClass())){
						private static final long serialVersionUID = 1L;
						protected void __executeForEach__(Field field) {
							if(CollectionHelper.getInstance().isEmpty(fieldNames) || fieldNames.contains(field.getName()))
								if(CollectionHelper.getInstance().isEmpty(excludedFieldNames) || !excludedFieldNames.contains(field.getName()))
									if(isInputable(detail, object, field))
										fields.add(field);
						}
					}.execute();
					if(CollectionHelper.getInstance().isNotEmpty(fields))
						sortFields(detail, object, fields);
					return fields;
				}
				
				@Override
				public void sortFields(Detail detail, Object object, List<Field> fields) {
					final List<String> fieldNames = CollectionHelper.getInstance().createList(getFieldNames(detail, object));
					if(CollectionHelper.getInstance().isNotEmpty(fieldNames)){
						Collections.sort(fields, new Comparator<Field>() {
							@Override
							public int compare(Field field1, Field field2) {
								return new Integer(fieldNames.indexOf(field1.getName())).compareTo(fieldNames.indexOf(field2.getName()));
							}
						});
					}else
						super.sortFields(detail, object, fields);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Class<? extends Output> getClass(Form.Detail form,Object object, Field field) {
					Class<? extends Output> aClass = null;
					if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)==null){
						
					}else{
						if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null)
							aClass = OutputFile.class;
						else
							aClass = OutputText.class;
					}
					
					if(aClass!=null){
						aClass = (Class<? extends Output>) ClassHelper.getInstance().getMapping(aClass, Boolean.TRUE);
					}
					
					if(aClass==null){
						logWarning("No output class has been found for field $", field);
						aClass = (Class<? extends Output>) ClassHelper.getInstance().getByName(Output.class);
					}
					return aClass;
				}
				
				@Override
				public Output get(Form.Detail detail,Object object, Field field) {
					Output output = null;
					Class<? extends Output> aClass = getClass(detail,object, field);
					if(aClass!=null){
						output = ClassHelper.getInstance().instanciateOne(aClass);
					}
					if(output!=null){
						output.setObject(object).setField(field);
						listenGet(output);
					}
					return output;
				}
			
				@Override
				public java.util.List<Output> get(Form.Detail form,Object object) {
					java.util.List<Output> list = new ArrayList<Output>();
					for(Field field : getFields(form,object))
						list.add(get(form,object, field));
					return list;
				}
			
				protected Boolean isField(Class<?> aClass,Object object1,Object object2,Field field,String fieldName){
					return ClassHelper.getInstance().isEqual(aClass,object1.getClass()) && object1 == object2 && field.getName().equals(fieldName);
				}
				
				@Override
				public void read(Output output){
					if(output.object!=null && output.field!=null)
						setPropertyValue(output,getReadableValue(output));
				}
				
				protected void setPropertyValue(Output output,Object value){
					output.getPropertiesMap().setValue(value);
				}
				
				@Override
				public Object getReadableValue(Output output) {
					Object value =  FieldHelper.getInstance().read(output.getObject(), output.getField());
					if(value!=null){
						FileHelper.Listener listener = FileHelper.getListener();
						if(value.getClass().equals( listener.getModelClass() ))
							value = getReadableValueFile(value,listener.getName(value),listener.getExtension(value),listener.getMime(value),listener.getBytes(value));
					}
					return value;
				}
				
				protected Object getReadableValueFile(Object value,String name,String extension,String mime,byte[] bytes){
					return value;
				}
			}
			
			@Override
			public void listenGet(Output output) {}
			
			@Override
			public Collection<String> getFieldNames(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public Collection<String> getExcludedFieldNames(Detail form, Object object) {
				return null;
			}
			
			@Override
			public Boolean isInputable(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Collection<Field> getFields(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public Class<? extends Output> getClass(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Output get(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public java.util.List<Output> get(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public void sortFields(Detail form, Object object, List<Field> fields) {}
			
			@Override
			public void read(Output output) {}
			
			@Override
			public Object getReadableValue(Output output) {
				return null;
			}
			
		}
		
	}
}
