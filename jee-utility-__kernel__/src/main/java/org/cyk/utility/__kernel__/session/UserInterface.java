package org.cyk.utility.__kernel__.session;

import java.io.Serializable;

import org.cyk.utility.__kernel__.security.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class UserInterface implements Serializable {
	private static final long serialVersionUID = 1L;

	private Theme theme;
	
	public UserInterface(User user) {
		
	}
	
	public Theme getTheme(Boolean injectIfNull) {
		if(theme == null && Boolean.TRUE.equals(injectIfNull))
			theme = new Theme();
		return theme;
	}
	
	public Object getThemeColor() {
		if(theme == null)
			return null;
		return theme.getColor();
	}
}
