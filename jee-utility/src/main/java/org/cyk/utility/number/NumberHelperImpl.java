package org.cyk.utility.number;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

public class NumberHelperImpl extends AbstractHelper implements NumberHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return number.intValue() == 0 && number.floatValue() == 0 && number.doubleValue() == 0;
	}

	@Override
	public Boolean isGreaterThanZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return number.intValue() > 0 || number.floatValue() > 0;
	}
	
	@Override
	public Number operate(Operation operation,Collection<Number> numbers){
		if(operation == null || __inject__(CollectionHelper.class).isEmpty(numbers))
			return null;
		Number result = null;
		switch(operation){
		case ADD:
			result = BigDecimal.ZERO;
			for(Number index : numbers)
				if(index != null)
					result = new BigDecimal(result.doubleValue()).add(new BigDecimal(index.doubleValue()));
			break;
		case SUBTRACT:
			for(Number index : numbers)
				if(index != null)
					if(result == null)
						result = index;
					else
						result = new BigDecimal(result.doubleValue()).subtract(new BigDecimal(index.doubleValue()));
			break;
		case MULTIPLY:
			result = BigDecimal.ONE;
			for(Number index : numbers)
				if(index != null)
					result = new BigDecimal(result.doubleValue()).multiply(new BigDecimal(index.doubleValue()));
			break;
		case DIVIDE:
			for(Number index : numbers)
				if(index != null)
					if(result == null)
						result = index;
					else
						result = new BigDecimal(result.doubleValue()).divide(new BigDecimal(index.doubleValue()));
			break;
		}
		return result;
	}
	
	@Override
	public Number operate(Operation operation, Number... numbers) {
		return operate(operation, __inject__(CollectionHelper.class).instanciate(numbers));
	}
	
	@Override
	public Number add(Number... numbers) {
		return operate(Operation.ADD, numbers);
	}

	@Override
	public Number subtract(Number... numbers) {
		return operate(Operation.SUBTRACT, numbers);
	}
	
}
