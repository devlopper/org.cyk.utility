package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.CardinalPoint;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FieldHelper.Constraints;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.LocaleHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
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
import org.cyk.utility.common.userinterface.output.Output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Input<T> extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	protected Object valueObject;
	protected T value,initialValue;
	protected Form.Detail formDetail;

	protected CardinalPoint labelCardinalPoint;
	
	protected CardinalPoint messageCardinalPoint;
		
	public Input<T> __setFieldFromName__(String name){
		super.__setFieldFromName__(name);
		return this;
	}
	
	public Input<T> setField(Field field){
		this.field = field;
		if(this.field == null){
			value = initialValue = null;
		}else{
			__setLabelFromField__(field);
			read();
		}
		return this;
	}
	
	public Input<T> __setField__(Object object,String fieldName){
		super.__setField__(object, fieldName);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Input<T> setLabelFromIdentifier(String identifier){
		return (Input<T>) super.setLabelFromIdentifier(identifier);
	}
	
	@Override
	public Input<T> setWidth(Number width) {
		super.setWidth(width);
		return this;
	}
	
	@Override
	public Input<T> setLength(Number length) {
		super.setLength(length);
		return this;
	}
	
	public Input<T> read(){
		getListener().read(this);
		return this;
	}
	
	public Input<T> write(){
		getListener().write(this);
		return this;
	}
	
	//Because some library scanning does not recognize chaining 
	public void setValue(T value){
		this.value = value;
	}
	
	/**/

	public static interface BuilderBase<OUTPUT extends Input<?>> extends Control.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Input<?>> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Input<?>> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Input<?>> {

		public static class Adapter extends BuilderBase.Adapter.Default<Input<?>> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<Input<?>>) ClassHelper.getInstance().getByName(Input.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

	/**/
	
	//private static final Listener LISTENER = ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class);
	public static Listener getListener(){
		//return LISTENER;
		return ClassHelper.getInstance().instanciateOne(Listener.class);
	}
	
	public static Input<?> get(Form.Detail detail,Object object,java.lang.reflect.Field field,FieldHelper.Constraints constraints,Control.Listener.Get getListener){
		return getListener().get(detail,object, field,constraints,getListener);
	}
	
	public static Input<?> get(Form.Detail detail,Object object,java.lang.reflect.Field field,FieldHelper.Constraints constraints){
		return getListener().get(detail,object, field,constraints);
	}
	
	public static Input<?> get(Form.Detail detail,Object object,java.lang.reflect.Field field){
		return get(detail, object, field, null);
	}
	
	public static Input<?> get(Form.Detail detail,Object object,String fieldName,FieldHelper.Constraints constraints){
		return get(detail,object, FieldHelper.getInstance().get(object.getClass(), fieldName),constraints);
	}
	
	public static Input<?> get(Form.Detail detail,Object object,String fieldName){
		return get(detail, object, fieldName, null);
	}
	
	/*public static Input<?> get(Form.Detail detail,Object object,String fieldName,Listener.Get getListener){
		String constraintsFieldName = StringHelper.getInstance().isBlank(fieldsObjectFieldName) ? fieldName : FieldHelper.getInstance().buildPath(fieldsObjectFieldName,fieldName);
		FieldHelper.Constraints constraints = FieldHelper.Field.get(getMaster().getObject().getClass(), constraintsFieldName).getConstraints();
		Field field = FieldHelper.getInstance().get(object.getClass(), fieldName);
		return get(detail,object, field,constraints,getInputListener);
	}*/
	
	public static java.util.List<Input<?>> get(Form.Detail detail,Object object){
		return getListener().get(detail,object);
	}
	
	public static void read(Collection<Input<?>> inputs){
		new CollectionHelper.Iterator.Adapter.Default<Input<?>>(inputs){
			private static final long serialVersionUID = 1L;
			@Override
			protected void __executeForEach__(Input<?> input) {
				input.read();
			}
		}.execute();
	}
	
	public static void write(Collection<Input<?>> inputs){
		new CollectionHelper.Iterator.Adapter.Default<Input<?>>(inputs){
			private static final long serialVersionUID = 1L;
			@Override
			protected void __executeForEach__(Input<?> input) {
				input.write();
				//System.out.println("Input.write(...).new Default() {...}.__executeForEach__() : "+input.getValue()+" : "+input.getObject());
			}
		}.execute();
	}

	public static Boolean isinputable(Class<?> aClass,String fieldName){
		return getListener().isInputable(aClass, fieldName);
	}
	
	/**/
	
	public static final java.util.List<Class<? extends Annotation>> ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
	static {
		ANNOTATIONS.add(org.cyk.utility.common.annotation.user.interfaces.Input.class);
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<String> getFieldNames(Form.Detail form,Object object);
		Collection<String> getExcludedFieldNames(Form.Detail form,Object object);
		Boolean isInputable(Class<?> aClass,String fieldName);
		Boolean isInputable(Form.Detail form,Object object,java.lang.reflect.Field field);
		java.util.Collection<Field> getFields(Form.Detail form,Object object);
		void sortFields(Form.Detail form,Object object,java.util.List<Field> fields);
		
		Class<? extends Input<?>> getClass(Form.Detail form,Object object,java.lang.reflect.Field field);
		
		Input<?> instanciate(Class<? extends Input<?>> aClass,Form.Detail form,Object object,java.lang.reflect.Field field, FieldHelper.Constraints constraints);
		void initialse(Input<?> input,Form.Detail form,Object object,java.lang.reflect.Field field, FieldHelper.Constraints constraints,Control.Listener.Get listener);
		
		Input<?> get(Form.Detail form,Object object,java.lang.reflect.Field field, FieldHelper.Constraints constraints,Control.Listener.Get listener);
		Input<?> get(Form.Detail form,Object object,java.lang.reflect.Field field, FieldHelper.Constraints constraints);
		Input<?> get(Form.Detail form,Object object,java.lang.reflect.Field field);
		void listenGet(Input<?> input);
		java.util.List<Input<?>> get(Form.Detail form,Object object);
		
		void read(Input<?> input);
		void write(Input<?> input);
		
		Object getReadableValue(Input<?> input);
		Object getWritableValue(Input<?> input);
		
		Class<?> computeChoiceInstanceClass(InputChoice<?> inputChoice);
		
		public static class Adapter extends AbstractBean implements Listener {
			private static final long serialVersionUID = 1L;

			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Boolean isInputable(Class<?> aClass, String fieldName) {
					return Boolean.TRUE;
				}
				
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
				public Class<? extends Input<?>> getClass(Form.Detail form,Object object, Field field) {
					Class<? extends Input<?>> aClass = null;
					Class<?> fieldType = FieldHelper.getInstance().getType(object.getClass(), field);
					if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)==null){
						if(String.class.equals(fieldType)){
							aClass = InputText.class;
						}else if(Date.class.equals(fieldType)){
							aClass = InputCalendar.class;
						}else if(ClassHelper.getInstance().isBoolean(fieldType)){
							aClass = InputBooleanButton.class;
						}else if(ClassHelper.getInstance().isNumber(fieldType)){
							String name = InputNumber.class.getName()+ClassHelper.getInstance().getWrapper(fieldType).getSimpleName();
							aClass = (Class<? extends Input<?>>) ClassHelper.getInstance().getByName(name);
						}else if(ClassHelper.getInstance().isIdentified(fieldType) || fieldType.isEnum()){
							aClass = InputChoiceOneCombo.class;
						}else if(ClassHelper.getInstance().isInstanceOf(Collection.class, fieldType)){
							aClass = InputChoiceManyPickList.class;
						}
						if(aClass == null)
							logWarning("No input class found for field named <<{}>> of type <<{}>>", field,fieldType);
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
							String name = InputNumber.class.getName()+ClassHelper.getInstance().getWrapper(fieldType).getSimpleName();
							aClass = (Class<? extends Input<?>>) ClassHelper.getInstance().getByName(name);
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
						aClass = (Class<? extends Input<?>>) ClassHelper.getInstance().getMapping(aClass, Boolean.TRUE);
						//Field staticField = FieldHelper.getInstance().get(aClass, "DEFAULT_CLASS");
						//aClass = staticField == null ? aClass : (Class<? extends Input<?>>)FieldHelper.getInstance().readStatic(staticField);
					}
					
					if(aClass==null){
						logWarning("No input class has been found for field {}", field);
						aClass = (Class<? extends Input<?>>) ClassHelper.getInstance().getByName(Input.class);
					}
					return aClass;
				}
				
				@Override
				public Input<?> instanciate(Class<? extends Input<?>> aClass, Detail form, Object object, Field field,Constraints constraints) {
					return ClassHelper.getInstance().instanciateOne(aClass);
				}
				
				@Override
				public void initialse(Input<?> input, Detail detail, Object object, Field field,Constraints constraints,Control.Listener.Get listener) {
					Boolean isReadChoicesElementsOnSetField = null;
					if(input instanceof InputChoice){
						if(Boolean.TRUE.equals(((InputChoice<?>)input).getIsReadChoicesElementsOnSetField())){
							((InputChoice<?>)input).setIsReadChoicesElementsOnSetField(isReadChoicesElementsOnSetField = Boolean.FALSE);
						}
					}
					
					input.setFormDetail(detail);
					input.setObject(object).setField(field);
					if(listener != null && StringHelper.getInstance().isNotBlank(listener.getLabelValueIdentifier()))
							input.getLabel().getPropertiesMap().setValue(StringHelper.getInstance().get(listener.getLabelValueIdentifier(), new Object[]{}));
					input.getPropertiesMap().setLabel(input.getLabel().getPropertiesMap().getValue());
					input.getPropertiesMap().setRequired(field.getAnnotation(NotNull.class)!=null);
					input.getPropertiesMap().setRequiredMessage(StringHelper.getInstance().get("validation.userinterface.input.value.required"
							, new Object[]{input.getLabel().getPropertiesMap().getValue()}));
					
					if(input instanceof InputCalendar){
						((InputCalendar)input).getPropertiesMap().setPattern(Constant.Date.getPattern(LocaleHelper.getInstance().get()
								, constraints == null || constraints.getDatePart() == null ? Constant.Date.Part.DATE_ONLY : constraints.getDatePart()
										, Constant.Date.Length.SHORT).getValue());
					}
					if(input instanceof InputChoice){
						((InputChoice<?>)input).setNullChoicable(input.getInitialValue() == null || !Boolean.TRUE.equals(input.getPropertiesMap().getRequired()));
						if(isReadChoicesElementsOnSetField == null || Boolean.FALSE.equals(isReadChoicesElementsOnSetField)){
							((InputChoice<?>)input).readChoicesElements();
							if(Boolean.FALSE.equals(isReadChoicesElementsOnSetField))
								((InputChoice<?>)input).setIsReadChoicesElementsOnSetField(isReadChoicesElementsOnSetField = Boolean.TRUE);
						}
						
					}
					
					listenGet(input);
					
					if(Boolean.TRUE.equals(input.getPropertiesMap().getReadableOnly())){
						if(input.getPropertiesMap().getOutputComponent()==null){
							input.getPropertiesMap().setOutputComponent(Output.get(detail, object, field));
						}
					}
				}
				
				@Override
				public Input<?> get(Form.Detail detail,Object object, Field field, FieldHelper.Constraints constraints,Control.Listener.Get listener) {
					Input<?> input = null;
					Class<? extends Input<?>> aClass = getClass(detail,object, field);
					if(aClass!=null)
						input = instanciate(aClass, detail, object, field, constraints);
					if(input!=null){
						if(listener!=null)
							listener.processBeforeInitialise(input,detail, object, field, constraints);
						initialse(input, detail, object, field, constraints,listener);
						if(listener!=null)
							listener.processAfterInitialise(input,detail, object, field, constraints);
					}
					return input;
				}
				
				@Override
				public Input<?> get(Form.Detail detail,Object object, Field field, FieldHelper.Constraints constraints) {
					return get(detail,object,field,constraints,null);
				}
				
				@Override
				public Input<?> get(Form.Detail detail,Object object, Field field) {
					return get(detail, object, field, null);
				}
			
				@Override
				public java.util.List<Input<?>> get(Form.Detail form,Object object) {
					java.util.List<Input<?>> list = new ArrayList<Input<?>>();
					for(Field field : getFields(form,object))
						list.add(get(form,object, field));
					return list;
				}
			
				protected Boolean isField(Class<?> aClass,Object object1,Object object2,Field field,String fieldName){
					return ClassHelper.getInstance().isEqual(aClass,object1.getClass()) && object1 == object2 && field.getName().equals(fieldName);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public void read(@SuppressWarnings("rawtypes") Input input){
					if(input.object!=null && input.field!=null)
						input.value = input.initialValue = getReadableValue(input);
				}
				
				@Override
				public void write(Input<?> input){
					if(input.object!=null && input.field!=null){
						Object value = getWritableValue(input);
						logTrace("write. class = {} , field = {} , value = {}", input.object.getClass(),input.field,value);
						FieldHelper.getInstance().set(input.object,value, input.field);
					}
				}
				
				@Override
				public Object getReadableValue(Input<?> input) {
					return FieldHelper.getInstance().read(input.getObject(), input.getField());
				}
				
				@Override
				public Object getWritableValue(Input<?> input) {
					Object value = getPreparedValue(input);
					if(input instanceof InputFile){
						//InputFile inputFile = (InputFile) input;
						Class<?> fileClass = FileHelper.getListener().getModelClass();
						if(!FileHelper.File.class.equals(fileClass) && input.getField().getType().equals(fileClass)){
							Object file = null;
							if(value==null){
								if(input.getValue()!=null)
									value = FieldHelper.getInstance().read(input.getObject(), input.getField());
							}else{
								file = FieldHelper.getInstance().read(input.getObject(), input.getField());
								if(file==null)
									file = ClassHelper.getInstance().instanciateOne(fileClass);
								setFile(file, value);
								value = file;
							}
						}
					}
					
					return value;
				}
				
				public Object getPreparedValue(Input<?> input){
					return input.getValue();
				}
								
				protected String[] getFileFieldNames(){
					return new String[]{FileHelper.File.FIELD_NAME,FileHelper.File.FIELD_EXTENSION,FileHelper.File.FIELD_BYTES,FileHelper.File.FIELD_MIME};
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
				
				@Override
				public Class<?> computeChoiceInstanceClass(InputChoice<?> inputChoice) {
					Class<?> type = FieldHelper.getInstance().getType(inputChoice.getObject().getClass(), inputChoice.getField());
					if(ClassHelper.getInstance().isInstanceOf(Collection.class, type))
						return FieldHelper.getInstance().getParameterType(inputChoice.getField(), 0);
					if(List.class.equals(type))
				        type = (Class<?>) ((ParameterizedType) inputChoice.getField().getGenericType()).getActualTypeArguments()[0];
					return type;
				}
			}
			
			@Override
			public Input<?> instanciate(Class<? extends Input<?>> aClass, Detail form, Object object, Field field,Constraints constraints) {
				return null;
			}
			
			@Override
			public void initialse(Input<?> input, Detail form, Object object, Field field,Constraints constraints,Control.Listener.Get listener) {}
			
			@Override
			public Boolean isInputable(Class<?> aClass, String fieldName) {
				return null;
			}
			
			@Override
			public void listenGet(Input<?> input) {}
			
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
			public Class<? extends Input<?>> getClass(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Input<?> get(Form.Detail form,Object object, Field field, FieldHelper.Constraints constraints,Control.Listener.Get listener) {
				return null;
			}
			
			@Override
			public Input<?> get(Form.Detail form,Object object, Field field, FieldHelper.Constraints constraints) {
				return null;
			}
			
			@Override
			public Input<?> get(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public java.util.List<Input<?>> get(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public void sortFields(Detail form, Object object, List<Field> fields) {}
			
			@Override
			public void read(Input<?> input) {}
			
			@Override
			public void write(Input<?> input) {}
			
			@Override
			public Object getReadableValue(Input<?> input) {
				return null;
			}
			
			@Override
			public Object getWritableValue(Input<?> input) {
				return null;
			}
		
			@Override
			public Class<?> computeChoiceInstanceClass(InputChoice<?> inputChoice) {
				return null;
			}
		}
		
		/**/
		
	}

}