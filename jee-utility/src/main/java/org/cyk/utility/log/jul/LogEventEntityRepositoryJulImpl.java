package org.cyk.utility.log.jul;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.log.AbstractLogEventEntityRepositoryImpl;

@ApplicationScoped
public class LogEventEntityRepositoryJulImpl extends AbstractLogEventEntityRepositoryImpl implements LogEventEntityRepositoryJul, Serializable {
	private static final long serialVersionUID = 1L;

}
