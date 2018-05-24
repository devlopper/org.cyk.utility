package org.cyk.utility.common.userinterface.collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOne;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class AddRemoveCommandActionAdapter extends Command.ActionAdapter {
	private static final long serialVersionUID = 1L;
	
	protected DataTable dataTable;
	
	@Override
	protected Object __execute__() {
		InputChoiceOne inputChoiceOne = (InputChoiceOne) dataTable.getPropertiesMap().getAddInputComponent();
		Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
		try {
			____execute____(actionOnClass, inputChoiceOne);
		} catch (Exception e) {
			System.out
					.println("DataTable.AddRemoveCommandActionAdapter.__execute__() ***********************************************************");
			e.printStackTrace();
		}
		return null;
	}
	
	protected void ____execute____(Class<?> actionOnClass,InputChoiceOne inputChoiceOne){
		
	}
	
	protected void setObjectSource(Object object,Object source){
		String fieldName = dataTable.getChoiceValueClassMasterFieldName();
		if(StringHelper.getInstance().isNotBlank(fieldName)){
			try {
				FieldHelper.getInstance().set(object, source,fieldName);
			} catch (Exception e) {
				//System.out.println("DataTable.AddRemoveCommandActionAdapter.setObjectSource() SET OBJECT SOURCE : field name = "+fieldName+" value = "+source);
				//debug(object);
				e.printStackTrace();
			}
		}
	}
	
	protected void listenObjectCreated(Object object,Object source){}
	
	protected CollectionHelper.Instance<?> getDestinationCollection(){
		return dataTable.getFormMasterObjectActionOnClassCollectionInstance();
	}

	protected Object getChoice(Object object){
		if(dataTable.getPropertiesMap().getChoiceValueClass()!=null)
			return FieldHelper.getInstance().read(object, ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
		return null;
	}
}