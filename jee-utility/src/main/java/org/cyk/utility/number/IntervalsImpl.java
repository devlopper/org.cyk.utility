package org.cyk.utility.number;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class IntervalsImpl extends AbstractCollectionInstanceImpl<Interval> implements Intervals,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Intervals add(Number lowValue, Boolean isLowValueExcluded, Number highValue, Boolean isHighValueExcluded) {
		Interval interval = __inject__(Interval.class);
		interval.getLow(Boolean.TRUE).setValue(lowValue).setIsExcluded(isLowValueExcluded);
		interval.getHigh(Boolean.TRUE).setValue(highValue).setIsExcluded(isHighValueExcluded);
		add(interval);
		return this;
	}

	@Override
	public Intervals add(Number lowValue, Number highValue) {
		add(lowValue, Boolean.FALSE, highValue, Boolean.FALSE);
		return this;
	}

	@Override
	public Boolean contains(Number number) {
		if(collection!=null)
			for(Interval index : collection)
				if(Boolean.TRUE.equals(index.contains(number)))
					return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
}
