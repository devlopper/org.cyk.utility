package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import lombok.Setter;
import lombok.Getter;

public abstract class AbstractColumn<DATA,CELL extends AbstractCell<VALUE>,VALUE> extends AbstractDimension<DATA,CELL,VALUE> implements Column<DATA,CELL,VALUE>,Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	//@Getter @Setter protected Object object;
	@Getter @Setter protected Field field;
	@Getter @Setter protected String footer;
	
	public AbstractColumn() {
		super();
	}

	public AbstractColumn(DATA data, String title) {
		super(data, title);
	}

	public AbstractColumn(String title) {
		super(title);
	}
	
	@Override
	public List<CELL> getCells() {
		return null;
	}

}
