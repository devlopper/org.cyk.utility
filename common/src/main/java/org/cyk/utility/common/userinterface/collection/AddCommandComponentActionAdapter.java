package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOne;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class AddCommandComponentActionAdapter extends AddRemoveCommandActionAdapter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Class<?> actionOnClass, InputChoiceOne inputChoiceOne) {
		if(actionOnClass==null){
			
		}else{
			Object choice = inputChoiceOne == null ? null : inputChoiceOne.getValue();
			if(inputChoiceOne == null){
				
			}else{
				if(choice!=null){
					Object object = ClassHelper.getInstance().instanciateOne(actionOnClass);
					setObjectSource(object,choice);
					listenObjectCreated(object,choice);
					dataTable.addOneRow(object);
					CollectionHelper.Instance<?> collection = getDestinationCollection();
					if(collection!=null){
						collection.addOne(object);
					}
					//inputChoiceOne.getChoices().removeOne(choice);	
				}	
			}		
		}
	}
}