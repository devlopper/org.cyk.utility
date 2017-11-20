package org.cyk.utility.common.userinterface;

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
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Control extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Object object;
	protected Field field;
	
	/**/
	
	public Control __setFieldFromName__(String name){
		setField(FieldHelper.getInstance().get(object.getClass(), name));
		return this;
	}
	
	public Control __setField__(Object object,String fieldName){
		setObject(object);
		__setFieldFromName__(fieldName);
		return this;
	}
	
	public Control __setLabelFromField__(Field field){
		OutputText outputText = new OutputText();
		outputText.getPropertiesMap().setValue(StringHelper.getInstance().getField(field));
		setLabel(outputText);
		return this;
	}
	
	public Control __setLabelFromField__(){
		__setLabelFromField__(field);
		return this;
	}
	
	@Override
	public Control setWidth(Number width) {
		return (Control) super.setWidth(width);
	}
	
	@Override
	public Control setLength(Number length) {
		return (Control) super.setLength(length);
	}
	
	public Control read(){
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Control> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {

		public static class Adapter<OUTPUT extends Control> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Control> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Control> {

		public static class Adapter extends BuilderBase.Adapter.Default<Control> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Control.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<String> getFieldNames(Form.Detail form,Object object);
		Collection<String> getExcludedFieldNames(Form.Detail form,Object object);
		Boolean isControlable(Form.Detail form,Object object,java.lang.reflect.Field field);
		java.util.Collection<Field> getFields(Form.Detail form,Object object);
		void sortFields(Form.Detail form,Object object,java.util.List<Field> fields);
		
		Class<? extends Control> getClass(Form.Detail form,Object object,java.lang.reflect.Field field);
		
		Control get(Form.Detail form,Object object,java.lang.reflect.Field field);
		void listenGet(Control control);
		java.util.List<Control> get(Form.Detail form,Object object);
		
		public static class Adapter extends AbstractBean implements Listener {
			private static final long serialVersionUID = 1L;

			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Boolean isControlable(Form.Detail form,Object object, Field field) {
					return CollectionHelper.getInstance().contains(getFieldNames(form, object), field.getName());
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
									if(isControlable(detail, object, field))
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
				
				@Override
				public Control get(Form.Detail detail,Object object, Field field) {
					Control control = null;
					Class<? extends Control> aClass = getClass(detail,object, field);
					if(aClass!=null){
						control = ClassHelper.getInstance().instanciateOne(aClass);
					}
					if(control!=null){
						listenGet(control);
					}
					return control;
				}
			
				@Override
				public java.util.List<Control> get(Form.Detail form,Object object) {
					java.util.List<Control> list = new ArrayList<Control>();
					for(Field field : getFields(form,object))
						list.add(get(form,object, field));
					return list;
				}
			
				protected Boolean isField(Class<?> aClass,Object object1,Object object2,Field field,String fieldName){
					return ClassHelper.getInstance().isEqual(aClass,object1.getClass()) && object1 == object2 && field.getName().equals(fieldName);
				}
				
				public Class<?> getFileClass(){
					return FileHelper.File.class;
				}
				
				protected String[] getFileFieldNames(){
					return new String[]{"name","extension","bytes","mime"};
				}
				
				protected Object getFileFieldValue(Object file,String fieldName){
					return FieldHelper.getInstance().read(file, fieldName);
				}
				
			}
			
			@Override
			public void listenGet(Control control) {}
			
			@Override
			public Collection<String> getFieldNames(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public Collection<String> getExcludedFieldNames(Detail form, Object object) {
				return null;
			}
			
			@Override
			public Boolean isControlable(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Collection<Field> getFields(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public Class<? extends Control> getClass(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Control get(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public java.util.List<Control> get(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public void sortFields(Detail form, Object object, List<Field> fields) {}
			
		}
		
	}
}