package org.cyk.utility.common.userinterface.event;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.CommandHelper.Command;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOne;
import org.cyk.utility.common.userinterface.output.Output;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Event extends Component.Invisible implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.common.helper.CommandHelper.Command listener = new CommandHelper.Command.Adapter.Default.NoExecution();
	
	/**/

	public static Event instanciateOne(Component.Visible component,Object name,Object process,Object update,CommandHelper.Command listener){
		Event event = new Event();
		event.getPropertiesMap().setEvent(name);
		event.getPropertiesMap().setProcess(process);
		event.getPropertiesMap().setUpdate(update);
		if(listener!=null)
			event.setListener(listener);
		component.getPropertiesMap().setEvent(event);
		return event;
	}
	
	public static Event.Builder instanciateBuilder(Form.Detail formDetail,String fieldName,String[] updatedFieldNames,CommandHelper.Command listener,String[] processedFieldNames,String eventName){
		Event.Builder eventBuilder = new Event.Builder.Adapter.Default();
		eventBuilder.getPropertiesMap().setName(eventName);		
		eventBuilder.getPropertiesMap().setFormDetail(formDetail);
		eventBuilder.getPropertiesMap().setFieldName(fieldName);
		if(ArrayHelper.getInstance().isNotEmpty(processedFieldNames))
			eventBuilder.getPropertiesMap().setProcessedFieldNames(Arrays.asList(processedFieldNames));
		if(ArrayHelper.getInstance().isNotEmpty(updatedFieldNames))
			eventBuilder.getPropertiesMap().setUpdatedFieldNames(Arrays.asList(updatedFieldNames));
		eventBuilder.getPropertiesMap().setListener(listener);
		return eventBuilder;
	}
	
	public static Event instanciateOne(Form.Detail formDetail,String fieldName,String[] updatedFieldNames,CommandHelper.Command listener,String[] processedFieldNames,String eventName){
		Event.Builder builder = instanciateBuilder(formDetail, fieldName, updatedFieldNames, listener, processedFieldNames, eventName);
		Event event = builder.execute();
		return event;
	}
	
	public static Event instanciateOne(Form.Detail formDetail,String fieldName,String[] updatedFieldNames,CommandHelper.Command listener,String[] processedFieldNames){
		Control control = formDetail.getControlByFieldName(fieldName);
		String eventName;
		if(control instanceof InputChoiceOne)
			eventName = "change";
		else
			eventName = "blur";
		return instanciateOne(formDetail, fieldName, updatedFieldNames, listener,processedFieldNames,eventName);
	}
	
	public static Event instanciateOne(Form.Detail formDetail,String fieldName,String[] updatedFieldNames,CommandHelper.Command listener){
		return instanciateOne(formDetail,fieldName, updatedFieldNames, listener,(String[])null);
	}
	
	public static Event.Builder instanciateBuilder(DataTable.Cell cell,String[] updatedColumnFieldNames,CommandHelper.Command listener,String[] processedColumnFieldNames,String eventName){
		Event.Builder eventBuilder = new Event.Builder.Adapter.Default();
		eventBuilder.getPropertiesMap().setName(eventName);		
		eventBuilder.getPropertiesMap().setCell(cell);
		if(ArrayHelper.getInstance().isNotEmpty(processedColumnFieldNames))
			eventBuilder.getPropertiesMap().setProcessedColumnFieldNames(Arrays.asList(processedColumnFieldNames));
		if(ArrayHelper.getInstance().isNotEmpty(updatedColumnFieldNames))
			eventBuilder.getPropertiesMap().setUpdatedColumnFieldNames(Arrays.asList(updatedColumnFieldNames));
		eventBuilder.getPropertiesMap().setListener(listener);
		return eventBuilder;
	}
	
	/**/
	@Getter @Setter @Accessors(chain=true)
	public static class CommandAdapter extends CommandHelper.Command.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Event event;
		
		@Override
		protected Object __execute__() {
			____execute____();
			return null;
		}

		protected void ____execute____() {}
		
		protected Object getEventPropertyFormMasterObject(){
			return ((Form.Detail)event.getPropertiesMap().getFormDetail()).getMaster().getObject();
		}
		
		protected DataTable.Cell getCell(){
			return (DataTable.Cell)event.getPropertiesMap().getCell();
		}
		
		protected Object getEventPropertyCellRowValue(){
			return getCell().getRow().getPropertiesMap().getValue();
		}
		
		protected Object getCell(String columnFieldName){
			return getCell().getRow().getCell(columnFieldName);
		}
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Event> extends Component.Invisible.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Event> extends Component.Invisible.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Event> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Event> {

		public static class Adapter extends BuilderBase.Adapter.Default<Event> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Event.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default() {
					getPropertiesMap().setDisabled(Boolean.FALSE);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				protected Event __execute__() {
					final Event event = new Event();
					final Form.Detail detail = (Detail) getPropertiesMap().getFormDetail();
					final DataTable.Cell cell = (DataTable.Cell) getPropertiesMap().getCell();
					
					event.getPropertiesMap().copyFrom(getPropertiesMap(), Properties.DISABLED,Properties.NAME,Properties.PROCESS,Properties.UPDATE
							,Properties.UPDATED_FIELD_NAMES,Properties.FORM_DETAIL,Properties.UPDATED_COLUMN_FIELD_NAMES,Properties.CELL);
					
					Collection<String> processedFieldNames = CollectionHelper.getInstance().createList((Collection<String>) event.getPropertiesMap().getProcessedFieldNames());
					if(StringHelper.getInstance().isNotBlank((String)getPropertiesMap().getFieldName()))
						processedFieldNames = CollectionHelper.getInstance().add(processedFieldNames, (String)getPropertiesMap().getFieldName());
					event.getPropertiesMap().setProcessedFieldNames(processedFieldNames);
					
					new CollectionHelper.Iterator.Adapter.Default<String>(processedFieldNames){
						private static final long serialVersionUID = 1L;

						protected void __executeForEach__(String fieldName) {
							event.getPropertiesMap().addString(Properties.PROCESS,"@(."+detail.getControlByFieldName(fieldName).getPropertiesMap().getIdentifierAsStyleClass()+")");
						}
					}.execute();
					
					new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) event.getPropertiesMap().getUpdatedFieldNames()){
						private static final long serialVersionUID = 1L;

						protected void __executeForEach__(String fieldName) {
							event.getPropertiesMap().addString(Properties.UPDATE,"@(."+detail.getControlByFieldName(fieldName).getPropertiesMap().getIdentifierAsStyleClass()+")");
						}
					}.execute();
					
					Collection<String> processedColumnFieldNames = CollectionHelper.getInstance().createList((Collection<String>) event.getPropertiesMap().getProcessedColumnFieldNames());
					if(cell!=null)
						processedColumnFieldNames = CollectionHelper.getInstance().add(processedColumnFieldNames, (String)cell.getColumn().getPropertiesMap().getFieldName());
					event.getPropertiesMap().setProcessedColumnFieldNames(processedColumnFieldNames);
					
					if(StringHelper.getInstance().isBlank((String)event.getPropertiesMap().getUpdate())){
						event.getPropertiesMap().setUpdate("@(form)");
					}
					
					if(getPropertiesMap().getListener()!=null)
						event.setListener((Command) getPropertiesMap().getListener());
					
					if(getPropertiesMap().getListener() instanceof CommandAdapter)
						((CommandAdapter)event.getListener()).setEvent(event);
					event.getListener().addActionListener(new Action.ActionListener.Adapter(){
						private static final long serialVersionUID = 1L;
						
						@Override
						public void __executeBefore__(Action<?, ?> action) {
							super.__executeBefore__(action);
							new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) event.getPropertiesMap().getProcessedFieldNames()){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String fieldName) {
									detail.getInputByFieldName(fieldName).write();
								}
							}.execute();
							
							new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) event.getPropertiesMap().getProcessedColumnFieldNames()){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String columnFieldName) {
									cell.getRow().getCell(columnFieldName).getInput().write();
								}
							}.execute();
							
						}
						
						@Override
						public void processOnStatus(Action<?, ?> action) {
							super.processOnStatus(action);
							new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) event.getPropertiesMap().getUpdatedFieldNames()){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String fieldName) {
									detail.getControlByFieldName(fieldName).read();
								}
							}.execute();
							
							new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) event.getPropertiesMap().getUpdatedColumnFieldNames()){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String columnFieldName) {
									if(cell.getRow().getCell(columnFieldName).getInput()!=null)
										cell.getRow().getCell(columnFieldName).getInput().read();
									if(cell.getRow().getCell(columnFieldName).getPropertiesMap().getValue()!=null){
										((Output)cell.getRow().getCell(columnFieldName).getPropertiesMap().getValue()).read();
									}
								}
							}.execute();
							
						}
						
					});
					
					Component component = (Component) getPropertiesMap().getComponent();
					if(component == null){
						if(StringHelper.getInstance().isNotBlank((String)getPropertiesMap().getFieldName()))
							component = detail.getInputByFieldName((String)getPropertiesMap().getFieldName());
						if(component ==null && cell!=null){
							component = cell.getInput();
						}
					}
					
					if(component!=null)
						component.getPropertiesMap().setEvent(event);
					
					return event;
				}
				
			}
		}
	}
}