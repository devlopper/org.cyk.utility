package org.cyk.utility.number;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

@ApplicationScoped
public class NumberHelperImpl extends AbstractHelper implements NumberHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static NumberHelper INSTANCE;
	public static NumberHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(NumberHelper.class);
		return INSTANCE;
	}
	public static NumberHelper getInstance() {
		return getInstance(null);
	}
	
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
	
	@Override
	public Number operate(Operation operation, Number... numbers) {
		return operate(operation, List.of(numbers));
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
	public Boolean compare(Number number1, Number number2, ComparisonOperator operator) {
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
	
	/**/
	
	public static Boolean __isZero__(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return number.intValue() == 0 && number.floatValue() == 0 && number.doubleValue() == 0;
	}

	public static Boolean __isGreaterThanZero__(Number number) {
		if(number == null)
			return Boolean.FALSE;
		return number.intValue() > 0 || number.floatValue() > 0;
	}
	
	public static Number __operate__(Operation operation,Collection<Number> numbers){
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
	
	public static Number __operate__(Operation operation, Number... numbers) {
		return __operate__(operation, List.of(numbers));
	}
	
	public static Number __add__(Number... numbers) {
		return __operate__(Operation.ADD, numbers);
	}

	public static Number __subtract__(Number... numbers) {
		return __operate__(Operation.SUBTRACT, numbers);
	}
	
	public static <NUMBER> NUMBER __get__(Class<NUMBER> aClass,Object object,NUMBER nullValue){
		if(object==null || ( (object instanceof String) && __inject__(StringHelper.class).isBlank((String)object) ) )
			return nullValue;
		return org.cyk.utility.__kernel__.klass.ClassHelper.instanciate(aClass, new Object[]{String.class,object.toString()});
	}
	
	public static <NUMBER> NUMBER __get__(Class<NUMBER> aClass,Object object){
		return __get__(aClass,object,(NUMBER)null);
	}
	
	public static Integer __getInteger__(Object object,Integer nullValue){
		return __get__(Integer.class, object, nullValue);
	}
	
	public static Integer __getInteger__(Object object) {
		return __getInteger__(object, null);
	}
	
	public static Long __getLong__(Object object,Long nullValue){
		return __get__(Long.class, object, nullValue);
	}

	public static Long __getLong__(Object object) {
		return __getLong__(object, null);
	}

	
	public static BigDecimal __getBigDecimal__(Object object, BigDecimal nullValue) {
		return __get__(BigDecimal.class, object, nullValue);
	}

	public static BigDecimal __getBigDecimal__(Object object) {
		return __getBigDecimal__(object, null);
	}
	
	public static Boolean __compare__(Number number1, Number number2, ComparisonOperator operator) {
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
}
