package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.userinterface.ContentType;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class ConditionHelper extends AbstractHelper implements Serializable  {
	private static final long serialVersionUID = 1L;

	private static ConditionHelper INSTANCE;
	
	public static ConditionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ConditionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	@Getter @Setter
	public static class Condition implements Serializable {
		private static final long serialVersionUID = 1L;

		static {
			ClassHelper.getInstance().map(Builder.class, Builder.Adapter.Default.class,Boolean.FALSE);
		}
		
		private Object identifier;
		private Boolean value;
		private String message;
		
		/**/
		
		@Override
		public String toString() {
			return identifier+","+value+","+message;
		}
		
		public static Condition.Builder.Null getBuilderNull(Object instance,String...names){
			return new Builder.Null.Adapter.Default().setFieldObject(instance).setFieldName(FieldHelper.getInstance().buildPath(names));
		}
		
		public static Condition.Builder.Comparison getBuilderComparison(Object instance,Object value,Boolean greater,Boolean equal,String...names){
			return new Builder.Comparison.Adapter.Default().setFieldObject(instance).setFieldName(FieldHelper.getInstance().buildPath(names))
					.setValue2(value).setGreater(greater).setEqual(equal);
		}
		
		public static Collection<Condition.Builder> getBuildersDoesNotBelongsTo(Object instance,Object from,Object to,String...names){
			Collection<Condition.Builder> collection = new ArrayList<ConditionHelper.Condition.Builder>();
			collection.add(ConditionHelper.Condition.getBuilderComparison(instance, from, Boolean.FALSE, Boolean.FALSE, names));
			collection.add(ConditionHelper.Condition.getBuilderComparison(instance, to, Boolean.TRUE, Boolean.FALSE, names));
			return collection;
		}
		
		public static Condition.Builder getBuilder(){
			return ClassHelper.getInstance().instanciateOne(Builder.class);
		}
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Condition> {
			
			Builder setFieldObject(Object fieldObject);
			Object getFieldObject();
			
			Builder setFieldName(String fieldName);
			String getFieldName();
			
			Builder setFieldValue(Object fieldValue);
			Object getFieldValue();
			
			Builder setFieldValueFormat(String fieldValueFormat);
			String getFieldValueFormat();
			
			Builder setDomainClass(Class<?> domainClass);
			Class<?> getDomainClass();
			
			String getDomainNameIdentifier();
			Builder setDomainNameIdentifier(String domainNameIdentifier);
			Builder setDomainNameIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			String getMessageIdentifier();
			Builder setMessageIdentifier(String messageIdentifier);
			Builder setMessageIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			String getValueNameIdentifier();
			Builder setValueNameIdentifier(String valueNameIdentifier);
			
			java.lang.Boolean getConditionValue();
			Builder setConditionValue(java.lang.Boolean conditionValue);
			
			java.lang.Class<?> getConditionIdentifierClass();
			Builder setConditionIdentifierClass(java.lang.Class<?> identifierClass);
			
			@Override Builder setIdentifier(Object identifier);
			
			@Override 
			Builder setInput(Object input);
			
			java.lang.Boolean getIsNegateConditionValue();
			Builder setIsNegateConditionValue(java.lang.Boolean isNegateConditionValue);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Condition> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String valueNameIdentifier;
				protected String messageIdentifier,domainNameIdentifier;
				protected java.lang.Boolean conditionValue,isNegateConditionValue;
				protected Object fieldObject,fieldValue;
				protected String fieldName,fieldValueFormat;
				protected Class<?> domainClass,conditionIdentifierClass;
				
				public Adapter() {
					super(Condition.class);
				}
				
				public Builder setIsNegateConditionValue(java.lang.Boolean isNegateConditionValue){
					return null;
				}
				
				@Override
				public Builder setFieldValueFormat(String fieldValueFormat){
					return null;
				}
				
				@Override
				public Builder setValueNameIdentifier(String valueNameIdentifier){
					return null;
				}
				
				@Override
				public Builder setMessageIdentifier(String messageIdentifier){
					return null;
				}
				
				@Override
				public Builder setMessageIdentifier(StringHelper.Builder messageIdentifierBuilder){
					return null;
				}
				
				@Override
				public Builder setDomainNameIdentifier(String domainNameIdentifier){
					return null;
				}
				
				@Override
				public Builder setDomainNameIdentifier(StringHelper.Builder domainNameIdentifierBuilder){
					return null;
				}
				
				@Override
				public Builder setConditionValue(java.lang.Boolean conditionValue){
					return null;
				}
				
				@Override
				public Builder setFieldObject(Object fieldObject) {
					return null;
				}
				
				@Override
				public Builder setFieldValue(Object fieldValue) {
					return null;
				}
				
				@Override
				public Builder setFieldName(String fieldName) {
					return null;
				}
				
				@Override
				public Builder setDomainClass(Class<?> domainClass) {
					return null;
				}
				
				@Override
				public Builder setInput(Object input) {
					return (Builder) super.setInput(input);
				}
				
				@Override
				public Builder setIdentifier(Object identifier) {
					return (Builder) super.setIdentifier(identifier);
				}
				
				@Override
				public Builder setConditionIdentifierClass(Class<?> conditionIdentifierClass) {
					return null;
				}
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					{
						setFieldValueFormat("(%s)");
					}
					
					@Override
					public Builder setIsNegateConditionValue(Boolean isNegateConditionValue) {
						this.isNegateConditionValue = isNegateConditionValue;
						return this;
					}
					
					@Override
					public Builder setFieldObject(Object fieldObject) {
						this.fieldObject = fieldObject;
						return this;
					}
					
					@Override
					public Builder setFieldName(String fieldName) {
						this.fieldName = fieldName;
						return this;
					}
					
					@Override
					public Builder setFieldValue(Object fieldValue) {
						this.fieldValue = fieldValue;
						return this;
					}
					
					@Override
					public Builder setFieldValueFormat(String fieldValueFormat) {
						this.fieldValueFormat = fieldValueFormat;
						return this;
					}
					
					@Override
					public Builder setMessageIdentifier(String messageIdentifier){
						this.messageIdentifier = messageIdentifier;
						return this;
					}
					
					@Override
					public Builder setMessageIdentifier(StringHelper.Builder messageIdentifierBuilder){
						setMessageIdentifier(messageIdentifierBuilder.execute());
						return this;
					}
					
					@Override
					public Builder setDomainNameIdentifier(String domainNameIdentifier){
						this.domainNameIdentifier = domainNameIdentifier;
						return this;
					}
					
					@Override
					public Builder setDomainNameIdentifier(StringHelper.Builder domainNameIdentifierBuilder){
						setDomainNameIdentifier(domainNameIdentifierBuilder.execute());
						return this;
					}
					
					@Override
					public Builder setDomainClass(Class<?> domainClass) {
						this.domainClass = domainClass;
						return this;
					}
					
					@Override
					public Builder setIdentifier(Object identifier) {
						this.identifier = identifier;
						return this;
					}
					
					@Override
					public Builder setInput(Object input) {
						return (Builder) super.setInput(input);
					}
					
					@Override
					public Builder setValueNameIdentifier(String valueNameIdentifier) {
						this.valueNameIdentifier = valueNameIdentifier;
						return this;
					}
					
					@Override
					public Builder setConditionValue(java.lang.Boolean conditionValue) {
						this.conditionValue = conditionValue;
						return this;
					}
					
					@Override
					public Builder setConditionIdentifierClass(Class<?> conditionIdentifierClass) {
						this.conditionIdentifierClass = conditionIdentifierClass;
						return this;
					}
					
					@Override
					protected Condition __execute__() {
						Condition condition = new Condition();
						condition.setIdentifier(getIdentifier());
						condition.setValue(getConditionValue());
						java.lang.reflect.Field field = getFieldObject() == null || StringHelper.getInstance().isBlank(getFieldName()) ? null 
								: FieldHelper.getInstance().get(getFieldObject().getClass(), getFieldName());
						loggingMessageBuilder.addNamedParameters("object",getFieldObject(),"field",field);
						Object value = getFieldValue();
						if(value == null){
							value = field == null ? null : FieldHelper.getInstance().read(getFieldObject(), getFieldName());
						}
						loggingMessageBuilder.addNamedParameters("value",value);
						____execute____(condition,getFieldObject(), field, value);
						
						if(condition.getValue()!=null && Boolean.TRUE.equals(getIsNegateConditionValue()))
							condition.setValue(!condition.getValue());
						
						if(condition.getIdentifier() == null){
							FieldHelper.Field __field__  = field == null ? null : FieldHelper.Field.get(getFieldObject().getClass(), getFieldName());
							if(field != null){
								Class<?> identifierClass = getConditionIdentifierClass();
								if(identifierClass!=null)
									condition.setIdentifier(__field__.getIdentifier(identifierClass));
							}
						}
						
						if(java.lang.Boolean.TRUE.equals(condition.getValue())){
							String messageIdentifier = getMessageIdentifier();
							if(StringHelper.getInstance().isBlank(messageIdentifier)) {
								//if(field == null)
								//	messageIdentifier = "condition.default";
								//else
									messageIdentifier = "condition.entity.field.value";
							}
							
							String domainName;
							if(StringHelper.getInstance().isBlank(getDomainNameIdentifier()))
								domainName = StringHelper.getInstance().getClazz(getFieldObject().getClass(),CaseType.L);
							else
								domainName = StringHelper.getInstance().get(getDomainNameIdentifier(),CaseType.L, new Object[]{});
							
							String valueName;
							if(StringHelper.getInstance().isBlank(getValueNameIdentifier()))
								valueName = StringHelper.getInstance().getField(field.getName(),CaseType.L);
							else
								valueName = StringHelper.getInstance().get(getValueNameIdentifier(),CaseType.L, new Object[]{});
							
							condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(messageIdentifier).setCaseType(CaseType.FU)
									.addManyParameters(getParameters(condition, getFieldObject(), field, value, domainName, valueName)).execute());
						}			
						
						return condition;
					}
					
					protected void ____execute____(Condition condition,Object instance,java.lang.reflect.Field field,Object value){}
					
					protected Object[] getParameters(Condition condition,Object instance,java.lang.reflect.Field field,Object value,String domainName,String valueName){
						return new Object[]{domainName,getFieldValueName(condition, instance, field,value, valueName),getFieldValue(condition, instance, field, value)
								,getValueMustBe(condition, instance, field, valueName)};
					}
					
					protected String getFieldValueName(Condition condition,Object instance,java.lang.reflect.Field field,Object value,String name) {
						return name;
					}
					
					protected String getFieldValue(Condition condition,Object instance,java.lang.reflect.Field field,Object value) {
						String string = formatValue(value);
						return StringHelper.getInstance().isBlank(string) ? Constant.EMPTY_STRING : String.format(getFieldValueFormat(),string);
					}
					
					protected String getValueMustBe(Condition condition,Object instance,java.lang.reflect.Field field,Object value) {
						return "??? MUST BE ???";
					}
					
					protected String formatValue(Object value) {
						String string;
						if(value == null)
							string = Constant.EMPTY_STRING;
						else if(value instanceof Date)
							string = new TimeHelper.Stringifier.Date.Adapter.Default((Date)value).execute();
						else
							string = value.toString();
						return string;
					}
				
				}
				
			}
			
			public static interface Null extends Builder {
				
				@Override Null setDomainNameIdentifier(String domainNameIdentifier);
				
				@Override Null setValueNameIdentifier(String valueNameIdentifier);
				
				Null setFieldObject(Object fieldObject);
				
				Null setFieldName(String fieldName);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Null,Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public Null setDomainNameIdentifier(String domainNameIdentifier) {
						return (Null) super.setDomainNameIdentifier(domainNameIdentifier);
					}
					
					@Override
					public Null setValueNameIdentifier(String valueNameIdentifier) {
						return (Null) super.setValueNameIdentifier(valueNameIdentifier);
					}
					
					@Override
					public Null setFieldObject(Object fieldObject) {
						return (Null) super.setFieldObject(fieldObject);
					}
					
					@Override
					public Null setFieldName(String fieldName) {
						return (Null) super.setFieldName(fieldName);
					}
					
					public static class Default extends Null.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setConditionIdentifierClass(Null.class);
						}
						
						@Override
						protected void ____execute____(Condition condition,Object instance, Field field, Object value) {
							/*FieldHelper.Field __field__  = field == null ? null : FieldHelper.Field.get(instance.getClass(), getFieldName());
							if(field == null || __field__.getConstraints().getIsNullable() == null || Boolean.TRUE.equals(__field__.getConstraints().getIsNullable()))
								condition.setValue(value == null);
							else
								condition.setValue(value != null);
							
							condition.setValue(
								field == null ? Boolean.TRUE : (__field__.getConstraints().getIsNullable() == null || Boolean.TRUE.equals(__field__.getConstraints().getIsNullable()))
								&& value == null
								);
							*/
							condition.setValue(value == null);
						}

						@Override
						protected String getValueMustBe(Condition condition, Object instance, Field field, Object value) {
							return StringHelper.getInstance().get((Boolean.TRUE.equals(getIsNegateConditionValue()) ? Constant.EMPTY_STRING : "not.")+"null.__feminine__",CaseType.L, new Object[] {});
						}
					
					}	
				}
			}
			
			/**
			 * 
			 * Arithmetic comparison of two values. value can be number , date and so on.
			 *
			 */
			public static interface Comparison extends Builder {
				
				@Override Comparison setDomainNameIdentifier(String domainNameIdentifier);
				
				Comparison setValue1(Object value);
				Object getValue1();
				
				Comparison setValue2(Object value);
				Object getValue2();
				
				Comparison setGreater(java.lang.Boolean greater);
				java.lang.Boolean getGreater();
				
				Comparison setEqual(java.lang.Boolean equal);
				java.lang.Boolean getEqual();
				
				@Override Comparison setValueNameIdentifier(String valueNameIdentifier);
				
				@Override Comparison setFieldObject(Object fieldObject);
				@Override Comparison setFieldName(String fieldName);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Comparison,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Object value1,value2;
					protected java.lang.Boolean equal,greater;
					
					@Override
					public Comparison setDomainNameIdentifier(String domainNameIdentifier) {
						return (Comparison) super.setDomainNameIdentifier(domainNameIdentifier);
					}
					
					@Override
					public Comparison setValueNameIdentifier(String valueNameIdentifier) {
						return (Comparison) super.setValueNameIdentifier(valueNameIdentifier);
					}
					
					@Override
					public Comparison setValue1(Object value){
						return null;
					}
					
					@Override
					public Comparison setValue2(Object value){
						return null;
					}
					
					@Override
					public Comparison setGreater(java.lang.Boolean greater){
						return null;
					}
					
					@Override
					public Comparison setEqual(java.lang.Boolean equal){
						return null;
					}
					
					@Override
					public Comparison setFieldName(String fieldName) {
						return (Comparison) super.setFieldName(fieldName);
					}
					
					@Override
					public Comparison setFieldObject(Object fieldObject) {
						return (Comparison) super.setFieldObject(fieldObject);
					}
					
					public static class Default extends Comparison.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						public Comparison setValue1(Object value){
							this.value1 = value;
							return this;
						}
						
						@Override
						public Comparison setValue2(Object value){
							this.value2 = value;
							return this;
						}
						
						@Override
						public Comparison setGreater(java.lang.Boolean greater){
							this.greater = greater;
							return this;
						}
						
						@Override
						public Comparison setEqual(java.lang.Boolean equal){
							this.equal = equal;
							return this;
						}
						
						@Override
						protected void ____execute____(Condition condition, Object instance, Field field,Object value) {
							java.lang.Boolean greater = getGreater();
							java.lang.Boolean equal = getEqual();
							Number number1 = NumberHelper.getInstance().get(getValue1() == null ? value : getValue1());
							Number number2 = NumberHelper.getInstance().get(getValue2());
							loggingMessageBuilder.addNamedParameters("number1",number1,"number2",number2,"greater",greater,"equal",equal);
							condition.setValue(java.lang.Boolean.TRUE.equals(NumberHelper.getInstance().compare(number1,number2,greater,equal)));
						}
						
						@Override
						protected String getValueMustBe(Condition condition, Object instance, Field field,Object value) {
							return StringHelper.getInstance().get("__comparison__to__",CaseType.L, new Object[] {
									StringHelper.getInstance().getComparisonOperator(greater == null ? null : !greater, equal == null ? null : !equal
											, java.lang.Boolean.TRUE.equals(Boolean.FALSE), java.lang.Boolean.FALSE)
									, formatValue(getValue2())
							}); 
						}
					}	
				}
			
				/**/
				
				/**
				 * 
				 * Arithmetic comparison of the number of occurrence of a value 
				 *
				 */
				public static interface Count extends Comparison {
					
					@Override Count setFieldObject(Object fieldObject);
					@Override Count setFieldName(String fieldName);
					@Override Count setFieldValue(Object fieldValue);
					
					@Override Count setDomainNameIdentifier(String domainNameIdentifier);
					@Override Count setValueNameIdentifier(String valueNameIdentifier);
					@Override Count setDomainClass(Class<?> domainClass);
					
					@Getter @Setter
					public static class Adapter extends Comparison.Adapter.Default implements Count,Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						public Count setDomainNameIdentifier(String domainNameIdentifier) {
							return (Count) super.setDomainNameIdentifier(domainNameIdentifier);
						}
						
						@Override
						public Count setDomainClass(Class<?> domainClass) {
							return (Count) super.setDomainClass(domainClass);
						}
						
						@Override
						public Count setFieldObject(Object fieldObject) {
							return (Count) super.setFieldObject(fieldObject);
						}
						
						@Override
						public Count setFieldName(String fieldName) {
							return (Count) super.setFieldName(fieldName);
						}
						
						@Override
						public Count setFieldValue(Object fieldValue) {
							return (Count) super.setFieldValue(fieldValue);
						}
						
						@Override
						public Count setValueNameIdentifier(String valueNameIdentifier) {
							return (Count) super.setValueNameIdentifier(valueNameIdentifier);
						}
						
						public static class Default extends Count.Adapter implements Serializable {
							private static final long serialVersionUID = 1L;
							
							{
								setMessageIdentifier("condition.entity.instance.count");
								setFieldValueFormat("%s");
								setConditionIdentifierClass(Count.class);
							}
													
						}	
					}
				}
				
			}
			
			public static interface Contains extends Builder {
				
				@Override Contains setDomainNameIdentifier(String domainNameIdentifier);
				@Override Contains setValueNameIdentifier(String valueNameIdentifier);
				
				Contains setExtremity1(Comparison comparison);
				Object getExtremity1();
				
				Contains setExtremity2(Comparison comparison);
				Object getExtremity2();
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Contains,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Comparison extremity1,extremity2;
					
					@Override
					public Contains setDomainNameIdentifier(String domainNameIdentifier) {
						return (Contains) super.setDomainNameIdentifier(domainNameIdentifier);
					}
					
					@Override
					public Contains setValueNameIdentifier(String valueNameIdentifier) {
						return (Contains) super.setValueNameIdentifier(valueNameIdentifier);
					}
					
					@Override
					public Contains setExtremity1(Comparison comparison) {
						return null;
					}
					
					@Override
					public Contains setExtremity2(Comparison comparison) {
						return null;
					}
					
					public static class Default extends Contains.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						public Contains setExtremity1(Comparison comparison) {
							this.extremity1 = comparison;
							return this;
						}
						
						@Override
						public Contains setExtremity2(Comparison comparison) {
							this.extremity2 = comparison;
							return this;
						}
						
						@Override
						protected void ____execute____(Condition condition, Object instance, Field field,Object value) {
							Condition condition1 = getExtremity1().execute();
							Condition condition2 = getExtremity1().execute();
							condition.setValue(java.lang.Boolean.TRUE.equals(condition1.getValue()) || java.lang.Boolean.TRUE.equals(condition2.getValue()));
							condition.setMessage(StringHelper.getInstance().concatenate(new Object[] {condition1.getMessage(),condition2.getMessage()}
							, ContentType.DEFAULT.getNewLineMarker()));
						}
						
						
					}	
				}
			}
			
		}
		
	}
}
