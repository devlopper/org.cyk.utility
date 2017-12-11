package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Comparator<T> implements java.util.Comparator<T>,Serializable {
	private static final long serialVersionUID = 1L;
	
	public static enum Order{ASCENDING,DESCENDING;public static Order DEFAULT = ASCENDING;}
	
	private Collection<String> fieldNames;
	private Order order = Order.DEFAULT;
	
	public Comparator(Order order,String...fieldNames) {
		this.order = order;
		this.fieldNames = CollectionHelper.getInstance().get(fieldNames);
	}
	
	public Comparator(String...fieldNames) {
		this(Order.DEFAULT,fieldNames);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compare(final T o1, final T o2) {
		Integer comparison = 0;
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames))
			for(String fieldName : fieldNames){
				Comparable v1 = (Comparable<?>) FieldHelper.getInstance().read(o1, fieldName);
				Comparable v2 = (Comparable<?>) FieldHelper.getInstance().read(o2, fieldName);
				if(v1==null)
					if(v2==null)
						comparison = 0;
					else
						comparison = -1;
				else
					if(v2==null)
						comparison = 1;
					else
						comparison = v1.compareTo(v2);
				
				if(comparison==0)
					;//try next field
				else
					break;//order has been found , no more check required
			}
		if(Order.DESCENDING.equals(order))
			comparison = -comparison;
		return comparison;
	}

}
