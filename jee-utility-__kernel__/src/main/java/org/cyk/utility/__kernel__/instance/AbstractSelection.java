package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractSelection<T,VALUE> extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<T> choiceClass;
	protected Properties properties;
	protected Collection<T> choices;
	protected String message;
	protected VALUE value;
	
	protected T cursor;
	
	public AbstractSelection(Class<T> choiceClass,Properties properties) {
		setChoiceClass(choiceClass);
		if(choiceClass != null) {
			setChoices(InstanceGetter.getInstance().get(choiceClass,properties));
			setMessage("Veuillez sélectionner "+InternationalizationHelper.buildString(InternationalizationHelper.buildKey(choiceClass)));
		}
	}
	
	public AbstractSelection(Class<T> choiceClass) {
		this(choiceClass,null);
	}
	
	public Collection<T> getChoices(Boolean injectIfNull) {
		if(choices == null && Boolean.TRUE.equals(injectIfNull))
			choices = new ArrayList<>();
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
}
