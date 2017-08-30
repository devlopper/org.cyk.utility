package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Singleton;

import org.cyk.utility.common.Builder;

@Singleton
public class LocaleHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static LocaleHelper INSTANCE;
	
	public static LocaleHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new LocaleHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}

	public Locale get(){
		return ClassHelper.getInstance().instanciateOne(Get.Adapter.Default.DEFAULT_CLASS == null 
				? Get.Adapter.Default.class : Get.Adapter.Default.DEFAULT_CLASS).execute();
	}
	
	/**/
	
	public interface Get extends Builder.NullableInput<Locale> {
		
		public static class Adapter extends Builder.NullableInput.Adapter.Default<Locale> implements Get,Serializable {

			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Locale.class);
			}
			
			/**/
			
			public static class Default extends Get.Adapter implements Serializable {

				public static Class<? extends Default> DEFAULT_CLASS = Get.Adapter.Default.class;
				
				private static final long serialVersionUID = 1L;

				@Override
				protected Locale __execute__() {
					return Locale.FRENCH;
				}
				
			}
		}
	}
}
