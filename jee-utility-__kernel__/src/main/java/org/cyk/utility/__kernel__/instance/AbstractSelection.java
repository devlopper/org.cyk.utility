package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractSelection<T,VALUE> extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Properties properties;
	
	protected Class<T> choiceClass;
	@Accessors(chain = true) protected Boolean areChoicesGettable;
	@Accessors(chain = true) protected Boolean areChoicesHaveBeenGot;
	
	protected Collection<T> choices;
	@Accessors(chain = true) protected Listener<VALUE> listener;
	
	protected String message;
	
	/*  */
	protected T cursor;
	protected VALUE value;
	
	public AbstractSelection(Class<T> choiceClass,Properties properties) {
		setChoiceClass(choiceClass);
		if(choiceClass != null) {
			setMessage("Veuillez sélectionner "+InternationalizationHelper.buildString(InternationalizationHelper.buildKey(choiceClass)));	
		}
	}
	
	public AbstractSelection(Class<T> choiceClass) {
		this(choiceClass,null);
	}
	
	protected void __getChoices__() {
		if(areChoicesGettable!= null && Boolean.FALSE.equals(areChoicesGettable))
			return;
		if(Boolean.TRUE.equals(areChoicesHaveBeenGot))
			return;
		if(choiceClass != null) {
			setChoices(InstanceGetter.getInstance().get(choiceClass,properties));
		}
	}
	
	public Collection<T> getChoices() {
		__getChoices__();
		return choices;
	}
	
	public Collection<T> getChoices(Boolean injectIfNull) {
		Collection<T> choices = getChoices();
		if(choices == null && Boolean.TRUE.equals(injectIfNull))
			setChoices(choices = new ArrayList<>());
		return choices;
	}
	
	public AbstractSelection<T,VALUE> select(VALUE value) {
		if(value == null)
			return this;
		__select__(value);
		return this;
	}
	
	protected abstract void __select__(VALUE value);

	public AbstractSelection<T,VALUE> unselect(VALUE value) {
		if(value == null)
			return this;
		__unselect__(value);
		return this;
	}
	
	protected abstract void __unselect__(VALUE value);
	
	public void listenSelect() {
		__listenSelect__(value);
	}
	
	@SuppressWarnings("unchecked")
	protected void __listenSelect__(VALUE value) {
		if(listener == null)
			return;
		listener.select(value);
	}
	
	/**/
	
	public static interface Listener<VALUE> {
		
		default void processOnSelect(Collection<VALUE> values) {
			if(CollectionHelper.isEmpty(values))
				return;
			for(VALUE value : values)
				processOnSelect(value);
		}
		
		default void processOnSelect(VALUE value) {
			throw new RuntimeException("process on select "+value+" not yet implemented.");
		}
		
		default void select(Collection<VALUE> values) {
			if(CollectionHelper.isEmpty(values))
				return;
			processOnSelect(values);
		}
		
		default void select(@SuppressWarnings("unchecked") VALUE...values) {
			if(ArrayHelper.isEmpty(values))
				return;
			select(CollectionHelper.listOf(values));
		}
		
		default void unselect(Collection<VALUE> values) {
			if(CollectionHelper.isEmpty(values))
				return;
			processOnUnselect(values);
		}
		
		default void unselect(@SuppressWarnings("unchecked") VALUE...values) {
			if(ArrayHelper.isEmpty(values))
				return;
			unselect(CollectionHelper.listOf(values));
		}
		
		default void processOnUnselect(Collection<VALUE> values) {
			if(CollectionHelper.isEmpty(values))
				return;
			for(VALUE value : values)
				processOnUnselect(value);
		}
		
		default void processOnUnselect(VALUE value) {
			throw new RuntimeException("process on unselect "+value+" not yet implemented.");
		}
	}
}
