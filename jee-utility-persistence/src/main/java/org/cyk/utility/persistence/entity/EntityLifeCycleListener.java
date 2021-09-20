package org.cyk.utility.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EntityLifeCycleListener {

	void listen(java.lang.Object object,Event event,When when);
	
	public static abstract class AbstractImpl implements EntityLifeCycleListener,Serializable {
		public static String DEFAULT_USER_NAME = "UNKNOWN";
		public static String DEFAULT_ACTION = "UNKNOWN";
		public static String DEFAULT_FUNCTIONALITY = "UNKNOWN";
		
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
			AuditableWhoDoneWhatWhen instance = (AuditableWhoDoneWhatWhen) object;
			setAuditableWhoDoneWhatWhenIfBlank(
					instance
					, instance.get__auditWho__() //instance.set__auditWho__(ValueHelper.defaultToIfBlank(SessionHelper.getUserName(),DEFAULT_USER_NAME)); //TODO how to get principal in back end ?
					, instance.get__auditFunctionality__()
					, event.getValue()
					, LocalDateTime.now()
				);
		}
		
		public static void setAuditableWhoDoneWhatWhenIfBlank(java.lang.Object object,String who,String functionality,String what,LocalDateTime when) {
			if(!(object instanceof AuditableWhoDoneWhatWhen))
				return;
			AuditableWhoDoneWhatWhen instance = (AuditableWhoDoneWhatWhen) object;
			//instance.set__auditWho__(ValueHelper.defaultToIfBlank(SessionHelper.getUserName(),DEFAULT_USER_NAME)); //TODO how to get principal in back end ?			
			//if(StringHelper.isBlank(instance.get__auditWho__())) {
				if(StringHelper.isBlank(who))
					who = DEFAULT_USER_NAME;
				instance.set__auditWho__(who);
			//}
			
			/*
			if(StringHelper.isBlank(instance.get__auditWhat__())) {
				if(StringHelper.isBlank(what))
					what = DEFAULT_ACTION;
				instance.set__auditWhat__(what);
			}
			*/
					
			instance.set__auditWhat__(what);
			//if(StringHelper.isBlank(instance.get__auditFunctionality__())) {
				if(StringHelper.isBlank(functionality))
					functionality = DEFAULT_FUNCTIONALITY;
				instance.set__auditFunctionality__(functionality);
			//}
			
			//if(instance.get__auditWhen__() == null) {
				if(when == null)
					when = LocalDateTime.now();
				instance.set__auditWhen__(when);
			//}
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