package org.cyk.utility.common.model.table;

import lombok.Getter;
import lombok.Setter;

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
	public static class Adapter<DIMENSION,DATA,CELL extends Cell<VALUE>,VALUE> implements DimensionListener<DIMENSION,DATA,CELL,VALUE> {

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

	}

}
