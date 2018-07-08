package org.cyk.utility.log.jul;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.log.AbstractLogEventRepositoryImpl;

@Singleton
public class LogEventRepositoryJulImpl extends AbstractLogEventRepositoryImpl implements LogEventRepositoryJul, Serializable {
	private static final long serialVersionUID = 1L;

}
