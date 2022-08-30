package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.cyk.utility.persistence.entity.audit.AuditedAction;

import lombok.Getter;

@Getter
public class AuditedActionImpl  implements AuditedAction,Serializable {

	private String who;
	private LocalDateTime when;
	
	@Override
	public AuditedAction setWho(String who) {
		this.who = who;
		return this;
	}
	
	@Override
	public AuditedAction setWhen(LocalDateTime when) {
		this.when = when;
		return this;
	}
	
	public static final String FIELD_WHO = "who";
	public static final String FIELD_WHEN = "when";
}