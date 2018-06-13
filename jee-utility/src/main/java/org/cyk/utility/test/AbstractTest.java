package org.cyk.utility.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.assertion.AssertionHelper;

public abstract class AbstractTest extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	@Inject protected AssertionHelper assertionHelper;
	
}
