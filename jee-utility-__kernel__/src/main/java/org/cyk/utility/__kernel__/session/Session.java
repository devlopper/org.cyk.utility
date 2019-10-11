package org.cyk.utility.__kernel__.session;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.security.User;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	
	public Session(Principal principal) {
		user = new User(principal);
	}
	
	public Session() {
		this(SecurityHelper.getPrincipal());
	}
	
	public User getUser(Boolean injectIfNull) {
		if(user == null && Boolean.TRUE.equals(injectIfNull))
			user = new User(null);
		return user;
	}
	
	public String getUserName() {
		if(user == null)
			return null;
		return user.getName();
	}
	
	public Object getUserUuid() {
		if(user == null)
			return null;
		return user.getUuid();
	}
}
