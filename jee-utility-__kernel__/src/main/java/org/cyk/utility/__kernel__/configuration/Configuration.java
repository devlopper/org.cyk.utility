package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;

import org.apache.commons.lang3.RegExUtils;
import org.cyk.utility.__kernel__.number.NumberHelper;

public interface Configuration {

	static abstract class AbstractConverter<T> implements  org.eclipse.microprofile.config.spi.Converter<T> ,Serializable{
		public static final String SPACE_MARKER = "__CHAR__SPACE__";
	    @Override
	    public T convert(String value) {
	    	if(value == null)
	        	return null;
	    	return __convert__(RegExUtils.replaceAll(value, SPACE_MARKER, " "));
	    }
	    
	    abstract T __convert__(String value);
	}
	
	static class StringConverter extends AbstractConverter<String>  implements Serializable {
		@Override
		String __convert__(String value) {
			return value;
		}
	}
	
	static class LongConverter extends AbstractConverter<Long>  implements Serializable {
		@Override
		Long __convert__(String value) {
			return NumberHelper.getLong(value,0l);
		}
	}
}