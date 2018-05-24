package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOne;
import org.cyk.utility.common.userinterface.output.OutputText;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class RemoveCommandComponentActionAdapter extends AddRemoveCommandActionAdapter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Row row;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void ____execute____(Class<?> actionOnClass, InputChoiceOne inputChoiceOne) {
		final LoggingHelper.Message.Builder vLoggingMessageBuilder = loggingMessageBuilder;//new LoggingHelper.Message.Builder.Adapter.Default();
		vLoggingMessageBuilder.addManyParameters("remove datatable row");
		
		if(row.getPropertiesMap().getValue()!=null){
			vLoggingMessageBuilder.addNamedParameters("value id",InstanceHelper.getInstance().getIdentifier(row.getPropertiesMap().getValue()));
			if(inputChoiceOne == null){
				
			}else{
				Object choice = getChoice(row.getPropertiesMap().getValue());
				if(choice==null){
					
				}else{
					vLoggingMessageBuilder.addNamedParameters("choice id",InstanceHelper.getInstance().getIdentifier(choice));
					inputChoiceOne.getChoices().addOne(choice);	
					CollectionHelper.Instance<?> collection = getDestinationCollection();
					if(collection!=null){
						vLoggingMessageBuilder.addManyParameters("destination collection");
						vLoggingMessageBuilder.addNamedParameters("count before remove",CollectionHelper.getInstance().getSize(collection.getElements()));
						collection.removeOne(row.getPropertiesMap().getValue());
						vLoggingMessageBuilder.addNamedParameters("count after remove",CollectionHelper.getInstance().getSize(collection.getElements()));
						if(dataTable.getPropertiesMap().getMaster()!=null){
							vLoggingMessageBuilder.addNamedParameters("master id",InstanceHelper.getInstance().getIdentifier(dataTable.getPropertiesMap().getMaster()));
							Object object = dataTable.getPropertiesMap().getMaster();
							InstanceHelper.getInstance().computeChanges(object);
							Collection<String> updatedFieldNames = (Collection<String>) ((Command)row.getPropertiesMap().getRemoveCommandComponent()).getPropertiesMap().getUpdatedFieldNames();
							vLoggingMessageBuilder.addNamedParameters("updated field names",updatedFieldNames);
							
							new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) updatedFieldNames){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String fieldName) {
									Object fieldObject = FieldHelper.getInstance().readBeforeLast(((FormDetail)dataTable.getPropertiesMap().getFormDetail()).getMaster().getObject(), fieldName);
									Control control = ((FormDetail)dataTable.getPropertiesMap().getFormDetail()).getControlByFieldName(fieldObject,FieldHelper.getInstance().getLast(fieldName));										
									//Control control = ((Detail)dataTable.getPropertiesMap().getFormDetail()).getControlByFieldName(fieldName);
									Object v1 = control instanceof Input ? ((Input<?>)control).getValue() : ((OutputText)control).getPropertiesMap().getValue();
									control.read();
									Object v2 = control instanceof Input ? ((Input<?>)control).getValue() : ((OutputText)control).getPropertiesMap().getValue();
									vLoggingMessageBuilder.addNamedParameters(fieldName,v1+">"+v2);
								}
							}.execute();
							
							Collection<String> updatedColumnsFieldNames = (Collection<String>) ((Command)row.getPropertiesMap().getRemoveCommandComponent()).getPropertiesMap().getUpdatedColumnFieldNames();
							vLoggingMessageBuilder.addNamedParameters("updated column field names",updatedColumnsFieldNames);
							new CollectionHelper.Iterator.Adapter.Default<String>(updatedColumnsFieldNames){
								private static final long serialVersionUID = 1L;

								protected void __executeForEach__(String columnFieldName) {
									dataTable.getColumn(columnFieldName).__setPropertyFooterPropertyValueBasedOnMaster__();
								}
							}.execute();
							
						}
					}
				}	
			}				
		}
		
		//all computing are done  we can now remove the row
		dataTable.removeOneRow(row);
	}		
}