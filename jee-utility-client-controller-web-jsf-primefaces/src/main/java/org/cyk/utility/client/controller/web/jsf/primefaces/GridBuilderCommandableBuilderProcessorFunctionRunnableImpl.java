package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.AbstractGridBuilderCommandableBuilderProcessorFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.EntityListPage;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterNameStringBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueStringBuilder;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.primefaces.PrimeFaces;

public class GridBuilderCommandableBuilderProcessorFunctionRunnableImpl extends AbstractGridBuilderCommandableBuilderProcessorFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __runWithWindowRenderTypeDialog__(GridBuilder gridBuilder, CommandableBuilder commandableBuilder) {
		super.__runWithWindowRenderTypeDialog__(gridBuilder, commandableBuilder);
		SystemAction systemAction = commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction();
		if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionAdd) {
			commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnThrowableIsNull(Boolean.FALSE);
			SystemAction creationWindowSystemAction = commandableBuilder.getCommand().getWindowContainerManaged() instanceof EntityListPage ?
					__inject__(SystemActionCreate.class) : __inject__(SystemActionAdd.class);
			commandableBuilder./*setCommandFunctionActionClass(SystemActionCreate.class).*/addCommandFunctionTryRunRunnable(new Runnable() {
				@Override
				public void run() {
					Map<String,Object> options = new HashMap<String, Object>();
			        options.put("modal", true);
			        options.put("width", 840);
			        options.put("height", 440);
			        options.put("contentWidth", "100%");
			        options.put("contentHeight", "100%");
			        
			        UniformResourceIdentifierParameterNameStringBuilder uniformResourceIdentifierParameterNameStringBuilder = __inject__(UniformResourceIdentifierParameterNameStringBuilder.class);
			        UniformResourceIdentifierParameterValueStringBuilder uniformResourceIdentifierParameterValueStringBuilder = __inject__(UniformResourceIdentifierParameterValueStringBuilder.class);
			        Map<String,List<String>> parameters = new HashMap<String, List<String>>();
			        parameters.put(uniformResourceIdentifierParameterNameStringBuilder.setNameAsEntityClass().execute().getOutput()
			        		, (List<String>) __inject__(CollectionHelper.class).instanciate(gridBuilder.getRowDataClass().getSimpleName().toLowerCase()));
			        parameters.put(uniformResourceIdentifierParameterNameStringBuilder.setNameAsActionClass().execute().getOutput()
			        		, (List<String>) __inject__(CollectionHelper.class).instanciate(uniformResourceIdentifierParameterValueStringBuilder.setValue(creationWindowSystemAction).execute().getOutput()));
			        parameters.put(uniformResourceIdentifierParameterNameStringBuilder.setNameAsActionIdentifier().execute().getOutput()
			        		, (List<String>) __inject__(CollectionHelper.class).instanciate(uniformResourceIdentifierParameterValueStringBuilder.setValue(creationWindowSystemAction).execute().getOutput()));
			        parameters.put(uniformResourceIdentifierParameterNameStringBuilder.setNameAsWindowRenderTypeClass().execute().getOutput()
			        		, (List<String>) __inject__(CollectionHelper.class).instanciate(uniformResourceIdentifierParameterValueStringBuilder.setValue(WindowRenderTypeDialog.class).execute().getOutput()));
			        PrimeFaces.current().dialog().openDynamic("__entity__EditView", options, parameters);
				}
			});
			
			EventBuilder event = __inject__(EventBuilder.class);
			event.setOutputProperties(__inject__(Properties.class));
			event.getOutputProperties().setEvent("dialogReturn");
			event.getOutputProperties().setDisabled("false");
			event.getOutputProperties().setAsync("true");
			event.getOutputProperties().setGlobal("true");
			event.getOutputProperties().setPartialSubmit("true");
			event.getOutputProperties().setResetValues("true");
			event.getOutputProperties().setIgnoreAutoUpdate("true");
			event.getOutputProperties().setImmediate("true");
			event.getOutputProperties().setSkipChildren("true");
			event.getOutputProperties().setUpdate("@(."+gridBuilder.getOutputProperties().getIdentifierAsStyleClass()+")");
			CommandFunction function = __inject__(CommandFunction.class);
			function.try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {
				@Override
				public void run() {
					/*SelectEvent selectEvent = (SelectEvent) function.getProperties().getParameter();
					Data data = (Data) selectEvent.getObject();
					data.setIdentifier(100);
					DataTable dataTable = null;//(DataTable) gridBuilder.getComponent().getTargetModel();
					ListDataModel<Object> dataModel = (ListDataModel<Object>) dataTable.getValue();
					Collection<org.cyk.utility.client.controller.data.Row> collection = (Collection<org.cyk.utility.client.controller.data.Row>) dataModel.getWrappedData();
					collection.add(__inject__(org.cyk.utility.client.controller.data.RowBuilder.class).setGrid(gridBuilder).setData(data).execute().getOutput());
					*/
				}
			});
			event.getOutputProperties().setFunction(function);
			function.setAction(__inject__(SystemActionCreate.class));
			commandableBuilder.addEvents(event);	
		}
	}
	
}