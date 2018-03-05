package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.inject.Singleton;

import org.cyk.utility.common.helper.StringHelper.CaseType;

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
		
		public static Condition.Builder getBuilder(){
			return ClassHelper.getInstance().instanciateOne(Builder.class);
		}
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Condition> {
			
			Builder setFieldObject(Object fieldObject);
			Object getFieldObject();
			
			Builder setFieldName(String fieldName);
			String getFieldName();
			
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
			
			@Override Builder setIdentifier(Object identifier);
			
			@Override 
			Builder setInput(Object input);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Condition> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String valueNameIdentifier;
				protected String messageIdentifier,domainNameIdentifier;
				protected java.lang.Boolean conditionValue;
				protected Object fieldObject;
				protected String fieldName;
				
				public Adapter() {
					super(Condition.class);
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
				public Builder setFieldName(String fieldName) {
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
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					{
						setMessageIdentifier("condition.default");
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
					protected Condition __execute__() {
						Condition condition = new Condition();
						condition.setIdentifier(getIdentifier());
						condition.setValue(getConditionValue());
						java.lang.reflect.Field field = getFieldObject() == null || StringHelper.getInstance().isBlank(getFieldName()) ? null 
								: FieldHelper.getInstance().get(getFieldObject().getClass(), getFieldName());
						
						Object value = field==null ? null : FieldHelper.getInstance().read(getFieldObject(), field);
						____execute____(condition,getFieldObject(), field, value);
						
						if(java.lang.Boolean.TRUE.equals(condition.getValue())){
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
							
							condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(getMessageIdentifier()).setCaseType(CaseType.FU)
									.addManyParameters(getParameters(condition, getFieldObject(), field, value, domainName, valueName)).execute());
						}			
						
						return condition;
					}
					
					protected void ____execute____(Condition condition,Object instance,java.lang.reflect.Field field,Object value){}
					
					protected Object[] getParameters(Condition condition,Object instance,java.lang.reflect.Field field,Object value,String domainName,String valueName){
						return new Object[]{domainName,valueName,getInput()};
					}
				}
				
			}
			
			public static interface Duplicate extends Builder {
				
				Long getValueCount();
				Duplicate setValueCount(Long valueCount);
				
				@Override Duplicate setValueNameIdentifier(String valueNameIdentifier);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Duplicate,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Long valueCount;
					
					@Override
					public Duplicate setValueCount(Long valueCount){
						return null;
					}
					
					@Override
					public Duplicate setValueNameIdentifier(String valueNameIdentifier) {
						return (Duplicate) super.setValueNameIdentifier(valueNameIdentifier);
					}
					
					public static class Default extends Duplicate.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setMessageIdentifier("condition.duplicate");
						}
						
						@Override
						public Duplicate setValueCount(Long valueCount) {
							this.valueCount = valueCount;
							return this;
						}
						
						@Override
						protected void ____execute____(Condition condition, Object instance, Field field,Object value) {
							Long count = getValueCount();
							condition.setValue(count!=null && count>0);
						}
						
					}	
				}
			}
			
			public static interface Comparison extends Builder {
				
				@Override Comparison setDomainNameIdentifier(String domainNameIdentifier);
				
				Comparison setNumber1(Number number);
				Number getNumber1();
				
				Comparison setNumber2(Number number);
				Number getNumber2();
				
				Comparison setGreater(java.lang.Boolean greater);
				java.lang.Boolean getGreater();
				
				Comparison setEqual(java.lang.Boolean equal);
				java.lang.Boolean getEqual();
				
				@Override Comparison setValueNameIdentifier(String valueNameIdentifier);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Comparison,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Number number1,number2;
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
					public Comparison setNumber1(Number number){
						return null;
					}
					
					@Override
					public Comparison setNumber2(Number number){
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
					
					public static class Default extends Comparison.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setMessageIdentifier("condition.comparison");
						}
						
						@Override
						public Comparison setNumber1(Number number){
							this.number1 = number;
							return this;
						}
						
						@Override
						public Comparison setNumber2(Number number){
							this.number2 = number;
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
							condition.setValue(java.lang.Boolean.TRUE.equals(NumberHelper.getInstance().compare(getNumber1(),getNumber2(),greater,equal)));
						}
						
						@Override
						protected Object[] getParameters(Condition condition, Object instance, Field field,Object value, String domainName, String valueName) {
							java.lang.Boolean masculine = StringHelper.getInstance().isMasculine(valueNameIdentifier);
							return new Object[]{
									new StringHelper.ToStringMapping.Adapter.Default(valueNameIdentifier).setProperty(StringHelper.ToStringMapping.PROPERTY_NAME_GENDER, java.lang.Boolean.TRUE).execute()
									,number1,StringHelper.getInstance().getComparisonOperator(greater == null ? null : !greater, equal == null ? null : !equal
											, java.lang.Boolean.TRUE.equals(masculine), java.lang.Boolean.FALSE),number2
							};
						}
					}	
				}
			}
			
			public static interface Contains extends Builder {
				
				@Override Comparison setDomainNameIdentifier(String domainNameIdentifier);
				
				Comparison setNumber1(Number number);
				Number getNumber1();
				
				Comparison setNumber2(Number number);
				Number getNumber2();
				
				Comparison setGreater(java.lang.Boolean greater);
				java.lang.Boolean getGreater();
				
				Comparison setEqual(java.lang.Boolean equal);
				java.lang.Boolean getEqual();
				
				@Override Comparison setValueNameIdentifier(String valueNameIdentifier);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Comparison,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Number number1,number2;
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
					public Comparison setNumber1(Number number){
						return null;
					}
					
					@Override
					public Comparison setNumber2(Number number){
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
					
					public static class Default extends Comparison.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setMessageIdentifier("condition.comparison");
						}
						
						@Override
						public Comparison setNumber1(Number number){
							this.number1 = number;
							return this;
						}
						
						@Override
						public Comparison setNumber2(Number number){
							this.number2 = number;
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
							condition.setValue(java.lang.Boolean.TRUE.equals(NumberHelper.getInstance().compare(getNumber1(),getNumber2(),greater,equal)));
						}
						
						@Override
						protected Object[] getParameters(Condition condition, Object instance, Field field,Object value, String domainName, String valueName) {
							java.lang.Boolean masculine = StringHelper.getInstance().isMasculine(valueNameIdentifier);
							return new Object[]{
									new StringHelper.ToStringMapping.Adapter.Default(valueNameIdentifier).setProperty(StringHelper.ToStringMapping.PROPERTY_NAME_GENDER, java.lang.Boolean.TRUE).execute()
									,number1,StringHelper.getInstance().getComparisonOperator(greater == null ? null : !greater, equal == null ? null : !equal
											, java.lang.Boolean.TRUE.equals(masculine), java.lang.Boolean.FALSE),number2
							};
						}
					}	
				}
			}
			
			public static interface NotNull extends Builder {
				
				@Override NotNull setDomainNameIdentifier(String domainNameIdentifier);
				
				@Override NotNull setValueNameIdentifier(String valueNameIdentifier);
				
				NotNull setFieldObject(Object fieldObject);
				
				NotNull setFieldName(String fieldName);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements NotNull,Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public NotNull setDomainNameIdentifier(String domainNameIdentifier) {
						return (NotNull) super.setDomainNameIdentifier(domainNameIdentifier);
					}
					
					@Override
					public NotNull setValueNameIdentifier(String valueNameIdentifier) {
						return (NotNull) super.setValueNameIdentifier(valueNameIdentifier);
					}
					
					@Override
					public NotNull setFieldObject(Object fieldObject) {
						return (NotNull) super.setFieldObject(fieldObject);
					}
					
					@Override
					public NotNull setFieldName(String fieldName) {
						return (NotNull) super.setFieldName(fieldName);
					}
					
					public static class Default extends NotNull.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setMessageIdentifier("condition.notnull");
						}
						
						@Override
						protected void ____execute____(Condition condition,Object instance, Field field, Object value) {
							condition.setValue(value == null);
						}

					}	
				}
			}
			
		}
		
	}
}
