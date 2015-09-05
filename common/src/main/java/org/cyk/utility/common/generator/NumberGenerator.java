package org.cyk.utility.common.generator;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class NumberGenerator extends AbstractValueGenerator<Object>{

	private static final long serialVersionUID = -8165480008450605152L;

	private static final Random RANDOM = new Random();
	
	private Double min; 
	private Double max;
	
	public NumberGenerator() {}
	
	@Override
	public Integer generate() {
		return getIntValue(Integer.MAX_VALUE);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T generate(Class<T> clazz) {
		T value = null;
		//byte
		if(clazz.equals(byte.class) || clazz.equals(Byte.class))
			value =  (T) (Byte)((Integer)getIntValue(Byte.MAX_VALUE)).byteValue();
		//short
		else if(clazz.equals(short.class) || clazz.equals(Short.class))
			value =  (T) (Short)((Integer)getIntValue(Short.MAX_VALUE)).shortValue();
		//int
		else if(clazz.equals(int.class) || clazz.equals(Integer.class))
			value =  (T) (Integer)getIntValue(Integer.MAX_VALUE);
		//long
		else if(clazz.equals(long.class) || clazz.equals(Long.class))
			value =  (T) (Long)getLongValue();
		//float
		else if(clazz.equals(float.class) || clazz.equals(Float.class))
			value =  (T) (Float)getDoubleValue().floatValue();
		//double
		else if(clazz.equals(double.class) || clazz.equals(Double.class))
			value =  (T) (Double)getDoubleValue();
		else
			return null;
		return (T) value;
	}
	
	private int getIntValue(int limit){
		//System.out.println(limit);
		int value = max==null?RANDOM.nextInt(limit):RANDOM.nextInt(max.intValue());
		if(min!=null && value<min)
			value = min.intValue();
		return value;
	}
	
	private long getLongValue(){
		long value = RANDOM.nextLong();
		if(min!=null && value<min)
			value = min.longValue();
		if(max!=null && value>max)
			value = max.longValue();
		return value;
	}
	
	private Double getDoubleValue(){
		double value = RANDOM.nextInt(Byte.MAX_VALUE+1)/RANDOM.nextDouble();
		if(min!=null && value<min)
			value = min;
		if(max!=null && value>max)
			value = max;
		return value;
	}
	
	
	public static void main(String args[]){
		NumberGenerator numberGenerator = new NumberGenerator();
		System.out.println(numberGenerator.generate());
		numberGenerator.setMax(10D);
		numberGenerator.setMin(5D);
		System.out.println(numberGenerator.generate());
		
		System.out.println(numberGenerator.generate());
		
		/*System.out.println(numberGenerator.generate(Byte.class));
		System.out.println(numberGenerator.generate(int.class));
		System.out.println(numberGenerator.generate(Double.class));*/
	}

}
