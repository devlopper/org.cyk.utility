package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputFile;
import org.cyk.utility.client.controller.component.input.InputFileBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.DataGetter;
import org.cyk.utility.client.controller.data.DataMethodsNamesGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.FormData;
import org.cyk.utility.client.controller.data.Row;

public abstract class AbstractWindowContainerManagedWindowBuilderEditDataImpl extends AbstractWindowContainerManagedWindowBuilderEditImpl implements WindowContainerManagedWindowBuilderEditData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		if(formClass!=null) {
			Form form = __inject__(formClass);
			if(window.getTitle()!=null)
				form.setTitle(window.getTitle().getValue());
			Data data = __getData__(window, systemAction, formClass, rowClass);
			
			if(form instanceof FormData<?>) {
				((FormData<Data>)form).setData(data);	
			}
						
			ViewBuilder viewBuilder = getView();
			if(viewBuilder == null) {
				viewBuilder = __inject__(ViewBuilder.class);
				setView(viewBuilder);
				
			}
			//viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);
			
			__execute__(form,systemAction,data,viewBuilder);
			
			Strings methodsNames = __inject__(DataMethodsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
			if(CollectionHelper.isNotEmpty(methodsNames)) {
				for(String index : methodsNames.get()) {
					//TODO we can write a DataCommandableBuilderGetter
					CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, index ,systemAction);
					/* TODO if it is update action then we need to know which field to process : we can write a getter for it */
					commandable.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setProperty(Properties.FIELDS, StringHelper.concatenate(__getPersistenceEntityFieldNames__(window, systemAction, formClass),","));
					
					Boolean isHasInputFile = CollectionHelper.isNotEmpty(viewBuilder.getComponentsBuilder(Boolean.TRUE).getComponents(Boolean.TRUE)
							.getIsInstanceOf(InputFileBuilder.class,InputFile.class));
					commandable.getCommand(Boolean.TRUE).setIsSynchronous(Boolean.TRUE.equals(isHasInputFile));
				}
			}
		}
	}
	
	protected Data __getData__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		return __inject__(DataGetter.class).setSystemAction(systemAction).execute().getOutput();
	}
	
	protected Collection<String> __getPersistenceEntityFieldNames__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass){
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		return CollectionHelper.isEmpty(fieldNames) ? null : fieldNames.get();
	}
	
	protected abstract void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder);
	
}
