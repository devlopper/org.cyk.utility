package org.cyk.utility.server.business;

public interface BusinessFunctionRemover extends BusinessFunctionTransaction {

	BusinessFunctionRemover setAll(Boolean value);
	Boolean getAll();
}
