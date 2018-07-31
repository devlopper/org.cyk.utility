package org.cyk.utility.assertion;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MyClass {

	private String f1;
	private String f2="v2";
	private Integer integer;
	private Integer integer2;
}
