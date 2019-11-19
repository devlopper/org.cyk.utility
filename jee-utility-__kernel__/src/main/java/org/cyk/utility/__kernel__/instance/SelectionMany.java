package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SelectionMany<T> extends AbstractSelection<T,Collection<T>> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SelectionMany(Class<T> choiceClass,Properties properties) {
		super(choiceClass,properties);
	}
	
	public SelectionMany(Class<T> choiceClass) {
		super(choiceClass);
	}
}
