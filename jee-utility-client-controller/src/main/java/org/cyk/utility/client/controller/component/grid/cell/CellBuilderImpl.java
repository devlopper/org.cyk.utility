package org.cyk.utility.client.controller.component.grid.cell;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public class CellBuilderImpl extends AbstractVisibleComponentBuilderImpl<Cell> implements CellBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewBuilder view;
	private OutputStringTextBuilder text;
	
	@Override
	protected void __execute__(Cell cell) {
		super.__execute__(cell);
		ViewBuilder view = getView();
		
		if(view == null) {
			OutputStringTextBuilder text = getText();
			if(text!=null) {
				view = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
				view.getComponentsBuilder().addComponents(text);
			}
		}
		
		if(view!=null) {
			cell.setView(view.execute().getOutput());
		}
	}
	
	@Override
	public ViewBuilder getView() {
		return view;
	}
	
	@Override
	public ViewBuilder getView(Boolean injectIfNull) {
		if(view == null && Boolean.TRUE.equals(injectIfNull))
			view = __inject__(ViewBuilder.class);
		return view;
	}

	@Override
	public CellBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}


	@Override
	public OutputStringTextBuilder getText() {
		return text;
	}

	@Override
	public OutputStringTextBuilder getText(Boolean injectIfNull) {
		if(text == null && Boolean.TRUE.equals(injectIfNull))
			text = __inject__(OutputStringTextBuilder.class);
		return text;
	}

	@Override
	public CellBuilder setText(OutputStringTextBuilder text) {
		this.text = text;
		return this;
	}

	@Override
	public CellBuilder setTextValue(String textValue) {
		getText(Boolean.TRUE).setValue(textValue);
		return this;
	}
	
	
	/**/

}
