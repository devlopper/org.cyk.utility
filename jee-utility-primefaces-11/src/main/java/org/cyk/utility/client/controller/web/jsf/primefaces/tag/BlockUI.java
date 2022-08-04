package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.random.RandomHelper;

public class BlockUI extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public BlockUI() {
		setProperty(Properties.ANIMATE, Boolean.TRUE);
		setProperty(Properties.BLOCKED, Boolean.FALSE);
		setProperty(Properties.RENDERED, Boolean.TRUE);
		setProperty(Properties.IDENTIFIER, RandomHelper.getAlphabetic(5));
	}
	
}
