package org.cyk.utility.test.business.server;

import java.io.Serializable;

public interface Transaction extends org.cyk.utility.test.persistence.server.Transaction {

	public static abstract class AbstractImpl extends org.cyk.utility.test.persistence.server.Transaction.AbstractImpl implements Transaction,Serializable {

	}	
}