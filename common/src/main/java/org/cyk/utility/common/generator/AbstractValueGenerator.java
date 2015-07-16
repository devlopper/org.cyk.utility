package org.cyk.utility.common.generator;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractValueGenerator<T> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1759040479499294862L;

	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	
	public abstract T generate();

}
