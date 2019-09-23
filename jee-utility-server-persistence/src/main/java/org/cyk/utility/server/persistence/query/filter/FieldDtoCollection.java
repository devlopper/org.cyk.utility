package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObjectCollection;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueDto;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(FieldDto.class)
public class FieldDtoCollection extends AbstractRepresentationObjectCollection<FieldDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType,ValueUsageType valueUsageType,ArithmeticOperator arithmeticOperator) {
		FieldDto fieldDto = new FieldDto();
		fieldDto.setArithmeticOperator(arithmeticOperator);
		if(StringUtils.isBlank(klass))
			fieldDto.setName(path);
		else
			fieldDto.setField(new org.cyk.utility.field.FieldDto().setKlass(klass).setPath(path).setType(type));
		fieldDto.setValue(new ValueDto().setContainer(valueContainer).setType(valueType).setUsageType(valueUsageType).setValue(value));
		return (FieldDtoCollection) add(fieldDto);
	}
	
	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType,ValueUsageType valueUsageType) {
		return add(klass,path, value,type, valueContainer,valueType,valueUsageType, null);
	}
	
	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.field.FieldDto.Type type,ValueDto.Container valueContainer,ValueDto.Type valueType) {
		return add(klass,path, value,type,valueContainer,valueType, null);
	}
	
	public FieldDtoCollection add(String klass,String path,Object value,ValueUsageType valueUsageType) {
		org.cyk.utility.field.FieldDto.Type type = null;
		ValueDto.Container valueContainer = ValueDto.Container.NONE;
		ValueDto.Type valueType = null;
		String valueAsString = null;
		if(value != null) {
			if(value instanceof Collection) {
				type = org.cyk.utility.field.FieldDto.Type.COLLECTION;
				valueContainer = ValueDto.Container.COLLECTION;
				valueType = ValueDto.Type.STRING;
			}else if(value instanceof String) {
				type = org.cyk.utility.field.FieldDto.Type.STRING;
				valueType = ValueDto.Type.STRING;
				valueAsString = (String) value;
			}else if(value instanceof Integer) {
				type = org.cyk.utility.field.FieldDto.Type.INTEGER;
				valueType = ValueDto.Type.INTEGER;
			}
			if(__inject__(StringHelper.class).isBlank(valueAsString))
				try {
					valueAsString = JsonbBuilder.create().toJson(value);
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
		}
		return add(klass,path, valueAsString,type,valueContainer,valueType, valueUsageType);
	}
	
	public FieldDtoCollection add(String klass,String path,Object value) {
		return add(klass,path,value,null);
	}
}
