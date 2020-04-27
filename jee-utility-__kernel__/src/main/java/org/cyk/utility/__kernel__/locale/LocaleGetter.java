package org.cyk.utility.__kernel__.locale;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface LocaleGetter {

	Locale get(Arguments arguments);
	
	default Locale get() {
		return get(null);
	}
	
	public static abstract class AbstractImpl extends AbstractObject implements LocaleGetter,Serializable  {
		public static Locale DEFAULT = Locale.FRENCH;
		
		@Override
		public Locale get(Arguments arguments) {
			if(arguments == null)
				return DEFAULT;
			return DEFAULT;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Locale default_;
	}
	
	/**/
	
	static LocaleGetter getInstance() {
		return Helper.getInstance(LocaleGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}