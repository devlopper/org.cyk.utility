package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.computation.DataReadConfiguration;

public interface Row<DATA,CELL extends Cell<VALUE>, VALUE> extends Dimension<DATA, CELL, VALUE> {

	public interface Listener<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends AbstractDimension.DimensionListener<DIMENSION,DATA,CELL,VALUE> {

		Collection<DATA> load(DataReadConfiguration configuration);
		Long count(DataReadConfiguration configuration);
		
		/**/
		
		public static class Adapter<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends AbstractDimension.DimensionListener.Adapter<DIMENSION, DATA, CELL, VALUE> 
			implements Listener<DIMENSION,DATA,CELL,VALUE> , Serializable {
			
			public static Boolean READABLE = null;
			public static Boolean UPDATABLE = null;
			public static Boolean DELETABLE = null;
			
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<DATA> load(DataReadConfiguration configuration) {
				return null;
			}
		
			@Override
			public Long count(DataReadConfiguration configuration) {
				return null;
			}
			
			public static class Default<DIMENSION extends Row<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends Listener.Adapter<DIMENSION, DATA, CELL, VALUE> 
				implements Serializable {
				
				private static final long serialVersionUID = 1L;
			
			}
		
		}
	}
	
}
