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
	public SelectionOne<T> setAreChoicesGettable(Boolean areChoicesGettable) {
		return (SelectionOne<T>) super.setAreChoicesGettable(areChoicesGettable);
	}
	
	@Override
	public SelectionOne<T> setListener(Listener<T> listener) {
		return (SelectionOne<T>) super.setListener(listener);
	}
	
	@Override
	protected void __select__(T value) {
		setValue(value);
	}

	@Override
	protected void __unselect__(T value) {
		// TODO Auto-generated method stub
		
	}
}
