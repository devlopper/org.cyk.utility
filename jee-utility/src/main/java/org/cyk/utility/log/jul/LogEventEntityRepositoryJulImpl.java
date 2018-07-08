package org.cyk.utility.log.jul;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.log.AbstractLogEventEntityRepositoryImpl;

@Singleton
public class LogEventEntityRepositoryJulImpl extends AbstractLogEventEntityRepositoryImpl implements LogEventEntityRepositoryJul, Serializable {
	private static final long serialVersionUID = 1L;

}
