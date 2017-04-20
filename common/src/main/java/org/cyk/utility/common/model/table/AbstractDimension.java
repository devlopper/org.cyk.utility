package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

@NoArgsConstructor
public abstract class AbstractDimension<DATA,CELLTYPE extends AbstractCell<CELLVALUE>,CELLVALUE> extends AbstractBean implements Dimension<DATA, CELLTYPE,CELLVALUE>, Serializable {

	private static final long serialVersionUID = 3307695889353490821L;
 
	@Getter @Setter protected Long index,uiIndex;
	@Getter @Setter protected DATA data; 
	@Getter @Setter protected String title;
	//@Getter @Setter protected Boolean isSummary;
	@Getter @Setter protected DimensionType type = DimensionType.DETAIL;
	@Getter @Setter protected Boolean updatable,deletable,openable,countable;
	
	public AbstractDimension(DATA data,String title) {
		super(); 
		this.data = data;
		this.title = title;
	}
	
	public AbstractDimension(String title) {
		this(null,title);
	}
	
	public Boolean getIsDetail(){
		return DimensionType.DETAIL.equals(type);
	}
	
	@Override
	public String toString() {
		return title;
	}
	
	/**/
	
	public interface DimensionListener<DIMENSION,DATA,CELL extends Cell<VALUE>,VALUE> {

		/**
		 * Create a new instance
		 * @return
		 */
		DIMENSION create();
		void created(DIMENSION dimension);
		
		/**
		 * Add the instance to the list
		 * @param dimension
		 */
		void add(DIMENSION dimension);
		void added(DIMENSION dimension);
		
		/**
		 * Open for details
		 * @param dimension
		 */
		Boolean isOpenable(DIMENSION dimension);
		void open(DIMENSION dimension);
		void opened(DIMENSION dimension);
		
		Boolean isUpdatable(DIMENSION dimension);
		void update(DIMENSION dimension,DATA data);
		void updated(DIMENSION dimension);
		
		Boolean isDeletable(DIMENSION dimension);
		void remove(DIMENSION dimension);
		void removed(DIMENSION dimension);
		
		Boolean isCountable(DIMENSION dimension);
		
		Boolean equals(DATA data1,DATA data2);
		
		void editInitiated(DIMENSION dimension);
		void editApplied(DIMENSION dimension);
		void editCanceled(DIMENSION dimension);
		
		DIMENSION select(DATA data);
		
		Boolean countable(DIMENSION dimension);	
		
		/**/
		
		@Getter @Setter
		public static class Adapter<DIMENSION,DATA,CELL extends Cell<VALUE>,VALUE> extends BeanAdapter implements DimensionListener<DIMENSION,DATA,CELL,VALUE>,Serializable {

			private static final long serialVersionUID = 1L;
			protected Boolean openable,updatable,deletable,countable;
			
			@Override
			public DIMENSION create() {
				return null;
			}

			@Override
			public void created(DIMENSION dimension) {}

			@Override
			public void add(DIMENSION dimension) {}

			@Override
			public void added(DIMENSION dimension) {}

			@Override
			public void open(DIMENSION dimension) {}

			@Override
			public void opened(DIMENSION dimension) {}

			@Override
			public void update(DIMENSION dimension, DATA data) {}

			@Override
			public void updated(DIMENSION dimension) {}

			@Override
			public void remove(DIMENSION dimension) {}

			@Override
			public void removed(DIMENSION dimension) {}

			@Override
			public Boolean equals(DATA data1, DATA data2) {
				return null;
			}

			@Override
			public void editInitiated(DIMENSION dimension) {}

			@Override
			public void editApplied(DIMENSION dimension) {}

			@Override
			public void editCanceled(DIMENSION dimension) {}

			@Override
			public DIMENSION select(DATA data) {
				return null;
			}

			@Override
			public Boolean countable(DIMENSION dimension) {
				return null;
			}

			@Override
			public Boolean isOpenable(DIMENSION dimension) {
				return openable;
			}

			@Override
			public Boolean isUpdatable(DIMENSION dimension) {
				return updatable;
			}

			@Override
			public Boolean isDeletable(DIMENSION dimension) {
				return deletable;
			}

			@Override
			public Boolean isCountable(DIMENSION dimension) {
				return countable;
			}
			
			protected Boolean isFieldNameIn(Field field,String...names){
				return commonUtils.isFieldNameIn(field, names);
			}
			
			protected Boolean isFieldNameNotIn(Field field,String...names){
				return commonUtils.isFieldNameNotIn(field, names);
			}

		}

	}

}
