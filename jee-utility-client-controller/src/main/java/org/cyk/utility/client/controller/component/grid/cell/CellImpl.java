package org.cyk.utility.client.controller.component.grid.cell;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.view.View;

public class CellImpl extends AbstractVisibleComponentImpl implements Cell,Serializable {
	private static final long serialVersionUID = 1L;

	private View view;
	
	@Override
	public View getView() {
		return view;
	}

	@Override
	public Cell setView(View view) {
		this.view = view;
		return this;
	}

}
