package org.cyk.utility.__kernel__.test.arquillian.archive.builder;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Clazz implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Class<?> value;
}
