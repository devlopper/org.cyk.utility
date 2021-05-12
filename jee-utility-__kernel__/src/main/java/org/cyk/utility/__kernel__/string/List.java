package org.cyk.utility.__kernel__.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class List implements Serializable {

	@Getter private java.util.List<String> strings;
	@Getter @Setter @Accessors(chain=true) private String separator = ConstantEmpty.STRING;
	
	public String concatenate(String separator) {
		if(CollectionHelper.isEmpty(strings))
			return null;
		return StringHelper.concatenate(strings, separator);
	}
	
	public String concatenate() {
		return concatenate(separator);
	}
	
	/**/
	
	public java.util.List<String> getStrings(Boolean injectIfNull) {
		if(strings == null && Boolean.TRUE.equals(injectIfNull))
			strings = new ArrayList<>();
		return strings;
	}
	
	public List add(Collection<String> strings) {
		if(CollectionHelper.isEmpty(strings))
			return this;
		getStrings(Boolean.TRUE).addAll(strings);
		return this;
	}
	
	public List add(String...strings) {
		if(ArrayHelper.isEmpty(strings))
			return this;
		return add(CollectionHelper.listOf(strings));
	}
}