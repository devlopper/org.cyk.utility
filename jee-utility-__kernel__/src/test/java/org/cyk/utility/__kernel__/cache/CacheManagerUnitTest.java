package org.cyk.utility.__kernel__.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CacheManagerUnitTest extends AbstractWeldUnitTest {

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		CacheManager.getInstance().clear();
	}
	
	@Test
	public void get() {
		assertThat(CacheManager.getInstance().get(MyObject.class, "1")).isNull();
	}
	
	@Test
	public void get_afterSet() {
		MyObject myObject01 = new MyObject();
		MyObject myObject02 = new MyObject();
		CacheManager.getInstance().set(MyObject.class, "1", myObject01);
		CacheManager.getInstance().set(MyObject.class, "2", myObject02);
		assertThat(CacheManager.getInstance().get(MyObject.class, "1")).isEqualTo(myObject01);
		assertThat(CacheManager.getInstance().get(MyObject.class, "2")).isEqualTo(myObject02);
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor
	public static class MyObject extends AbstractObject implements Serializable {
		
	}

	
	
}
