package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.data.structure.grid.Grid;
import org.cyk.utility.__kernel__.data.structure.grid.Grid.Row;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractDataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class DataTableDynamicPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		Grid grid = new Grid(CollectionHelper.listOf(
				new Amount().setAeYear(2020).setAeAmount(100l).setCp1Amount(50l).setCp2Amount(40l).setCp3Amount(10l)
				,new Amount().setAeYear(2021).setAeAmount(120l).setCp1Amount(70l).setCp2Amount(20l).setCp3Amount(30l)
				),"aeYear","aeAmount","cp1Amount","cp2Amount","cp3Amount");
		
		dataTable = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,Grid.Row.class,DataTable.FIELD_DATA_GRID,grid,DataTable.FIELD_EDITABLE,Boolean.TRUE
				,DataTable.FIELD_LISTENER,new AbstractDataTable.Listener.AbstractImpl() {
					
			@Override
			public Map<Object, Object> listenAddColumnGetArguments(AbstractDataTable dataTable,String fieldName) {
				Map<Object,Object> map = super.listenAddColumnGetArguments(dataTable, fieldName);
				if(map == null)
					map = new HashMap<>();
				if("aeYear".equals(fieldName)) {
					map.put(Column.FIELD_HEADER_TEXT, "A.E. Année");
					map.remove(Column.FIELD_REMOVE_COMMAND_BUTTON);
				}else if("aeAmount".equals(fieldName)) {
					map.put(Column.FIELD_HEADER_TEXT, "A.E. Montant");
					map.remove(Column.FIELD_REMOVE_COMMAND_BUTTON);
				}else if("cp1Amount".equals(fieldName)) {
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 1");
					map.remove(Column.FIELD_REMOVE_COMMAND_BUTTON);
				}else if("cp2Amount".equals(fieldName)) {
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 2");
					map.remove(Column.FIELD_REMOVE_COMMAND_BUTTON);
				}else if("cp3Amount".equals(fieldName)) {
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 3");
					map.remove(Column.FIELD_REMOVE_COMMAND_BUTTON);
				}else if("cp4Amount".equals(fieldName))
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 4");
				else if("cp5Amount".equals(fieldName))
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 5");
				else if("cp6Amount".equals(fieldName))
					map.put(Column.FIELD_HEADER_TEXT, "C.P. 6");
				return map;
			}
			
					@Override
					public Object listenSave(AbstractCollection collection) {
						AbstractDataTable dataTable = (AbstractDataTable) collection;
						MessageRenderer.getInstance().render(dataTable.getDataGrid().toString());
						dataTable.getDataGrid().writeValuesToObjects();
						MessageRenderer.getInstance().render(dataTable.getDataGrid().getRows().stream().map(row -> row.getObject()).collect(Collectors.toList()).toString());
						return null;
					}
				});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Dynamic Page";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Amount implements  Serializable {
		private Integer aeYear;
		private Long aeAmount;
		private Long cp1Amount;
		private Long cp2Amount;
		private Long cp3Amount;
		private Long cp4Amount;
		private Long cp5Amount;
		private Long cp6Amount;
	}
}