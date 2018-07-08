package org.cyk.utility.log;

import java.io.Serializable;

import org.cyk.utility.instance.AbstractInstanceRepositoryImpl;

public abstract class AbstractLogEventRepositoryImpl extends AbstractInstanceRepositoryImpl<LogEventEntity> implements LogEventRepository, Serializable {
    private static final long serialVersionUID = 1L;
    
}