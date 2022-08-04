package org.cyk.utility.client.controller.web.jsf.primefaces.__internal__;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.persistence.MetricsRepresentation;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class __InternalSystemMetricsPage__ extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	private MetricsRepresentation metricsRepresentation;
	private Boolean enabled;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		metricsRepresentation = ProxyGetter.getInstance().get(MetricsRepresentation.class);
		enabled = Boolean.TRUE.equals(metricsRepresentation.getEnabledAsBoolean());		
		Map<String,String> map = metricsRepresentation.getAsMap();
		Collection<Map<Object,Object>> cellsMaps = new ArrayList<>();
		Collection<Data> datas = new ArrayList<>();
		if(MapHelper.isNotEmpty(map)) {
			new TreeMap<>(map).forEach((k,v) -> {
				datas.add(new Data().setLabel(k).setValue(v));
			});
			DataTable dataTable = DataTable.build(DataTable.FIELD_VALUE,datas,DataTable.FIELD_RENDER_TYPE,RenderType.OUTPUT_UNSELECTABLE
					,DataTable.ConfiguratorImpl.FIELD_COLUMNS_FIELDS_NAMES,List.of(Data.FIELD_LABEL,Data.FIELD_VALUE));
			dataTable.addHeaderToolbarLeftCommands(CommandButton.build(CommandButton.FIELD_VALUE, enabled ? "Désactiver" : "Activer"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
				@Override
				protected Object __runExecuteFunction__(AbstractAction action) {
					if(enabled)
						metricsRepresentation.disable();
					else
						metricsRepresentation.enable();
					Redirector.getInstance().redirect(OUTCOME, null);
					return null;
				}
			}));
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,dataTable,Cell.FIELD_WIDTH,12));
		}		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "System Metrics";
	}
	
	public static final String OUTCOME = "__internal__MetricsSystemView";
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Data {
		private String label,value;
		
		public static final String FIELD_LABEL = "label";
		public static final String FIELD_VALUE = "value";
	}
}