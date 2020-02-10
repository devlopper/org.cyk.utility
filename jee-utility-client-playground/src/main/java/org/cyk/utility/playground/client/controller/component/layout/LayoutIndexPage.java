package org.cyk.utility.playground.client.controller.component.layout;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutIndexPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layoutOneColumn,layoutTwoColumns,layoutThreeColumns,layoutComplex;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Layout Index Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		layoutOneColumn = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,1
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(12))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"first_name0"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"last_names0")
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_0"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_1")
					)));
		
		layoutTwoColumns = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,2
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(9))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"first_name0a"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"last_names0a")
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_0a"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_1a")
					)));
		
		layoutThreeColumns = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,3
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(6),2,new Cell().setWidth(3))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"matricule0"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"first_name0b"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"last_names0b")
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_0b"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_1b"),MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_2b")
					)));
		
		layoutComplex = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"matricule0b",Cell.FIELD_WIDTH,12)
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"first_name0c",Cell.FIELD_WIDTH,6)
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"last_names0c",Cell.FIELD_WIDTH,6)
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_0c",Cell.FIELD_WIDTH,2)
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_1c",Cell.FIELD_WIDTH,6)
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"tel_2c",Cell.FIELD_WIDTH,4)
					)));
	}
	
}