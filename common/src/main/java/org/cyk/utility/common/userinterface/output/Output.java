package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.input.Input;

public class Output extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	/**/
	
	@Override
	public Output setLabelFromIdentifier(String identifier){
		return (Output) super.setLabelFromIdentifier(identifier);
	}
	
	@Override
	public Output __setLabelFromField__(){
		return (Output) super.__setLabelFromField__();
	}
	
	@Override
	public Output __setFieldFromName__(String name){
		super.__setFieldFromName__(name);
		return this;
	}
	
	@Override
	public Output setField(Field field){
		this.field = field;
		if(this.field == null){
			
		}else{
			read();
		}
		return this;
	}
	
	@Override
	public Output __setField__(Object object,String fieldName){
		super.__setField__(object, fieldName);
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

	public static Output get(FormDetail detail,Object object,java.lang.reflect.Field field,FieldHelper.Constraints constraints){
		return getListener().get(detail,object, field,constraints);
	}
	
	public static Output get(FormDetail detail,Object object,java.lang.reflect.Field field){
		return get(detail, object, field, null);
	}
	
	public static java.util.List<Output> get(FormDetail detail,Object object){
		return getListener().get(detail,object);
	}
	
	public static Boolean isOutputable(Class<?> aClass,String fieldName){
		return getListener().isOutputable(aClass, fieldName);
	}
	
	public static Listener getListener(){
		return ClassHelper.getInstance().instanciateOne(Listener.class);
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<String> getFieldNames(FormDetail form,Object object);
		Collection<String> getExcludedFieldNames(FormDetail form,Object object);
		Boolean isOutputable(Class<?> aClass,String fieldName);
		Boolean isInputable(FormDetail form,Object object,java.lang.reflect.Field field);
		java.util.Collection<Field> getFields(FormDetail form,Object object);
		void sortFields(FormDetail form,Object object,java.util.List<Field> fields);
		
		Class<? extends Output> getClass(FormDetail form,Object object,java.lang.reflect.Field field);
		
		Output get(FormDetail form,Object object,java.lang.reflect.Field field,FieldHelper.Constraints constraints);
		Output get(FormDetail form,Object object,java.lang.reflect.Field field);
		void listenGet(Output output);
		java.util.List<Output> get(FormDetail form,Object object);
		
		void read(Output output);
		
		Object getReadableValue(Object object,Field field,FieldHelper.Constraints constraints);
		Object getReadableValue(Object object,String fieldName,FieldHelper.Constraints constraints);
		Object getReadableValue(Output output);
		
		
		public static class Adapter extends AbstractBean implements Listener {
			private static final long serialVersionUID = 1L;

			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Boolean isOutputable(Class<?> aClass, String fieldName) {
					return Input.isinputable(aClass, fieldName);
				}
				
				@Override
				public Boolean isInputable(FormDetail form,Object object, Field field) {
					return field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)!=null 
							|| CollectionHelper.getInstance().contains(getFieldNames(form, object), field.getName());
				}
				
				@Override
				public Collection<Field> getFields(final FormDetail detail,final Object object) {
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
				public void sortFields(FormDetail detail, Object object, List<Field> fields) {
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
				public Class<? extends Output> getClass(FormDetail form,Object object, Field field) {
					Class<? extends Output> aClass = null;
					if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)==null){
						if(field.getType().equals(FileHelper.getListener().getModelClass()))
							aClass = OutputFile.class;
						else
							aClass = OutputText.class;
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
				public Output get(FormDetail detail,Object object, Field field, FieldHelper.Constraints constraints) {
					Output output = null;
					Class<? extends Output> aClass = getClass(detail,object, field);
					if(aClass!=null){
						output = ClassHelper.getInstance().instanciateOne(aClass);
						output.getPropertiesMap().setConstraints(constraints);
					}
					if(output!=null){
						output.setObject(object).setField(field);
						if(output instanceof OutputText){
							
						}
						listenGet(output);
					}
					return output;
				}
				
				@Override
				public Output get(FormDetail detail,Object object, Field field) {
					return get(detail, object, field, null);
				}
			
				@Override
				public java.util.List<Output> get(FormDetail form,Object object) {
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
					if(value!=null){
						if(ClassHelper.getInstance().isIdentified(value.getClass())){
							output.getPropertiesMap().setLinked(Boolean.TRUE);
							OutputLink link = new OutputLink();
							link.getPropertiesMap().setValue(UniformResourceLocatorHelper.getInstance().stringify(Constant.Action.READ, value));
							//if(output instanceof OutputText)
							//	link.getPropertiesMap().setOutputTextComponent(output);
							output.getPropertiesMap().setOutputLinkComponent(link);
						}
						if(ClassHelper.getInstance().isString(value.getClass()))
							if(ContentType.HTML.equals(Component.RENDER_AS_CONTENT_TYPE)){
								if(output instanceof OutputText){
									if(output.getPropertiesMap().getTitle() == null)
										output.getPropertiesMap().setTitle(value);
								}
								if(!StringHelper.getInstance().isContainMarkupLanguageTag((String)value))
									value = StringHelper.getInstance().getHtml((String) value);
							}
					}
					output.getPropertiesMap().setValue(value);
				}
				
				@Override
				public Object getReadableValue(Object object, String fieldName,FieldHelper.Constraints constraints) {
					Object value =  FieldHelper.getInstance().read(object, fieldName);
					if(value!=null){
						FileHelper.Listener listener = FileHelper.getListener();
						if(value.getClass().equals( listener.getModelClass() ))
							value = getReadableValueFile(value,listener.getName(value),listener.getExtension(value),listener.getMime(value),listener.getBytes(value));
						else if(ClassHelper.getInstance().isDate(value.getClass()))
							value = new TimeHelper.Stringifier.Date.Adapter.Default((Date) value).setProperty(TimeHelper.Stringifier.PROPERTY_NAME_TIME_PART
									, constraints == null || constraints.getDatePart() == null ? Constant.Date.Part.DATE_ONLY : constraints.getDatePart()).execute();
						else if(ClassHelper.getInstance().isBoolean(value.getClass()))
							value = StringHelper.getInstance().getResponse((Boolean) value);
						else if(ClassHelper.getInstance().isString(value.getClass())){
							//value = StringHelper.getInstance().getHtml((String) value);
						}
					}
					
					return value;
				}
				
				@Override
				public Object getReadableValue(Object object, Field field,FieldHelper.Constraints constraints) {
					return getReadableValue(object, field.getName(),constraints);
				}
				
				@Override
				public Object getReadableValue(Output output) {
					return getReadableValue(output.getObject(), output.getField(),(FieldHelper.Constraints)output.getPropertiesMap().getConstraints());
				}
					
				protected Object getReadableValueFile(Object value,String name,String extension,String mime,byte[] bytes){
					return value;
				}
			}
			
			@Override
			public Boolean isOutputable(Class<?> aClass, String fieldName) {
				return null;
			}
			
			@Override
			public void listenGet(Output output) {}
			
			@Override
			public Collection<String> getFieldNames(FormDetail form,Object object) {
				return null;
			}
			
			@Override
			public Collection<String> getExcludedFieldNames(FormDetail form, Object object) {
				return null;
			}
			
			@Override
			public Boolean isInputable(FormDetail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Collection<Field> getFields(FormDetail form,Object object) {
				return null;
			}
			
			@Override
			public Class<? extends Output> getClass(FormDetail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Output get(FormDetail form, Object object, Field field, FieldHelper.Constraints constraints) {
				return null;
			}
			
			@Override
			public Output get(FormDetail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public java.util.List<Output> get(FormDetail form,Object object) {
				return null;
			}
			
			@Override
			public void sortFields(FormDetail form, Object object, List<Field> fields) {}
			
			@Override
			public void read(Output output) {}
			
			@Override
			public Object getReadableValue(Output output) {
				return null;
			}
			
			@Override
			public Object getReadableValue(Object object, Field field,FieldHelper.Constraints constraints) {
				return null;
			}
			
			@Override
			public Object getReadableValue(Object object, String fieldName,FieldHelper.Constraints constraints) {
				return null;
			}
			
		}
		
	}
}
