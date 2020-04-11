package org.cyk.utility.__kernel__.value;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Dependent
public class Value extends AbstractObject implements  Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true) private String name;
	@Getter @Setter @Accessors(chain=true) private Object object;
	@Getter @Setter @Accessors(chain=true) private FieldInstance fieldInstance;
	@Getter @Setter @Accessors(chain=true) private String configurationValueName;
	
	private Object __value__;
	private Boolean __isValueHashBeenSet__;
	
	public Value set(Object value) {
		__isValueHashBeenSet__ = Boolean.TRUE;
		this.__value__ = value;
		return this;
	}
	
	public Object get() {
		if(isHasBeenSet() || (fieldInstance == null && StringHelper.isBlank(configurationValueName)))
			return __value__;
		if(fieldInstance != null)
			__value__ = FieldHelper.read(object, fieldInstance.getPath());
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
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
	public static class Dto extends AbstractObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String value;
		private Container container;
		private Type type;
		private ValueUsageType usageType;
		
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
}