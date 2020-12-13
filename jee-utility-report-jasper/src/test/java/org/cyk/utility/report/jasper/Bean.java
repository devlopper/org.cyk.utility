package org.cyk.utility.report.jasper;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Bean implements Serializable {

	private Integer index;
	private String string;
	private InputStream imageAsInputStream;
	
	public static Collection<Bean> buildCollection() {
		Collection<Bean> collection = new ArrayList<>();
		Bean bean = new Bean();
		bean.setImageAsInputStream(Bean.class.getResourceAsStream("armoirieci.jpg"));
		bean.setString("hello01");
		bean.setIndex(174);
		collection.add(bean);
		bean = new Bean();
		bean.setString("worldBB");
		bean.setIndex(255);
		collection.add(bean);
		bean = new Bean();
		bean.setString("worldBAAAA");
		bean.setIndex(25444);
		collection.add(bean);
		return collection;
	}
	
}
