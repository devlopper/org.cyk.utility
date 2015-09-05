package org.cyk.utility.common.generator;

import java.io.Serializable;
import java.util.Random;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.utility.common.CommonUtils;

public class PrimitiveGenerator extends AbstractValueGenerator<Object> implements Serializable{

	private static final long serialVersionUID = 6913469904258653785L;

	private static final Random RANDOM = new Random();
	
	private NumberGenerator numberGenerator = new NumberGenerator();
	private StringGenerator stringGenerator = new StringGenerator();
	
	@Override
	public Object generate() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T generate(Class<T> clazz) {
		logInfo("Starting Here");
		logDebug("Debug Details");
		if(CommonUtils.getInstance().isNumberClass(clazz))
			return numberGenerator.generate(clazz);
		else if(String.class.equals(clazz))
			return (T) stringGenerator.generate();
		else if(Boolean.class.equals(ClassUtils.primitiveToWrapper(clazz)))
			return (T)(Boolean)RANDOM.nextBoolean();
		else if(Character.class.equals(clazz) || char.class.equals(clazz))
			return (T) (Character)(char)RANDOM.nextInt(Character.MAX_VALUE+1);
		logError("{} is not a primitive",clazz.getName());
		return null;
	}
	
	public static void main(String[] args) {
		PrimitiveGenerator primitiveGenerator = new PrimitiveGenerator();
		System.out.println(primitiveGenerator.generate(Integer.class));
		System.out.println(primitiveGenerator.generate(Double.class));
		System.out.println(primitiveGenerator.generate(String.class));
		System.out.println(primitiveGenerator.generate(Boolean.class));
		System.out.println(primitiveGenerator.generate(PrimitiveGenerator.class));
	}

}
