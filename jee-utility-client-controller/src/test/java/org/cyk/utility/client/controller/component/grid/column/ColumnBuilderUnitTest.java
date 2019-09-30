package org.cyk.utility.client.controller.component.grid.column;

import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ColumnBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_column_one_field_name() {
		ColumnBuilder builder = __inject__(ColumnBuilder.class);
		builder.addFieldNameStrings("person");
		Column column = builder.execute().getOutput();
		assertionHelper.assertNotNull(column);
		assertionHelper.assertEquals(1, CollectionHelper.getSize(column.getView(ViewMap.HEADER).getComponents()));
		OutputStringText title = (OutputStringText) column.getView(ViewMap.HEADER).getComponents().getAt(0);
		assertionHelper.assertNotNull(title);
		assertionHelper.assertEquals("Personne",title.getValue());
	}
	
	@Test
	public void get_column_two_field_names() {
		ColumnBuilder builder = __inject__(ColumnBuilder.class);
		builder.addFieldNameStrings("person","code");
		Column column = builder.execute().getOutput();
		assertionHelper.assertNotNull(column);
		assertionHelper.assertEquals(1, CollectionHelper.getSize(column.getView(ViewMap.HEADER).getComponents()));
		OutputStringText title = (OutputStringText) column.getView(ViewMap.HEADER).getComponents().getAt(0);
		assertionHelper.assertNotNull(title);
		assertionHelper.assertEquals("Code",title.getValue());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {
		
		private String identifier;
		private String code;
		private String name;
		
	}
}
