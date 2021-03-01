package org.cyk.utility.test.maven.pom;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Activation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean activeByDefault;

}
