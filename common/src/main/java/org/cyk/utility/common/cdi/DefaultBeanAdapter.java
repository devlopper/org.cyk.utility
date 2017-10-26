package org.cyk.utility.common.cdi;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultBeanAdapter extends BeanListener.Adapter implements Serializable {

	private static final long serialVersionUID = -336945445877746426L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBeanAdapter.class);
	
	@Override
	public void info(String message) {
		LOGGER.info(message);
	}
	
	

}
