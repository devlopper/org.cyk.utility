package org.cyk.utility.common.model.table;

import java.util.Collection;

import org.cyk.utility.common.computation.DataReadConfiguration;

public interface RowListener<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionListener<DIMENSION,DATA,CELL,VALUE> {

	Collection<DATA> load(DataReadConfiguration configuration);
	Long count(DataReadConfiguration configuration);
	
}
