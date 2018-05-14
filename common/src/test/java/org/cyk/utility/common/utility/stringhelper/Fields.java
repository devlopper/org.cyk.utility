package org.cyk.utility.common.utility.stringhelper;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Fields implements Serializable {
	private static final long serialVersionUID = 1L;

	private String string;	
	
	private String electronicMailAddress;
	private String locality;
	
	private String myFieldLocality01;
	private @Text String myFieldLocality02;
	private @Text(value="") String myFieldLocality03;
	private @Text(value=" ") String myFieldLocality04;
	private @Text(value="locality") String myFieldLocality05;
	private @Text(value="buying.price",valueType=ValueType.ID) String myFieldLocality06;
	private @Text(value="locality",valueType=ValueType.VALUE) String myFieldLocality07;
	
	private BigDecimal valueGap;
	
	private SubFields subFields;
	
	/**/
	
	public static final String FIELD_STRING = "string";
}
