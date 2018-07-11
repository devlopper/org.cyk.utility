package org.cyk.utility.number;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

@Singleton
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
	
	@Override
	public <NUMBER> NUMBER get(Class<NUMBER> aClass,Object object,NUMBER nullValue){
		if(object==null || ( (object instanceof String) && __inject__(StringHelper.class).isBlank((String)object) ) )
			return nullValue;
		return __inject__(ClassHelper.class).instanciate(aClass, new Object[]{String.class,object.toString()});
	}
	
	@Override
	public <NUMBER> NUMBER get(Class<NUMBER> aClass,Object object){
		return get(aClass,object,(NUMBER)null);
	}
	
	@Override
	public  Integer getInteger(Object object,Integer nullValue){
		return get(Integer.class, object, nullValue);
	}
	
	@Override
	public  Long getLong(Object object,Long nullValue){
		return get(Long.class, object, nullValue);
	}

	@Override
	public Long getLong(Object object) {
		return getLong(object, null);
	}

	
	@Override
	public BigDecimal getBigDecimal(Object object, BigDecimal nullValue) {
		return get(BigDecimal.class, object, nullValue);
	}

	@Override
	public BigDecimal getBigDecimal(Object object) {
		return getBigDecimal(object, null);
	}


}
