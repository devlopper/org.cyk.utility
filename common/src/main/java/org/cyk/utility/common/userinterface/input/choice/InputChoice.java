package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.SelectItemHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.input.Input;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoice<T> extends Input<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected final CollectionHelper.Instance<Object> choices = new CollectionHelper.Instance<Object>();
	protected Boolean nullChoicable;
	
	/**/
	
	public InputChoice<T> setField(Object object, String fieldName,Collection<?> choicesElements) {
		setField(object, fieldName);
		choices.removeAll().addMany(choicesElements);
		return this;
	}
	
	@Override
	public InputChoice<T> setField(Object object, String fieldName) {
		return (InputChoice<T>) super.setField(object, fieldName);
	}
	
	@Override
	public InputChoice<T> setField(Field field) {
		super.setField(field);
		readChoicesElements();
		return this;
	}
	
	public InputChoice<T> readChoicesElements(){
		if(object!=null){
			choices.removeAll();
			Class<?> type = FieldHelper.getInstance().getType(object.getClass(), field);
			if(List.class.equals(type))
		        type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			Collection<?> instances = InstanceHelper.getInstance().get(type);
			if(CollectionHelper.getInstance().isNotEmpty(instances)){
				choices.addMany(Boolean.TRUE.equals(getPropertiesMap().getSelectItemWrappable()) ? SelectItemHelper.getInstance().get(instances, nullChoicable) : instances);
			}
		}
		return this;
	}
	
	public String getChoiceLabel(Object choice){
		return InstanceHelper.getInstance().getLabel(choice);
	}
	
	/*
	void addChoice(Object object);
	void removeChoice(Object object);
	k8
	void removeSelected();
	
	Boolean getIsAutomaticallyRemoveSelected();
	void setIsAutomaticallyRemoveSelected(Boolean value);
	
	
	void addChoiceIfAutomaticallyRemoveSelected(Object object);
	void removeSelectedAutomatically();
	*/
	public static interface BuilderBase<OUTPUT extends InputChoice<?>> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoice<?>> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoice<?>> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoice<?>> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoice<?>> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoice<?>>) ClassHelper.getInstance().getByName(InputChoice.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

	/**/
	
	public enum ChoiceSet{AUTO,YES_NO}
}