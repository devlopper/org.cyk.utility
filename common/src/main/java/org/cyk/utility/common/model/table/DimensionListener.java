package org.cyk.utility.common.model.table;


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
	void open(DIMENSION dimension);
	void opened(DIMENSION dimension);
	
	void update(DIMENSION dimension,DATA data);
	void updated(DIMENSION dimension);
	
	void remove(DIMENSION dimension);
	void removed(DIMENSION dimension);
	
	Boolean equals(DATA data1,DATA data2);
	
	void editInitiated(DIMENSION dimension);
	void editApplied(DIMENSION dimension);
	void editCanceled(DIMENSION dimension);
	
	DIMENSION select(DATA data);
	
	Boolean countable(DIMENSION dimension);
	
}
