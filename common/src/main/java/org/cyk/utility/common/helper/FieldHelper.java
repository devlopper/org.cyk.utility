package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.ClassRepository;
import org.cyk.utility.common.Constant;

@Singleton
public class FieldHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String FIELD_NAME_SEPARATOR = Constant.CHARACTER_DOT.toString();
	
	public String buildPath(String...fieldNames){
		return StringUtils.join(fieldNames,FIELD_NAME_SEPARATOR);
	}
	
	public List<String> getFieldNames(String...fieldPaths){
		return Arrays.asList(StringUtils.split(buildPath(fieldPaths), FIELD_NAME_SEPARATOR));
	}
	
	public Object read(Object instance,String...fieldNames){
		try {
			return PropertyUtils.getProperty(instance, buildPath(fieldNames));
		} catch (Exception e) {
			logThrowable(e);
			return null;
		}
	}
	
	public void set(Object object,String...fieldNames){
		for(String p : getFieldNames(fieldNames)){
			Object pValue = read(object, p);
			if(pValue==null){
				Field field = ClassRepository.getInstance().getField(object.getClass(), p);
				try {
					FieldUtils.writeField(object, p, pValue = field.getType().newInstance(), Boolean.TRUE);
				} catch (Exception e) {
					new RuntimeException(e);
				}
				logTrace("Field {} of object {} instanciated",field,object);
			}
			object = pValue;
		}
	}
	
	public void set(Object instance,Object value,String...fieldNames){
		String path = buildPath(fieldNames);
		set(instance, path);//we want to be sure we can reach the latest field
		try {
			PropertyUtils.setProperty(instance, path, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
