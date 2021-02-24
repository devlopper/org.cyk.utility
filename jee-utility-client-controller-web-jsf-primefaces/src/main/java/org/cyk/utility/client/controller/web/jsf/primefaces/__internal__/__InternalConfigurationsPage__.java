package org.cyk.utility.client.controller.web.jsf.primefaces.__internal__;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class __InternalConfigurationsPage__ extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	private Set<String> processedVariablesNames = new HashSet<>();
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		Collection<Map<Object,Object>> cellsMaps = new ArrayList<>();
		
		addInformationFromConfigurationProperty(cellsMaps, "Identifiant", VariableName.SYSTEM_IDENTIFIER);
		addInformationFromConfigurationProperty(cellsMaps, "Name", VariableName.SYSTEM_NAME);
		addInformationFromConfigurationProperty(cellsMaps, "Version", VariableName.SYSTEM_VERSION);
		addInformationFromConfigurationProperty(cellsMaps, "Timestamp", VariableName.SYSTEM_TIMESTAMP_AS_STRING);
		addInformationFromConfigurationProperty(cellsMaps, "Home url", VariableName.SYSTEM_WEB_HOME_URL);
		
		ConfigurationHelper.GOT.keySet().stream().filter(name -> !processedVariablesNames.contains(name)).forEach(name -> {
			addLabelValue(cellsMaps, name, StringHelper.get(ConfigurationHelper.GOT.get(name)));
		});
		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps);
	}
	
	public void addLabelValue(Collection<Map<Object,Object>> cellsMaps,String name,String value) {
		cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,OutputText.buildFromValue(name),Cell.FIELD_WIDTH,6));
		cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,OutputText.buildFromValue(ValueHelper.defaultToIfBlank(value,"--- NA ---")),Cell.FIELD_WIDTH,6));
	}
	
	public void addInformationFromConfigurationProperty(Collection<Map<Object,Object>> cellsMaps,String name,String configurationPropertyName) {
		addLabelValue(cellsMaps, name, ConfigurationHelper.getValueAsString(configurationPropertyName));
		processedVariablesNames.add(configurationPropertyName);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Configuration";
	}
}