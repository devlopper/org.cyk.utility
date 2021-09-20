package org.cyk.utility.__kernel__.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.field.Field;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Value extends AbstractObject implements  Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Object object;
	private Field field;
	private String configurationValueName;
	
	private Object __value__;
	private Boolean __isValueHashBeenSet__;
	
	public Value set(Object value) {
		__isValueHashBeenSet__ = Boolean.TRUE;
		this.__value__ = value;
		return this;
	}
	
	public Object get() {
		if(isHasBeenSet() || (field == null && StringHelper.isBlank(configurationValueName)))
			return __value__;
		if(field != null)
			__value__ = FieldHelper.read(object, field.getPath());
		else if(StringHelper.isNotBlank(configurationValueName))
			__value__ = ConfigurationHelper.getValue(configurationValueName, null, null,null,null);
		return __value__;
	}
	
	public Value initialize() {
		__value__ = null;
		__isValueHashBeenSet__ = null;
		Object value = get();
		set(value);
		return this;
	}
	
	public Boolean isHasBeenSet() {
		return Boolean.TRUE.equals(__isValueHashBeenSet__);
	}
	
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String value;
		private Container container;
		private Type type;
		private ValueUsageType usageType;
		
		@Override
		public String toString() {
			Collection<String> strings = new ArrayList<>();
			if(StringHelper.isNotBlank(value))
				strings.add("Value="+value);
			if(container != null)
				strings.add("CONT="+container);
			if(type != null)
				strings.add("Type="+type);
			if(usageType != null)
				strings.add("UT="+usageType);
			return StringHelper.concatenate(strings, " ");
		}
		
		/**/
		
		public static enum Container {
			NONE,COLLECTION,MAP
		}
		
		public static enum Type {
			STRING,INTEGER,LONG
		}
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, Value> {
			
		}
	}

	/**/
	
	public static enum Type {
		CURRENCY
	}
}