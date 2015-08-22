package org.cyk.utility.common.model.table;


public class RowAdapter<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionAdapter<DIMENSION, DATA, CELL, VALUE> 
	implements RowListener<DIMENSION,DATA,CELL,VALUE> {


}
