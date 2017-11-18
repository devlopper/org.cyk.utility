package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputBooleanButton;
import org.cyk.utility.common.userinterface.input.InputBooleanCheckBox;
import org.cyk.utility.common.userinterface.input.InputCalendar;
import org.cyk.utility.common.userinterface.input.InputEditor;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.InputPassword;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyAutoComplete;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyButton;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyCheck;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyCombo;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyPickList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneAutoComplete;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneButton;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCascadeList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCombo;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneRadio;
import org.cyk.utility.common.userinterface.input.number.InputNumber;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Control extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	
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
				
				@SuppressWarnings("unchecked")
				@Override
				public Class<? extends Control> getClass(Form.Detail form,Object object, Field field) {
					Class<? extends Control> aClass = null;
					if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)==null){
						if(String.class.equals(field.getType())){
							aClass = InputText.class;
						}else if(Date.class.equals(field.getType())){
							aClass = InputCalendar.class;
						}
					}else{
						if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null)
							aClass = InputText.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputPassword.class)!=null)
							aClass = InputPassword.class;
						
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputTextarea.class)!=null)
							aClass = InputTextarea.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputEditor.class)!=null)
							aClass = InputEditor.class;
						
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton.class)!=null)
							aClass = InputBooleanButton.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck.class)!=null)
							aClass = InputBooleanCheckBox.class;
						
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputNumber.class)!=null){
							String name = InputNumber.class.getName()+ClassHelper.getInstance().getWrapper(field.getType()).getSimpleName();
							aClass = (Class<? extends Control>) ClassHelper.getInstance().getByName(name);
						}
						
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputCalendar.class)!=null)
							aClass = InputCalendar.class;
						//one choice
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCombo.class)!=null)
							aClass = InputChoiceOneCombo.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneList.class)!=null)
							aClass = InputChoiceOneList.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCascadeList.class)!=null)
							aClass = InputChoiceOneCascadeList.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneButton.class)!=null)
							aClass = InputChoiceOneButton.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete.class)!=null)
							aClass = InputChoiceOneAutoComplete.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneRadio.class)!=null)
							aClass = InputChoiceOneRadio.class;
						//many choices
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyAutoComplete.class)!=null)
							aClass = InputChoiceManyAutoComplete.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyButton.class)!=null)
							aClass = InputChoiceManyButton.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheck.class)!=null)
							aClass = InputChoiceManyCheck.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyList.class)!=null)
							aClass = InputChoiceManyList.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCombo.class)!=null)
							aClass = InputChoiceManyCombo.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyPickList.class)!=null)
							aClass = InputChoiceManyPickList.class; //InstanceHelper.getInstance().getIfNotNullElseDefault(InputChoiceManyPickList.DEFAULT_CLASS,InputChoiceManyPickList.class);
						
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null)
							aClass = InputFile.class;
					}
					
					if(aClass!=null){
						aClass = (Class<? extends Control>) ClassHelper.getInstance().getMapping(aClass, Boolean.TRUE);
						//Field staticField = FieldHelper.getInstance().get(aClass, "DEFAULT_CLASS");
						//aClass = staticField == null ? aClass : (Class<? extends Control>)FieldHelper.getInstance().readStatic(staticField);
					}
					
					if(aClass==null){
						logWarning("No control class has been found for field $", field);
						aClass = (Class<? extends Control>) ClassHelper.getInstance().getByName(Input.class);
					}
					return aClass;
				}
				
				@Override
				public Control get(Form.Detail detail,Object object, Field field) {
					Control control = null;
					Class<? extends Control> aClass = getClass(detail,object, field);
					if(aClass!=null){
						control = ClassHelper.getInstance().instanciateOne(aClass);
					}
					if(control!=null){
						//control.setFormDetail(detail);
						//control.setObject(object).setField(field);
						//control.getPropertiesMap().setLabel(control.getLabel().getPropertiesMap().getValue());
						/*control.getPropertiesMap().setRequired(field.getAnnotation(NotNull.class)!=null);
						control.getPropertiesMap().setRequiredMessage(StringHelper.getInstance().get("validation.userinterface.control.value.required"
								, new Object[]{control.getLabel().getPropertiesMap().getValue()}));
						*/
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
				
				protected void setFileFieldValue(Object object,String name,Object value){
					Field field = FieldHelper.getInstance().get(object.getClass(), name);
					if(field!=null)
						FieldHelper.getInstance().set(object,value, name);
				}
				
				protected void setFile(Object file,Object value){
					for(String fieldName : getFileFieldNames()){
						setFileFieldValue(file, fieldName, getFileFieldValue(value, fieldName));
					}
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