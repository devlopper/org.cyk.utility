package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
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
	protected Boolean nullChoicable,isReadChoicesElementsOnSetField=Boolean.TRUE;
	protected Collection<?> instances;
	protected Listener inputChoiceListener;
	/**/
	
	public InputChoice<T> setField(Object object, String fieldName,Collection<?> choicesElements) {
		__setField__(object, fieldName);
		choices.removeAll().addMany(choicesElements);
		return this;
	}
	
	@Override
	public InputChoice<T> __setField__(Object object, String fieldName) {
		return (InputChoice<T>) super.__setField__(object, fieldName);
	}
	
	@Override
	public InputChoice<T> setField(Field field) {
		super.setField(field);
		if(Boolean.TRUE.equals(getIsReadChoicesElementsOnSetField()))
			readChoicesElements();
		return this;
	}
	
	public InputChoice<T> readChoicesElements(){
		if(object!=null){
			choices.removeAll();
			Class<?> type = Input.getListener().computeChoiceInstanceClass(this);
			if(instances == null)
				instances = InstanceHelper.getInstance().get(type);
			//Collection<?> instances = InstanceHelper.getInstance().get(type);
			if(CollectionHelper.getInstance().isNotEmpty(instances)){
				choices.addMany(Boolean.TRUE.equals(getPropertiesMap().getSelectItemWrappable()) ? SelectItemHelper.getInstance().get(instances, nullChoicable) : instances);
			}
		}
		return this;
	}
	
	public Object getChoiceValue(Object choice){
		return choice;
	}
	
	public String getChoiceLabel(Object choice){
		if(inputChoiceListener == null)
			return InstanceHelper.getInstance().getLabel(choice);
		return inputChoiceListener.getChoiceLabel(choice);
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
	
	/**/
	
	public static interface Listener {
		
		String getChoiceLabel(Object value);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
		
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getChoiceLabel(Object value) {
					return InstanceHelper.getInstance().getLabel(value);
				}
				
			}
		
			@Override
			public String getChoiceLabel(Object value) {
				return null;
			}
		}
	}
}