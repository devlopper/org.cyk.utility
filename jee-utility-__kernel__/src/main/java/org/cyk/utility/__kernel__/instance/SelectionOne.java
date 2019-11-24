package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SelectionOne<T> extends AbstractSelection<T,T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public SelectionOne(Class<T> choiceClass,Properties properties) {
		super(choiceClass,properties);
	}
	
	public SelectionOne(Class<T> choiceClass) {
		super(choiceClass);
	}

	@Override
	protected void __select__(T value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void __unselect__(T value) {
		// TODO Auto-generated method stub
		
	}
}
