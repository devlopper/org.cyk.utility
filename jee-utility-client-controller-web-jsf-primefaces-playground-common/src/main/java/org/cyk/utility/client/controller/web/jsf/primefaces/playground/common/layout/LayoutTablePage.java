package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.layout;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class LayoutTablePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Layout Table";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("No Header,No Footer"), createLayoutBuilder(null,3,2,null,null,null))
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Header,No Footer"), createLayoutBuilder(Boolean.TRUE,3,2,null,null,null))
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("No Header,Footer"), createLayoutBuilder(null,3,2,Boolean.TRUE,null,null))
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Header,Footer"), createLayoutBuilder(Boolean.TRUE,3,2,Boolean.TRUE,null,null))
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("All"), createLayoutBuilder(Boolean.TRUE,3,2,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE))
		;
		return viewBuilder;
	}
	
	private LayoutBuilder createLayoutBuilder(Boolean hasHeader,Integer columnCount,Integer rowCount,Boolean hasFooter,Boolean hasOrderNumberColumn,Boolean hasCommandablesColumn) {
		LayoutBuilder layoutBuilder = __inject__(LayoutBuilder.class);
		LayoutTypeGrid table = __inject__(LayoutTypeGrid.class);
		table.setIsHasHeader(hasHeader).setColumnCount(columnCount).setRowCount(rowCount).setIsHasFooter(hasFooter).setIsHasOrderNumberColumn(hasOrderNumberColumn)
			.setIsHasCommandablesColumn(hasCommandablesColumn);
		layoutBuilder.setType(table);
		return layoutBuilder;
	}
	
}
