package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface InternationalizationStringGetter {

	String get(String identifier);

	String get(Arguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements InternationalizationStringGetter,Serializable {
		
		@Override
		public String get(Arguments arguments) {
			if(arguments == null)
				return null;
			String identifier = arguments.identifier;
			if(StringHelper.isBlank(identifier))
				return null;
			
			Locale locale = arguments.locale;
			if(locale == null)
				locale = Locale.FRENCH;
			
			Case kase = arguments.kase;
			if(kase == null)
				kase = Case.DEFAULT;
			
			return __get__(identifier, arguments.arguments, locale, kase);
		}
		
		protected String __get__(String identifier,Object[] arguments,Locale locale,Case kase) {
			throw new RuntimeException(String.format("Internationalization(i18n) of %s%s in %s not yet implemented", identifier,ArrayHelper.isEmpty(arguments) ? "" : Arrays.deepToString(arguments),locale));
		}
		
		@Override
		public String get(String identifier) {
			if(StringHelper.isBlank(identifier))
				return null;
			return get(new Arguments().setIdentifier(identifier));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String identifier;
		private Object[] arguments;
		private Locale locale;
		private Case kase;
	}
	
	/**/
	
	static InternationalizationStringGetter getInstance() {
		return DependencyInjection.inject(InternationalizationStringGetter.class);
	}
}