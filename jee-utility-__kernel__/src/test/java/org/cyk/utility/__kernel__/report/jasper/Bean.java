package org.cyk.utility.__kernel__.report.jasper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Bean implements Serializable {

	private Integer index;
	private String string;
	
	public static Collection<Bean> buildCollection() {
		Collection<Bean> collection = new ArrayList<>();
		Bean bean = new Bean();
		bean.setString("hello");
		bean.setIndex(1);
		collection.add(bean);
		bean = new Bean();
		bean.setString("world");
		bean.setIndex(2);
		collection.add(bean);
		return collection;
	}
	
}
