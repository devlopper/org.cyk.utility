package org.cyk.utility.common.helper;

import java.io.Serializable;

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
			
			String getDomainNameIdentifier();
			Builder setDomainNameIdentifier(String domainNameIdentifier);
			Builder setDomainNameIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			String getMessageIdentifier();
			Builder setMessageIdentifier(String messageIdentifier);
			Builder setMessageIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			String getValueNameIdentifier();
			Builder setValueNameIdentifier(String valueNameIdentifier);
			
			Object getConditionIdentifier();
			Builder setConditionIdentifier(Object conditionIdentifier);
			
			java.lang.Boolean getConditionValue();
			Builder setConditionValue(java.lang.Boolean conditionValue);
			
			@Override 
			Builder setInput(Object input);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Condition> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Object conditionIdentifier;
				protected String valueNameIdentifier;
				protected String messageIdentifier,domainNameIdentifier;
				protected java.lang.Boolean conditionValue;
				
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
				public Builder setConditionIdentifier(Object conditionIdentifier){
					return null;
				}
				
				@Override
				public Builder setInput(Object input) {
					return (Builder) super.setInput(input);
				}
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					{
						setMessageIdentifier("condition.default");
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
					public Builder setConditionIdentifier(Object conditionIdentifier) {
						this.conditionIdentifier = conditionIdentifier;
						return this;
					}
					
					@Override
					protected Condition __execute__() {
						Condition condition = new Condition();
						condition.setIdentifier(getIdentifier());
						condition.setValue(getConditionValue());
						if(java.lang.Boolean.TRUE.equals(condition.getValue()))
							condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(getMessageIdentifier()).setCaseType(CaseType.FU)
									/*.addManyParameters(new StringHelper.ToStringMapping.Adapter.Default(getDomainNameIdentifier()).setCaseType(CaseType.L).execute()
											,new StringHelper.ToStringMapping.Adapter.Default(getValueNameIdentifier()).setCaseType(CaseType.L).execute(),getInput())*/
									.execute());
						return condition;
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
						protected Condition __execute__() {
							Condition condition = super.__execute__();
							Long count = getValueCount();
							condition.setValue(count!=null && count>0);
							if(java.lang.Boolean.TRUE.equals(condition.getValue()))
								condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(getMessageIdentifier()).setCaseType(CaseType.FU)
										.addManyParameters(new StringHelper.ToStringMapping.Adapter.Default(getDomainNameIdentifier()).setCaseType(CaseType.L).execute()
												,new StringHelper.ToStringMapping.Adapter.Default(getValueNameIdentifier()).setCaseType(CaseType.L).execute(),getInput())
										.execute());
							return condition;
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
						protected Condition __execute__() {
							Condition condition = super.__execute__();
							java.lang.Boolean greater = getGreater();
							java.lang.Boolean equal = getEqual();
							Number number1 = getNumber1();
							Number number2 = getNumber2();
							String valueNameIdentifier = getValueNameIdentifier();
							java.lang.Boolean masculine = StringHelper.getInstance().isMasculine(valueNameIdentifier);
							condition.setValue(java.lang.Boolean.TRUE.equals(NumberHelper.getInstance().compare(number1,number2,greater,equal)));
							if(java.lang.Boolean.TRUE.equals(condition.getValue()))
								condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(getMessageIdentifier()).setCaseType(CaseType.FU)
									.addManyParameters( new StringHelper.ToStringMapping.Adapter.Default(valueNameIdentifier).setProperty(StringHelper.ToStringMapping.PROPERTY_NAME_GENDER, java.lang.Boolean.TRUE).execute()
											,number1,StringHelper.getInstance().getComparisonOperator(greater == null ? null : !greater, equal == null ? null : !equal
													, java.lang.Boolean.TRUE.equals(masculine), java.lang.Boolean.FALSE),number2)
									.execute());
							return condition;
						}
					}	
				}
			}
			
		}
		
	}
}
