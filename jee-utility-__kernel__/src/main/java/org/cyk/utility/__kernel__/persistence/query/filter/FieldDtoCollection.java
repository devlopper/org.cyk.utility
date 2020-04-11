package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObjectCollection;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(Field.Dto.class) @Deprecated
public class FieldDtoCollection extends AbstractRepresentationObjectCollection<Field.Dto> implements Serializable {
	private static final long serialVersionUID = 1L;

	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType,ValueUsageType valueUsageType,ArithmeticOperator arithmeticOperator) {
		Field.Dto fieldDto = new Field.Dto();
		fieldDto.setArithmeticOperator(arithmeticOperator);
		if(StringUtils.isBlank(klass))
			fieldDto.setName(path);
		else
			fieldDto.setField(new org.cyk.utility.__kernel__.field.FieldDto().setKlass(klass).setPath(path).setType(type));
		fieldDto.setValue(new Value.Dto().setContainer(valueContainer).setType(valueType).setUsageType(valueUsageType).setValue(value));
		return (FieldDtoCollection) add(fieldDto);
	}
	
	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType,ValueUsageType valueUsageType) {
		return add(klass,path, value,type, valueContainer,valueType,valueUsageType, null);
	}
	
	public FieldDtoCollection add(String klass,String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType) {
		return add(klass,path, value,type,valueContainer,valueType, null);
	}
	
	public FieldDtoCollection add(String klass,String path,Object value,ValueUsageType valueUsageType) {
		org.cyk.utility.__kernel__.field.FieldDto.Type type = null;
		Value.Dto.Container valueContainer = Value.Dto.Container.NONE;
		Value.Dto.Type valueType = null;
		String valueAsString = null;
		if(value != null) {
			if(value instanceof Collection) {
				type = org.cyk.utility.__kernel__.field.FieldDto.Type.COLLECTION;
				valueContainer = Value.Dto.Container.COLLECTION;
				valueType = Value.Dto.Type.STRING;
			}else if(value instanceof String) {
				type = org.cyk.utility.__kernel__.field.FieldDto.Type.STRING;
				valueType = Value.Dto.Type.STRING;
				valueAsString = (String) value;
			}else if(value instanceof Integer) {
				type = org.cyk.utility.__kernel__.field.FieldDto.Type.INTEGER;
				valueType = Value.Dto.Type.INTEGER;
			}
			if(StringHelper.isBlank(valueAsString))
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
