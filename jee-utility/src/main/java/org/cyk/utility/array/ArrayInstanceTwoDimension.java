package org.cyk.utility.array;

public interface ArrayInstanceTwoDimension<T> extends ArrayInstance<T[][]> {

	ArrayInstanceTwoDimension<T> setFirstDimensionElementCount(Integer firstDimensionElementCount);
	Integer getFirstDimensionElementCount();
	
	ArrayInstanceTwoDimension<T> setSecondDimensionElementCount(Integer secondDimensionElementCount);
	Integer getSecondDimensionElementCount();
	
	ArrayInstanceTwoDimension<T> set(Integer firstDimensionIndex,Integer secondDimensionIndex,T value);
	T get(Integer firstDimensionIndex,Integer secondDimensionIndex);
}
