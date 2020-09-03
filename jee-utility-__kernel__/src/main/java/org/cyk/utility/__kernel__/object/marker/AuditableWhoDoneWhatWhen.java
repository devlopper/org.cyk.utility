package org.cyk.utility.__kernel__.object.marker;

import java.time.LocalDateTime;

public interface AuditableWhoDoneWhatWhen {

	String get__auditWho__();
	AuditableWhoDoneWhatWhen set__auditWho__(String who);
	
	String get__auditWhat__();
	AuditableWhoDoneWhatWhen set__auditWhat__(String what);
	
	LocalDateTime get__auditWhen__();
	AuditableWhoDoneWhatWhen set__auditWhen__(LocalDateTime when);
	
}