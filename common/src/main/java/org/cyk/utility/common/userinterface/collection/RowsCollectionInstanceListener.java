package org.cyk.utility.common.userinterface.collection;

import java.util.Collection;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.RemoteCommand;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class RowsCollectionInstanceListener extends CollectionHelper.Instance.Listener.Adapter<Row> {
	private static final long serialVersionUID = 1L;
	
	protected DataTable dataTable;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addOne(CollectionHelper.Instance<Row> instance, final Row row, Object source, Object sourceObject) {
		super.addOne(instance, row, source, sourceObject);
		Class<? extends RemoveCommandComponentActionAdapter> removeCommandComponentActionAdapterClass = InstanceHelper.getInstance()
				.getIfNotNullElseDefault((Class<? extends RemoveCommandComponentActionAdapter>)dataTable.getPropertiesMap().getRemoveCommandComponentActionAdapterClass()
				, RemoveCommandComponentActionAdapter.class);
		RemoveCommandComponentActionAdapter removeCommandComponentActionAdapter = ClassHelper.getInstance().instanciateOne(removeCommandComponentActionAdapterClass);
		removeCommandComponentActionAdapter.setDataTable(dataTable);
		removeCommandComponentActionAdapter.setRow(row);
		
		final Command command = new Command();
		command.setLabelFromIdentifier("userinterface.command.remove")._setLabelPropertyRendered(Boolean.FALSE);
		command.setAction(removeCommandComponentActionAdapter);
		command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.MINUS);
		command.getPropertiesMap().setInputValueIsNotRequired(Boolean.TRUE);
		command.getPropertiesMap().setImmediate(Boolean.TRUE);
		
		command.getPropertiesMap().copyFrom(((Command)dataTable.getPropertiesMap().getAddCommandComponent()).getPropertiesMap(), Properties.PROCESS,Properties.UPDATE);
		command.getPropertiesMap().copyFrom((Properties)dataTable.getPropertyRowProperties().getRemoveCommandProperties(), Properties.UPDATED_FIELD_NAMES,Properties.UPDATED_COLUMN_FIELD_NAMES);
		
		//command.getPropertiesMap().setProcess(((Command)dataTable.getPropertiesMap().getAddCommandComponent()).getPropertiesMap().getProcess());
		command.getPropertiesMap().setUpdate("@(form)");
		
		//command.usePropertyRemoteCommand();
		RemoteCommand.instanciateOne(command);
		
		row.getMenu().getPropertiesMap().setRendered(Boolean.FALSE);
		
		//row.getDeleteMenuNode().getPropertiesMap().setUrl(null);
		//RemoteCommand.instanciateOne(row.getDeleteMenuNode(),command.getAction(),command.getActionListener());
		
		new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) row.getPropertiesMap().getUpdatedFieldNames()){
			private static final long serialVersionUID = 1L;

			protected void __executeForEach__(String fieldName) {
				command.getPropertiesMap().addString(Properties.UPDATE,"@(."+dataTable.getForm().getControlByFieldName(fieldName).getPropertiesMap().getIdentifierAsStyleClass()+")");
			}
		}.execute();
		
		if(StringHelper.getInstance().isBlank((String)command.getPropertiesMap().getUpdate())){
			command.getPropertiesMap().setUpdate("@(form)");
		}
		
		row.getPropertiesMap().setRemoveCommandComponent(command);
		
		if(dataTable.getPropertiesMap().getChoiceValueClass()!=null){
			Object choice = FieldHelper.getInstance().read(row.getPropertiesMap().getValue(), ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
			if(choice!=null && Boolean.TRUE.equals( ((InputChoice<?>)dataTable.getPropertiesMap().getAddInputComponent()).getChoices().getIsSourceDisjoint() )){
				((InputChoice<?>)dataTable.getPropertiesMap().getAddInputComponent()).getChoices().removeOne(choice);
			}
		}
		
		if(dataTable.getPropertiesMap().getMasterFieldName()!=null){
			//Class<?> fieldType = FieldHelper.getInstance().getType(row.getPropertiesMap().getValue().getClass(), fieldName);
			if(!dataTable.getPropertiesMap().getMaster().getClass().equals(row.getPropertiesMap().getValue().getClass())){
				String fieldName = (String)dataTable.getPropertiesMap().getMasterFieldName();
				FieldHelper.getInstance().set(row.getPropertiesMap().getValue(), dataTable.getPropertiesMap().getMaster()
						, fieldName);//doing this allow to share same memory object	
			}
			
		}
	}

}