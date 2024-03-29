package org.cyk.utility.log;

import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.instance.InstanceRepository;

public interface LogEventEntityRepository extends InstanceRepository<LogEventEntity> {
	
	LogLevel getLastLevel();
	String getLastMessage();
}
