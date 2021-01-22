package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.object.__static__.persistence.EntityLifeCycleListener;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface AuditableWhoDoneWhatWhen {

	String get__auditWho__();
	AuditableWhoDoneWhatWhen set__auditWho__(String who);
	
	String get__auditWhat__();
	AuditableWhoDoneWhatWhen set__auditWhat__(String what);
	
	String get__auditFunctionality__();
	AuditableWhoDoneWhatWhen set__auditFunctionality__(String functionality);
	
	Long get__auditWhenAsTimestamp__();
	AuditableWhoDoneWhatWhen set__auditWhenAsTimestamp__(Long whenAsTimestamp);
	
	String get__auditWhenAsString__();
	AuditableWhoDoneWhatWhen set__auditWhenAsString__(String whenAsString);
	
	/**/
	
	static void set(java.lang.Object object,String who,String functionality,String what) {
		if(!(object instanceof AuditableWhoDoneWhatWhen))
			return;
		AuditableWhoDoneWhatWhen instance = (AuditableWhoDoneWhatWhen) object;
		//Who
		if(StringHelper.isBlank(who))
			who = EntityLifeCycleListener.AbstractImpl.DEFAULT_USER_NAME;
		instance.set__auditWho__(who);
		
		//Functionality
		if(StringHelper.isBlank(functionality))
			functionality = EntityLifeCycleListener.AbstractImpl.DEFAULT_FUNCTIONALITY;
		instance.set__auditFunctionality__(functionality);
		
		//What
		instance.set__auditWhat__(what);
	}
}