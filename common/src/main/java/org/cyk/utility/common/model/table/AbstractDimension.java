package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractDimension<DATA,CELLTYPE extends AbstractCell<CELLVALUE>,CELLVALUE> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3307695889353490821L;
 
	protected Byte index;
	protected DATA data;
	protected String title;
	protected List<CELLTYPE> cells = new ArrayList<>();
	
	public AbstractDimension(DATA data,String title) {
		super(); 
		this.data = data;
		this.title = title;
	}
	
	public AbstractDimension(String title) {
		this(null,title);
	}

	public boolean addCell(CELLTYPE cell) {
		return cells.add(cell);
	}
	
}
