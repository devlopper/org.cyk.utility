package org.cyk.jee.utility.client.controller.web.jsf.primefaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class CellUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void build_widthUnitIsUiG() {
		Cell cell = Builder.build(Cell.class,Map.of(Cell.FIELD_IDENTIFIER,"first name",Cell.FIELD_WIDTH,3,Cell.FIELD_WIDTH_UNIT,Cell.WidthUnit.UI_G));
		assertThat(cell.getWidth()).isEqualTo(3);
		assertThat(cell.getStyleClass()).contains("ui-g-3");
	}
	
	@Test
	public void build_widthUnitIsFlex() {
		Cell cell = Builder.build(Cell.class,Map.of(Cell.FIELD_IDENTIFIER,"first name",Cell.FIELD_WIDTH,3,Cell.FIELD_WIDTH_UNIT,Cell.WidthUnit.FLEX));
		assertThat(cell.getWidth()).isEqualTo(3);
		assertThat(cell.getStyleClass()).contains("p-col-3");
	}
}
