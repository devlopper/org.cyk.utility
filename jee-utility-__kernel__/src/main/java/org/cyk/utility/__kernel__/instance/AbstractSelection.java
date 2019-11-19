package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
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
}
