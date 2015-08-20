package org.cyk.utility.common.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@NoArgsConstructor
public abstract class AbstractDimension<DATA,CELLTYPE extends AbstractCell<CELLVALUE>,CELLVALUE> extends AbstractBean implements Dimension<DATA, CELLTYPE,CELLVALUE>, Serializable {

	private static final long serialVersionUID = 3307695889353490821L;
 
	@Getter @Setter protected Long index,uiIndex;
	@Getter @Setter protected DATA data;
	@Getter @Setter protected String title;
	//@Getter @Setter protected Boolean isSummary;
	@Getter @Setter protected DimensionType type;
	
	public AbstractDimension(DATA data,String title) {
		super(); 
		this.data = data;
		this.title = title;
	}
	
	public AbstractDimension(String title) {
		this(null,title);
	}
	
	public Boolean isDetail(){
		return DimensionType.DETAIL.equals(type);
	}
	
}
