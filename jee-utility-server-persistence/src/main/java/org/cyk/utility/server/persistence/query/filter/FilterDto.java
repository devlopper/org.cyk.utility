package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.value.ValueDto;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @Deprecated
//@JsonIgnoreProperties(ignoreUnknown=true)
public class FilterDto extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String klass;
	//@JsonIgnoreProperties(value="size")
	private FieldDtoCollection fields;

	private String value;
	
	public FilterDto useKlass(Class<?> klass) {
		if(klass != null)
			this.klass = klass.getName();
		return this;
	}
	
	public FieldDtoCollection getFields(Boolean injectIfNull) {
		if(fields == null && Boolean.TRUE.equals(injectIfNull))
			fields = new FieldDtoCollection();
		return fields;
	}
	
	public FilterDto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType,ValueUsageType valueUsageType,ArithmeticOperator arithmeticOperator) {
		getFields(Boolean.TRUE).add(klass, path, value, type, valueContainer, valueType, valueUsageType, arithmeticOperator);
		return this;
	}
	
	public FilterDto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType,ValueUsageType valueUsageType) {
		getFields(Boolean.TRUE).add(klass, path, value, type, valueContainer, valueType, valueUsageType);
		return this;
	}
	
	public FilterDto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType) {
		getFields(Boolean.TRUE).add(klass, path, value, type, valueContainer, valueType);
		return this;
	}
	
	public FilterDto addField(String path,Object value,ValueUsageType valueUsageType) {
		getFields(Boolean.TRUE).add(klass, path, value, valueUsageType);
		return this;
	}
	
	public FilterDto addField(String path,Object value) {
		getFields(Boolean.TRUE).add(klass, path, value);
		return this;
	}
	
	/**/
	
	public static final String FIELD_FIELDS = "fields";
}
