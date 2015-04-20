package org.cyk.utility.common.generator;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractValueGenerator<T> extends AbstractBean {

	private static final long serialVersionUID = 1759040479499294862L;

	//TODO should be removed
	public static boolean DEBUG = false;
	
	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	
	public abstract T generate();

}
