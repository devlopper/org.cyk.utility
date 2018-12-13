package org.cyk.utility.time;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class DurationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Duration> implements DurationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Long begin,end;
	private Long numberOfMillisecond;
	
	@Override
	protected Duration __execute__() throws Exception {
		Duration duration = __inject__(Duration.class);
		Long numberOfMillisecond = getNumberOfMillisecond();
		if(numberOfMillisecond == null) {
			Long begin = getBegin();
			Long end = getEnd();
			if(begin!=null && end!=null)
				numberOfMillisecond = end - begin;
		}
		if(numberOfMillisecond!=null) {
			duration.setNumberOfMillisecond((short) (numberOfMillisecond % TimeConstant.NUMBER_OF_MILLISECOND_PER_SECOND));
			numberOfMillisecond = numberOfMillisecond - duration.getNumberOfMillisecond();
			duration.setNumberOfSecond((byte) ((numberOfMillisecond / TimeConstant.NUMBER_OF_MILLISECOND_PER_SECOND) % TimeConstant.NUMBER_OF_SECOND_PER_MINUTE));
			numberOfMillisecond = numberOfMillisecond - duration.getNumberOfSecond() * TimeConstant.NUMBER_OF_MILLISECOND_PER_SECOND;
			duration.setNumberOfMinute((byte) ((numberOfMillisecond / TimeConstant.NUMBER_OF_MILLISECOND_PER_MINUTE) % TimeConstant.NUMBER_OF_MINUTE_PER_HOUR));
		}
		return duration;
	}

	@Override
	public Long getBegin() {
		return begin;
	}

	@Override
	public DurationBuilder setBegin(Long begin) {
		this.begin = begin;
		return this;
	}
	
	@Override
	public DurationBuilder setBeginToNow() {
		return setBegin(System.currentTimeMillis());
	}

	@Override
	public Long getEnd() {
		return end;
	}

	@Override
	public DurationBuilder setEnd(Long end) {
		this.end = end;
		return this;
	}
	
	@Override
	public DurationBuilder setEndNow() {
		return setEnd(System.currentTimeMillis());
	}
	
	@Override
	public Long getNumberOfMillisecond() {
		return numberOfMillisecond;
	}
	
	@Override
	public DurationBuilder setNumberOfMillisecond(Long numberOfMillisecond) {
		this.numberOfMillisecond = numberOfMillisecond;
		return this;
	}
	
}
