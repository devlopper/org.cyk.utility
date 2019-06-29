package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class ChoicesLayoutResponsiveImpl extends AbstractChoicesLayoutImpl implements ChoicesLayoutResponsive,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer numberOfColumns;

	@Override
	public Integer getNumberOfColumns() {
		return numberOfColumns;
	}

	@Override
	public ChoicesLayoutResponsive setNumberOfColumns(Integer numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
		return this;
	}
	
}
