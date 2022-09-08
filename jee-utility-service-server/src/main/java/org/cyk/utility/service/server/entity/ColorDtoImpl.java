package org.cyk.utility.service.server.entity;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.ColorDto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ColorDtoImpl implements ColorDto {

	@JsonbProperty(value = JSON_HEXADECIMAL)
	String hexadecimal;
	
}