package org.cyk.utility.common.model.table;

import java.util.Collection;

import org.cyk.utility.common.computation.DataReadConfiguration;

public class RowAdapter<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionAdapter<DIMENSION, DATA, CELL, VALUE> 
	implements RowListener<DIMENSION,DATA,CELL,VALUE> {

	@Override
	public Collection<DATA> load(DataReadConfiguration configuration) {
		return null;
	}

	@Override
	public Long count(DataReadConfiguration configuration) {
		return null;
	}


}
