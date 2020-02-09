package org.cyk.utility.__kernel__.number;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.log.LogHelper;

public interface NumberHelper {

	/* get */
	
	@SuppressWarnings("unchecked")
	static <NUMBER> NUMBER get(Class<NUMBER> klass,Object object,NUMBER nullValue,Boolean isThrowException){
		if(klass == null || object == null)
			return nullValue;
		if((object instanceof String) && ((String)object).isBlank())
			return nullValue;
		try {
			if(Byte.class.equals(klass))
				return (NUMBER) Byte.valueOf(object.toString());
			if(Short.class.equals(klass))
				return (NUMBER) Short.valueOf(object.toString());
			if(Integer.class.equals(klass))
				if(object instanceof BigDecimal)
					return (NUMBER) Integer.valueOf(((BigDecimal)object).intValue());
				else
					return (NUMBER) Integer.valueOf(object.toString());
			if(Long.class.equals(klass))
				return (NUMBER) Long.valueOf(object.toString());
			if(Float.class.equals(klass))
				return (NUMBER) Float.valueOf(object.toString());
			if(Double.class.equals(klass))
				return (NUMBER) Double.valueOf(object.toString());
			if(BigDecimal.class.equals(klass))
				return (NUMBER) new BigDecimal(object.toString());
		} catch (NumberFormatException exception) {
			LogHelper.log(exception, NumberHelper.class);
			if(Boolean.TRUE.equals(isThrowException))
				throw exception;
			return nullValue;
		}
		throw new RuntimeException("creating number of type "+klass+" not yet handled");
	}
	
	static <NUMBER> NUMBER get(Class<NUMBER> klass,Object object,NUMBER nullValue){
		return get(klass,object,nullValue,Boolean.FALSE);
	}
	
	static <NUMBER> NUMBER get(Class<NUMBER> klass,Object object){
		if(klass == null || object == null)
			return null;
		if((object instanceof String) && ((String)object).isBlank())
			return null;
		return get(klass,object,(NUMBER)null);
	}
	
	static Integer getInteger(Object object,Integer nullValue){
		if(object == null)
			return nullValue;
		if((object instanceof String) && ((String)object).isBlank())
			return nullValue;
		return get(Integer.class, object, nullValue);
	}
	
	static Integer getInteger(Object object) {
		if(object == null)
			return null;
		if((object instanceof String) && ((String)object).isBlank())
			return null;
		return object instanceof Integer ? (Integer)object :  getInteger(object, null);
	}
	
	static Long getLong(Object object,Long nullValue){
		if(object == null)
			return nullValue;
		if((object instanceof String) && ((String)object).isBlank())
			return nullValue;
		return get(Long.class, object, nullValue);
	}

	static Long getLong(Object object) {
		if(object == null)
			return null;
		if((object instanceof String) && ((String)object).isBlank())
			return null;
		return getLong(object, null);
	}
	
	static Collection<Long> getLongs(Collection<?> collection,Long nullValue) {
		if(CollectionHelper.isEmpty(collection))
			return null;
		Collection<Long> longs = new ArrayList<>();
		for(Object index : collection) {
			Long value = getLong(index, nullValue);
			if(value == null)
				value = nullValue;
			longs.add(value);
		}			
		return longs;
	}
	
	static Collection<Long> getLongs(Collection<?> collection) {
		return getLongs(collection, null);
	}
	
	static BigDecimal getBigDecimal(Object object, BigDecimal nullValue) {
		if(object == null)
			return nullValue;
		if((object instanceof String) && ((String)object).isBlank())
			return nullValue;
		return get(BigDecimal.class, object, nullValue);
	}

	static BigDecimal getBigDecimal(Object object) {
		if(object == null)
			return null;
		if((object instanceof String) && ((String)object).isBlank())
			return null;
		return getBigDecimal(object, null);
	}
	
	/* compare */
	
	static Boolean compare(Number number1, Number number2, ComparisonOperator operator) {
		if(operator == null)
			return Boolean.FALSE;
		BigDecimal n1 = number1 == null ? null : new BigDecimal(number1.toString());
		BigDecimal n2 = number2 == null ? null : new BigDecimal(number2.toString());
		Boolean value = null;
		if(number1 == null)
			if(number2 == null)
				value = Boolean.TRUE;
			else
				value = Boolean.FALSE;
		else
			if(number2 == null)
				value = Boolean.FALSE;
			else{
				Integer comparison = n1.compareTo(n2);
				switch(operator){
				case EQ:value = comparison == 0;break;
				case NEQ:value = comparison != 0 ;break;
				case GT: value = comparison == 1;break;
				case GTE: value = comparison == 1 || comparison == 0;break;
				case LT: value = comparison == -1;break;
				case LTE: value = comparison == -1 || comparison == 0;break;
				}
			}
		return value;
	}
	
	static Boolean isEqualToZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return compare(number, 0, ComparisonOperator.EQ);
	}

	static Boolean isGreaterThanZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return compare(number, 0, ComparisonOperator.GT);
	}
	
	static Boolean isLessThanZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return compare(number, 0, ComparisonOperator.LT);
	}
	
	static Boolean isLessThanOrEqualZero(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return compare(number, 0, ComparisonOperator.LTE);
	}
	
	/* operate */
	
	static Number operate(Operation operation,Collection<Number> numbers){
		if(operation == null || CollectionHelper.isEmpty(numbers))
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
	
	static Number operate(Operation operation, Number... numbers) {
		if(operation == null || ArrayHelper.isEmpty(numbers))
			return null;
		return operate(operation, CollectionHelper.listOf(Boolean.TRUE, numbers));
	}
	
	static Number add(Collection<Number> numbers) {
		if(CollectionHelper.isEmpty(numbers))
			return null;
		return operate(Operation.ADD, numbers);
	}
	
	static Number add(Number... numbers) {
		if(ArrayHelper.isEmpty(numbers))
			return null;
		return operate(Operation.ADD, numbers);
	}
	
	static Number subtract(Collection<Number> numbers) {
		if(CollectionHelper.isEmpty(numbers))
			return null;
		return operate(Operation.SUBTRACT, numbers);
	}

	static Number subtract(Number... numbers) {
		if(ArrayHelper.isEmpty(numbers))
			return null;
		return operate(Operation.SUBTRACT, numbers);
	}
	
	static Number multiply(Collection<Number> numbers) {
		if(CollectionHelper.isEmpty(numbers))
			return null;
		return operate(Operation.MULTIPLY, numbers);
	}

	static Number multiply(Number... numbers) {
		if(ArrayHelper.isEmpty(numbers))
			return null;
		return operate(Operation.MULTIPLY, numbers);
	}
	
	static Number divide(Collection<Number> numbers) {
		if(CollectionHelper.isEmpty(numbers))
			return null;
		return operate(Operation.DIVIDE, numbers);
	}

	static Number divide(Number... numbers) {
		if(ArrayHelper.isEmpty(numbers))
			return null;
		return operate(Operation.DIVIDE, numbers);
	}
}
