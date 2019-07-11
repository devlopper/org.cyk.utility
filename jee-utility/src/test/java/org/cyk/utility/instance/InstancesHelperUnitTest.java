package org.cyk.utility.instance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstancesHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildOne_MyClass01_to_MyClass02(){
		MyClass02 class02 = __inject__(InstanceHelper.class).buildOne(MyClass02.class, new MyClass01().setString01("abc").setInteger01(17).setLong01(1l));
		assertThat(class02.getString01()).isEqualTo("abc");
		assertThat(class02.getInteger01()).isEqualTo("17");
		assertThat(class02.getLong01()).isEqualTo("1");
	}
	
	@Test
	public void buildMany_1000_MyClass01_to_MyClass02(){
		Collection<MyClass01> class01s = new ArrayList<>();
		for(Integer index = 0 ; index < 1000 ; index = index + 1) {
			class01s.add(new MyClass01().setString01("string"+index).setInteger01(index).setLong01(index.longValue()));
		}
		Collection<MyClass02> class02s = __inject__(InstanceHelper.class).buildMany(MyClass02.class, class01s);
		
		Integer count = 0;
		for(MyClass02 index  : class02s) {
			assertThat(index.getString01()).isEqualTo("string"+count);
			assertThat(index.getInteger01()).isEqualTo(count.toString());
			assertThat(index.getLong01()).isEqualTo(count.toString());	
			count++;
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		private String string01;
		private Integer integer01;
		private Long long01;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass02 {
		private String string01;
		private String integer01;
		private String long01;
	}
}
