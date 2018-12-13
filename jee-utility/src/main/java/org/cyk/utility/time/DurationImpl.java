package org.cyk.utility.time;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class DurationImpl extends AbstractObject implements Duration,Serializable {
	private static final long serialVersionUID = 1L;

	private Short numberOfMillisecond;
	private Byte numberOfSecond,numberOfMinute;
	
	@Override
	public Short getNumberOfMillisecond() {
		return numberOfMillisecond;
	}

	@Override
	public Duration setNumberOfMillisecond(Short numberOfMillisecond) {
		this.numberOfMillisecond = numberOfMillisecond;
		return this;
	}

	@Override
	public Byte getNumberOfSecond() {
		return numberOfSecond;
	}

	@Override
	public Duration setNumberOfSecond(Byte numberOfSecond) {
		this.numberOfSecond = numberOfSecond;
		return this;
	}

	@Override
	public Byte getNumberOfMinute() {
		return numberOfMinute;
	}

	@Override
	public Duration setNumberOfMinute(Byte numberOfMinute) {
		this.numberOfMinute = numberOfMinute;
		return this;
	}
	
	@Override
	public String toString() {
		return getNumberOfMinute()+"|"+getNumberOfSecond()+"|"+getNumberOfMillisecond();
	}

}
