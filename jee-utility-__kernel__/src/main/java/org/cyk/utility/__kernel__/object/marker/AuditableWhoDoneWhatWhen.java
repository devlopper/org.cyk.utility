package org.cyk.utility.__kernel__.object.marker;

import java.time.LocalDateTime;

public interface AuditableWhoDoneWhatWhen {

	String get__auditWho__();
	AuditableWhoDoneWhatWhen set__auditWho__(String who);
	
	String get__auditWhat__();
	AuditableWhoDoneWhatWhen set__auditWhat__(String what);
	
	String get__auditFunctionality__();
	AuditableWhoDoneWhatWhen set__auditFunctionality__(String functionality);
	
	LocalDateTime get__auditWhen__();
	AuditableWhoDoneWhatWhen set__auditWhen__(LocalDateTime when);
	
	Long get__auditWhenAsTimestamp__();
	AuditableWhoDoneWhatWhen set__auditWhenAsTimestamp__(Long whenAsTimestamp);
	
	String get__auditWhenAsString__();
	AuditableWhoDoneWhatWhen set__auditWhenAsString__(String whenAsString);
	
	String FIELD___AUDIT_RECORDS__ = "__auditRecords__";
}