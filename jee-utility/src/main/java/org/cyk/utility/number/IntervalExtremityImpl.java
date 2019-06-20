package org.cyk.utility.number;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.throwable.ThrowableHelper;

@Dependent
public class IntervalExtremityImpl extends AbstractObject implements IntervalExtremity,Serializable {
	private static final long serialVersionUID = 1L;

	private Number value;
	private Boolean isExcluded;
	
	@Override
	public Number getValue() {
		return value;
	}

	@Override
	public IntervalExtremity setValue(Number value) {
		this.value = value;
		return this;
	}

	@Override
	public Boolean getIsExcluded() {
		return isExcluded;
	}

	@Override
	public IntervalExtremity setIsExcluded(Boolean isExcluded) {
		this.isExcluded = isExcluded;
		return this;
	}

	@Override
	public <T extends Number> T getPreparedValueAs(Class<T> aClass) {
		Number value = getValue();
		T result = null;
		if(value !=null ) {
			if(Integer.class.equals(aClass))
				result = (T) new Integer(value.intValue());
			else if(Double.class.equals(aClass))
				result = (T) new Double(value.doubleValue());
			else
				__inject__(ThrowableHelper.class).throwRuntimeException(getClass()+" : get prepared value as "+aClass+" not yet handled");
		}
		return result;
	}
	
	@Override
	public String toString() {
		return value == null ? ConstantString.INFINITE : value.toString();
	}
}
