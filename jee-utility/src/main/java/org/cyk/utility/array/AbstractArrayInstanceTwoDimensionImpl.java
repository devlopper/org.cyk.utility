package org.cyk.utility.array;

import java.io.Serializable;
import java.lang.reflect.Array;

import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractArrayInstanceTwoDimensionImpl<T> extends AbstractArrayInstanceImpl<T> implements ArrayInstanceTwoDimension<T>,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer firstDimensionElementCount,secondDimensionElementCount;
	
	@Override
	public ArrayInstanceTwoDimension<T> set(Integer firstDimensionIndex, Integer secondDimensionIndex, T value) {
		T[][] array = (T[][]) getArray();
		if(array == null) {
			Class<?> elementClass = ValueHelper.returnOrThrowIfBlank("array element class", getElementClass());
			Integer firstDimensionElementCount = ValueHelper.returnOrThrowIfBlank("array first dimension element count", getFirstDimensionElementCount());
			Integer secondDimensionElementCount = ValueHelper.returnOrThrowIfBlank("array second dimension element count", getSecondDimensionElementCount());
			setArray(array = (T[][]) Array.newInstance(elementClass, firstDimensionElementCount,secondDimensionElementCount));
		}
		array[firstDimensionIndex][secondDimensionIndex] = value;
		return this;
	}
	
	@Override
	public T get(Integer firstDimensionIndex, Integer secondDimensionIndex) {
		T[][] array = (T[][]) getArray();
		return array == null ? null : array[firstDimensionIndex][secondDimensionIndex];
	}
	
	@Override
	public Integer getFirstDimensionElementCount() {
		return firstDimensionElementCount;
	}
	
	@Override
	public ArrayInstanceTwoDimension<T> setFirstDimensionElementCount(Integer firstDimensionElementCount) {
		this.firstDimensionElementCount = firstDimensionElementCount;
		return this;
	}
	
	@Override
	public Integer getSecondDimensionElementCount() {
		return secondDimensionElementCount;
	}
	
	@Override
	public ArrayInstanceTwoDimension<T> setSecondDimensionElementCount(Integer secondDimensionElementCount) {
		this.secondDimensionElementCount = secondDimensionElementCount;
		return this;
	}
	
}
