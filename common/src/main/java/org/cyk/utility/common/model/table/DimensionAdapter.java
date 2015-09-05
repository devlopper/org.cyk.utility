package org.cyk.utility.common.model.table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DimensionAdapter<DIMENSION,DATA,CELL extends Cell<VALUE>,VALUE> implements DimensionListener<DIMENSION,DATA,CELL,VALUE> {

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
