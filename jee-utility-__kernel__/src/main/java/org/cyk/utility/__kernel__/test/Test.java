package org.cyk.utility.__kernel__.test;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Test extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/* inputs */
	
	private String name;
	private Runnable runnable;
	private Boolean logDuration = Boolean.TRUE;
	
	/* outputs */
	
	private Long duration;
	
	/* expectation */
	
	private Class<? extends Throwable> expectedThrowableClass;
	
}
