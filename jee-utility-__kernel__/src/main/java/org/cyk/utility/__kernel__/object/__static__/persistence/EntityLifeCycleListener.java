package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EntityLifeCycleListener {

	void listen(java.lang.Object object,Event event,When when);
	
	public static abstract class AbstractImpl implements EntityLifeCycleListener,Serializable {
		public static String DEFAULT_USER_NAME = "ANONYMOUS";
		public static String DEFAULT_FUNCTIONALITY = "ANONYMOUS";
		
		@Override
		public void listen(java.lang.Object object,Event event, When when) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("object", object);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("event", event);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("when", when);
			switch(event) {
			case CREATE:
				if(When.BEFORE.equals(when))
					listenBeforeCreate(object);
				else if(When.AFTER.equals(when))
					listenAfterCreate(object);
				break;
			case READ:
				if(When.BEFORE.equals(when))
					throw new IllegalStateException("This is an illegal state : "+event+" : ");
				else if(When.AFTER.equals(when))
					listenAfterRead(object);
				break;
			case UPDATE:
				if(When.BEFORE.equals(when))
					listenBeforeUpdate(object);
				else if(When.AFTER.equals(when))
					listenAfterUpdate(object);
				break;
			case DELETE:
				if(When.BEFORE.equals(when))
					listenBeforeDelete(object);
				else if(When.AFTER.equals(when))
					listenAfterDelete(object);
				break;
			}
		}
		
		/**/
		
		protected void listenBeforeCreate(java.lang.Object object) {
			setAuditableWhoDoneWhatWhen(object, Event.CREATE);
		}
		
		protected void listenAfterCreate(java.lang.Object object) {
			
		}
		
		protected void listenAfterRead(java.lang.Object object) {
			
		}
		
		protected void listenBeforeUpdate(java.lang.Object object) {
			setAuditableWhoDoneWhatWhen(object, Event.UPDATE);
		}
		
		protected void listenAfterUpdate(java.lang.Object object) {
			
		}
		
		protected void listenBeforeDelete(java.lang.Object object) {
			setAuditableWhoDoneWhatWhen(object, Event.DELETE);
		}
		
		protected void listenAfterDelete(java.lang.Object object) {
			
		}
		
		/**/
		
		protected void setAuditableWhoDoneWhatWhen(java.lang.Object object,Event event) {
			if(!(object instanceof AuditableWhoDoneWhatWhen))
				return;
			((AuditableWhoDoneWhatWhen)object).set__auditWho__(ValueHelper.defaultToIfBlank(SessionHelper.getUserName(),DEFAULT_USER_NAME));
			((AuditableWhoDoneWhatWhen)object).set__auditWhat__(ValueHelper.defaultToIfBlank(event.getValue(),event.getValue()));
			((AuditableWhoDoneWhatWhen)object).set__auditWhen__(LocalDateTime.now());
		}
	}
	
	/**/
	
	static EntityLifeCycleListener getInstance() {
		return Helper.getInstance(EntityLifeCycleListener.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	@Getter @AllArgsConstructor
	public static enum Event {
		CREATE("CREATE")
		,READ("READ")
		,UPDATE("UPDATE")
		,DELETE("DELETE")
		
		;
		
		private String value;
		
		public Event setValue(String value) {
			this.value = value;
			return this;
		}
	}
	public static enum When {BEFORE,AFTER}
}