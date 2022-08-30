package org.cyk.utility.persistence.entity.audit;

import java.time.LocalDateTime;

public interface AuditedAction {

	String getWho();
	AuditedAction setWho(String who);
	
	LocalDateTime getWhen();
	AuditedAction setWhen(LocalDateTime when);
}