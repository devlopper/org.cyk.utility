package org.cyk.utility.test;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

public abstract class AbstractArquillianIntegrationTest extends AbstractArquillianTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected UserTransaction userTransaction;
	
}
