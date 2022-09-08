package org.cyk.utility.service.client.entity;
import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.service.entity.ColorDto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Color {

	@JsonbProperty(value = ColorDto.JSON_HEXADECIMAL)
	private String hexadecimal;
	
}