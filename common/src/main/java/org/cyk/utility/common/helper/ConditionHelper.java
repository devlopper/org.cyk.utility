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
			
			@Override 
			Builder setInput(Object input);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Condition> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String valueNameIdentifier;
				protected String messageIdentifier,domainNameIdentifier;
				
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
				public Builder setInput(Object input) {
					return (Builder) super.setInput(input);
				}
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
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
					public Builder setInput(Object input) {
						return (Builder) super.setInput(input);
					}
					
					@Override
					public Builder setValueNameIdentifier(String valueNameIdentifier) {
						this.valueNameIdentifier = valueNameIdentifier;
						return this;
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
							Condition condition = new Condition();
							Long count = getValueCount();
							condition.setValue(count!=null && count>0);
							if(Boolean.TRUE.equals(condition.getValue()))
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
				
				Comparison setGreater(Boolean greater);
				Boolean getGreater();
				
				Comparison setEqual(Boolean equal);
				Boolean getEqual();
				
				@Override Comparison setValueNameIdentifier(String valueNameIdentifier);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Comparison,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Number number1,number2;
					protected Boolean equal,greater;
					
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
					public Comparison setGreater(Boolean greater){
						return null;
					}
					
					@Override
					public Comparison setEqual(Boolean equal){
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
						public Comparison setGreater(Boolean greater){
							this.greater = greater;
							return this;
						}
						
						@Override
						public Comparison setEqual(Boolean equal){
							this.equal = equal;
							return this;
						}
						
						@Override
						protected Condition __execute__() {
							Condition condition = new Condition();
							Boolean greater = getGreater();
							Boolean equal = getEqual();
							Number number1 = getNumber1();
							Number number2 = getNumber2();
							String valueNameIdentifier = getValueNameIdentifier();
							Boolean masculine = StringHelper.getInstance().isMasculine(valueNameIdentifier);
							condition.setValue(Boolean.TRUE.equals(NumberHelper.getInstance().compare(number1,number2,greater,equal)));
							if(Boolean.TRUE.equals(condition.getValue()))
								condition.setMessage(new StringHelper.ToStringMapping.Adapter.Default(getMessageIdentifier()).setCaseType(CaseType.FU)
									.addManyParameters( new StringHelper.ToStringMapping.Adapter.Default(valueNameIdentifier).setProperty(StringHelper.ToStringMapping.PROPERTY_NAME_GENDER, Boolean.TRUE).execute()
											,number1,StringHelper.getInstance().getComparisonOperator(!greater, !equal, masculine, Boolean.FALSE),number2)
									.execute());
							return condition;
						}
					}	
				}
			}
			
		}
		
	}
}
