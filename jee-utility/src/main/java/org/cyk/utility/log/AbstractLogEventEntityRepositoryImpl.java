package org.cyk.utility.log;

import java.io.Serializable;

import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.instance.AbstractInstanceRepositoryImpl;

public abstract class AbstractLogEventEntityRepositoryImpl extends AbstractInstanceRepositoryImpl<LogEventEntity> implements LogEventEntityRepository, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Override
    public LogLevel getLastLevel() {
    	return getLast().getLevel();
    }
    
    @Override
    public String getLastMessage() {
    	return getLast().getMessage();
    }
    
}