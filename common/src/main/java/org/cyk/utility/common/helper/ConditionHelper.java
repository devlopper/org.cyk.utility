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

		private Boolean value;
		private String message;
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Condition> {
			
			String getDomainNameIdentifier();
			Builder setDomainNameIdentifier(String domainNameIdentifier);
			Builder setDomainNameIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			String getMessageIdentifier();
			Builder setMessageIdentifier(String messageIdentifier);
			Builder setMessageIdentifier(StringHelper.Builder messageIdentifierMapping);
			
			@Override 
			Builder setInput(Object input);
			
			@Getter @Setter
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Condition> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String messageIdentifier,domainNameIdentifier;
				
				public Adapter() {
					super(Condition.class);
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
				}
				
			}
			
			public static interface Duplicate extends Builder {
				
				String getValueNameIdentifier();
				Duplicate setValueNameIdentifier(String valueNameIdentifier);
				
				Long getValueCount();
				Duplicate setValueCount(Long valueCount);
				
				@Getter @Setter
				public static class Adapter extends Builder.Adapter.Default implements Duplicate,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected String valueNameIdentifier;
					protected Long valueCount;
					
					@Override
					public Duplicate setValueNameIdentifier(String valueNameIdentifier){
						return null;
					}
					
					@Override
					public Duplicate setValueCount(Long valueCount){
						return null;
					}
					
					public static class Default extends Duplicate.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						{
							setMessageIdentifier("condition.duplicate");
						}
						
						@Override
						public Duplicate setValueNameIdentifier(String valueNameIdentifier) {
							this.valueNameIdentifier = valueNameIdentifier;
							return this;
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
			
		}
		
	}
}
