package org.cyk.utility.__kernel__.array;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Dependent
public class ArrayTwoDimension<T> extends Array<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer firstDimensionElementCount,secondDimensionElementCount;
	
	@SuppressWarnings("unchecked")
	public ArrayTwoDimension<T> set(Integer firstDimensionIndex, Integer secondDimensionIndex, T value) {
		if(this.value == null) {
			Class<?> elementClass = ValueHelper.returnOrThrowIfBlank("array element class", getElementClass());
			Integer firstDimensionElementCount = ValueHelper.returnOrThrowIfBlank("array first dimension element count", getFirstDimensionElementCount());
			Integer secondDimensionElementCount = ValueHelper.returnOrThrowIfBlank("array second dimension element count", getSecondDimensionElementCount());
			this.value = java.lang.reflect.Array.newInstance(elementClass, firstDimensionElementCount,secondDimensionElementCount);
		}
		((T[][]) this.value)[firstDimensionIndex][secondDimensionIndex] = value;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public T get(Integer firstDimensionIndex, Integer secondDimensionIndex) {
		if(value == null)
			return null;
		return ((T[][]) value)[firstDimensionIndex][secondDimensionIndex];
	}
	
	public String toString(Integer columnWidth) {
		if(value == null)
			return super.toString();
		StringBuilder stringBuilder = new StringBuilder();
		for(Object[] array : (Object[][])value) {
			for(Object index : array) {
				stringBuilder.append(String.format("%1$"+columnWidth+"s |", index));	
			}
			stringBuilder.append("\r\n");
		}
		return stringBuilder.toString();
	}
	
	@Override
	public String toString() {
		return toString(10);
	}
	
}
