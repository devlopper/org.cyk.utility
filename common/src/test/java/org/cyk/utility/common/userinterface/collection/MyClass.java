package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code;
	private @Text(value="code") String code01;
	private @Text(value="CODE",valueType=ValueType.VALUE) String code02;
	private String name;
	private BigDecimal valueGap;
}
