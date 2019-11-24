package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SelectionMany<T> extends AbstractSelection<T,Collection<T>> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//protected T s;
	
	public SelectionMany(Class<T> choiceClass,Properties properties) {
		super(choiceClass,properties);
	}
	
	public SelectionMany(Class<T> choiceClass) {
		super(choiceClass);
	}
	
	public Collection<T> getValue(Boolean injectIfNull) {
		if(value == null && Boolean.TRUE.equals(injectIfNull))
			value = new ArrayList<>();
		return value;
	}
	
	@Override
	protected void __select__(Collection<T> value) {
		if(CollectionHelper.isEmpty(value))
			return;
		getValue(Boolean.TRUE).addAll(value);
		if(choices != null)
			choices.removeAll(value);
	}
	
	@SuppressWarnings("unchecked")
	public SelectionMany<T> select(T...array) {
		if(ArrayHelper.isEmpty(array))
			return this;
		select(CollectionHelper.listOf(array));	
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public SelectionMany<T> select() {
		if(cursor == null)
			return this;
		select(cursor);
		cursor = null;
		return this;
	}
	
	@Override
	protected void __unselect__(Collection<T> value) {
		if(value == null || CollectionHelper.isEmpty(value))
			return;
		if(this.value != null)
			this.value.removeAll(value);
		getChoices(Boolean.TRUE).addAll(value);
	}
	
	@SuppressWarnings("unchecked")
	public SelectionMany<T> unselect(T...array) {
		if(value == null || ArrayHelper.isEmpty(array))
			return this;
		unselect(CollectionHelper.listOf(array));	
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public SelectionMany<T> unselect() {
		if(cursor == null)
			return this;
		unselect(cursor);
		cursor = null;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public void unselectOne(T value) {
		if(value == null)
			return;
		unselect(value);
	}
}
