package org.cyk.utility.common.model.table;


public class DimensionAdapter<DIMENSION,DATA,CELL extends Cell<VALUE>,VALUE> implements DimensionListener<DIMENSION,DATA,CELL,VALUE> {

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

}
