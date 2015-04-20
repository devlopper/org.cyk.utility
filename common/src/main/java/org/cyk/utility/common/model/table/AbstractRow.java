package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public abstract class AbstractRow<DATA,CELL extends AbstractCell<VALUE>,VALUE> extends AbstractDimension<DATA,CELL,VALUE> implements Row<DATA,CELL,VALUE>,Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	@Getter protected List<CELL> cells = new ArrayList<>();
	
	public AbstractRow() {
		super();
	}

	public AbstractRow(DATA data, String title) {
		super(data, title);
	}

	public AbstractRow(String title) {
		super(title);
	}
	
	

}
