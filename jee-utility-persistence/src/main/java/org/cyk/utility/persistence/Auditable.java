package org.cyk.utility.persistence;

public interface Auditable {

	String FIELD___AUDIT_RECORDS__ = "__auditRecords__";
	
	String COLUMN___AUDIT_WHO__ = "COLUMN___AUDIT_WHO__";
	String COLUMN___AUDIT_FUNCTIONALITY__ = "COLUMN___AUDIT_FUNCTIONALITY__";
	String COLUMN___AUDIT_WHAT__ = "COLUMN___AUDIT_WHAT__";
	String COLUMN___AUDIT_WHEN__ = "COLUMN___AUDIT_WHEN__";
}