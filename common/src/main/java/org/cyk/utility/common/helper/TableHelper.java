package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class TableHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static TableHelper INSTANCE;
	
	public static TableHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new TableHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Getter @Setter
	public static class Table<T> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -3543754685060813767L;

		private CollectionHelper.Instance<T> collection;
		
		private CommandHelper.Command addCommand,deleteCommand;
		private Boolean isShowElementNameColumn = Boolean.TRUE;
		private String elementNameColumnName="THE N";
		//private Collection<Listener<T>> listeners;
		
		/**/
		
		public Table(Class<T> elementClass) {
			collection.setElementClass(elementClass);
			addCommand = CommandHelper.getInstance().getCommand();
			/*addCommand = new Command(){
				private static final long serialVersionUID = 1L;
				@Override
				protected Object __execute__() {
					add();
					return null;
				}
			};*/
			addCommand.setName("Add");
			
			deleteCommand = CommandHelper.getInstance().getCommand();
			/*deleteCommand = new Command(){
				private static final long serialVersionUID = 1L;
				@Override
				protected Object __execute__() {
					delete();
					return null;
				}
			};*/
			deleteCommand.setName("Delete");//.setIcon("ui-icon-trash");
		
		}
		
		protected void add(){
			collection.addOne();
		}
		
		protected void delete(){
			collection.removeItem(deleteCommand.getInput());
		}
		
		/*public InputCollection<T> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}*/
		
		/**/
		
		/*public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}*/
		
		/**/
		
		/*public static interface Listener<T> {
			
			public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
				private static final long serialVersionUID = 1L;

			}
			
			String METHOD_NAME_INSTANCIATE = "instanciate";
		}*/
	}
	
	public static interface Builder<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {
		
		public static class Adapter<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput.Adapter<OUTPUT> implements Serializable {

			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			/**/
			
			public static class Default<OUTPUT> extends Builder.Adapter<OUTPUT> implements Serializable {

				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}	
			}	
		}
	}
	
}
