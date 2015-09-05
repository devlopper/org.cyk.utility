package org.cyk.utility.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class HamcrestUT extends AbstractTest {

	private static final long serialVersionUID = 3426638169905760812L;

	@Test
	public void simple(){
		MyClass object = new MyClass(4l, "R", new BigDecimal("5"),new SubClass(1, "D"));
		hasProperties(object, "identifier",4l,"name","R","value",new BigDecimal("5"));
	}
	
	@Test
	public void collection(){
		List<MyClass> collection = new ArrayList<>();
		collection.add(new MyClass(1l, "A", new BigDecimal("2"),new SubClass(1, "D")));
		collection.add(new MyClass(3l, "Z", new BigDecimal("0"),new SubClass(1, "D")));
		collection.add(new MyClass(2l, "E", new BigDecimal("1"),new SubClass(1, "D")));
		collection.add(new MyClass(4l, "R", new BigDecimal("5"),new SubClass(1, "D")));
		collection.add(new MyClass(5l, "T", new BigDecimal("0"),new SubClass(1, "D")));
		collection.add(new MyClass(7l, "Y", new BigDecimal("8"),new SubClass(1, "D")));
		
		contains(MyClass.class, collection, new Object[]{"identifier","name","value"}, new Object[][]{
			 {1l, "A", "2"}
			,{3l, "Z", "0"}
			,{2l, "E", "1"}
			,{4l, "R", "5"}
			,{5l, "T", "0"}
			,{7l, "Y", "8"}
		});
		
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @ToString
	public static class MyClass{
		private Long identifier;
		private String name;
		private BigDecimal value;
		
		private SubClass subClass;
	}
	
	@Getter @Setter @AllArgsConstructor @ToString
	public static class SubClass{
		private Integer identifier;
		private String name;
	}
}
