package org.cyk.utility.__kernel__.object.__static__.representation;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

/**
 * Handle java time api convertion
 * @author CYK
 *
 * @param <TIME>
 */
public abstract class AbstractTimeAdapter<TIME> extends XmlAdapter<String, TIME> implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
    public TIME unmarshal(String string) throws Exception {
        return (TIME) MethodUtils.invokeExactStaticMethod(__getTimeClass__(), METHOD_NAME_PARSE, new java.lang.Object[] {string,__getFormatter__()},new Class[] {CharSequence.class,DateTimeFormatter.class} );
    }

    @Override
    public String marshal(TIME time) throws Exception {
        return __getFormatter__().format((TemporalAccessor) time);
    }
    
    @SuppressWarnings("unchecked")
	protected Class<TIME> __getTimeClass__() {
    	return (Class<TIME>) ClassHelper.getParameterAt(getClass(), 0);
    }
    
    protected DateTimeFormatter __getFormatter__() {
    	Class<?> klass = __getTimeClass__();
    	Collection<String> strings = StringHelper.splitByCharacterTypeCamelCase(klass.getSimpleName());
    	String name = FORMATTER_NAME_START + StringUtils.join(strings.toArray(new String[0]),ConstantCharacter.UNDESCORE.toString()).toUpperCase();
    	try {
    		return (DateTimeFormatter) FieldUtils.readStaticField(DateTimeFormatter.class, name);
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
			return null;
		}
    }
    
    /**/
    
    private static final String METHOD_NAME_PARSE = "parse";
    private static final String FORMATTER_NAME_START = "ISO_";
}