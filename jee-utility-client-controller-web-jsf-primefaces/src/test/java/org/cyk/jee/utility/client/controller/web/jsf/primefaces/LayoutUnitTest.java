package org.cyk.jee.utility.client.controller.web.jsf.primefaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class LayoutUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void buildTwoColumns_numberOfRowsAndNumberOfColumnsSet() {
		Layout layoutTwoColumns = Builder.build(Layout.class,Map.of("identifier","layout two columns"
				,"cells",CollectionHelper.listOf( new Cell(),new Cell())
				,Layout.FIELD_NUMBER_OF_ROWS,1
				,Layout.FIELD_NUMBER_OF_COLUMNS,2
				));
		assertThat(layoutTwoColumns.getNumberOfRows()).isEqualTo(1);
		assertThat(layoutTwoColumns.getNumberOfColumns()).isEqualTo(2);
	}
	
	@Test
	public void buildTwoColumns_ui_g_numberOfRowsAndNumberOfColumnsDeduced() {
		Layout layoutTwoColumns = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G
				,"cells",CollectionHelper.listOf(
					Cell.build(Map.of(Cell.FIELD_IDENTIFIER,"first name",Cell.FIELD_WIDTH,6))
					,Cell.build(Map.of(Cell.FIELD_IDENTIFIER,"last names",Cell.FIELD_WIDTH,6))
					)
				));
		assertThat(layoutTwoColumns.getNumberOfRows()).isEqualTo(1);
		assertThat(layoutTwoColumns.getNumberOfColumns()).isEqualTo(2);
		
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0)).isNotNull();
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0).getStyleClass()).isNotEmpty();
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0).getStyleClass()).contains("ui-g-6");
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),1).getStyleClass()).contains("ui-g-6");
	}
	
	@Test
	public void buildTwoColumns_flex_numberOfRowsAndNumberOfColumnsDeduced() {
		Layout layoutTwoColumns = Builder.build(Layout.class,Map.of(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(2),1,new Cell().setWidth(10))
				,Layout.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"first name")
					,MapHelper.instantiate(Cell.FIELD_IDENTIFIER,"last names")
					)
				));
		assertThat(layoutTwoColumns.getNumberOfRows()).isEqualTo(1);
		assertThat(layoutTwoColumns.getNumberOfColumns()).isEqualTo(2);
		
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0)).isNotNull();
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0).getStyleClass()).isNotEmpty();
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),0).getStyleClass()).contains("p-col-2");
		assertThat(CollectionHelper.getElementAt(layoutTwoColumns.getCells(),1).getStyleClass()).contains("p-col-10");
	}
}
