package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Singleton;

import org.apache.commons.text.RandomStringGenerator;
import org.cyk.utility.common.Builder;

@Singleton
public class RandomHelper extends AbstractHelper implements Serializable {
	static final long serialVersionUID = 1L;
	
	private static RandomHelper INSTANCE;
	
	public static RandomHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new RandomHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> aClass){
		if(java.lang.String.class.equals(aClass))
			return (T) getString(5);
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	public String getString(Integer length){
		return getAlphanumeric(length);
	}
	
	public String getAlphanumeric(Integer length){
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
		String randomLetters = generator.generate(length);
		return randomLetters;
	}
	
	public String getAlphabetic(Integer length){
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String randomLetters = generator.generate(length);
		return randomLetters;
	}
	
	public Number getNumeric(Integer length){
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').build();
		String randomLetters = generator.generate(length);
		return new BigDecimal(randomLetters);
	}
	
	public static interface Random<OUTPUT> extends Builder.NullableInput<OUTPUT> {
		
		public static class Adapter<OUTPUT> extends Builder.NullableInput.Adapter.Default<OUTPUT> implements Random<OUTPUT> , Serializable {
			private static final long serialVersionUID = -5769331804489939201L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			public static class Default<OUTPUT> extends Random.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
			}
			
		}
		
		public static interface String extends Random<java.lang.String> {
			
			public static class Adapter extends Random.Adapter.Default<java.lang.String> implements String , Serializable {
				private static final long serialVersionUID = -5769331804489939201L;

				public Adapter() {
					super(java.lang.String.class);
				}
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
				
			}
		}
	}
	
}
