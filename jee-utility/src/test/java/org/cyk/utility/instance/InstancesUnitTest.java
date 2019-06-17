package org.cyk.utility.instance;

import java.util.Arrays;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class InstancesUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void instances_addUsingCollection_MyClass01(){
		Instances instances = __inject__(Instances.class);
		assertionHelper.assertEquals(0, instances.getSize());
		instances.add(Arrays.asList(new MyClass01()));
		assertionHelper.assertEquals(1, instances.getSize());
		instances.add(Arrays.asList(new MyClass01(),new MyClass01()));
		assertionHelper.assertEquals(3, instances.getSize());
		instances.add(Arrays.asList(new MyClass01(),new MyClass01(),new MyClass01(),new MyClass01()));
		assertionHelper.assertEquals(7, instances.getSize());
	}
	
	@Test
	public void instances_addUsingArray_MyClass01(){
		Instances instances = __inject__(Instances.class);
		assertionHelper.assertEquals(0, instances.getSize());
		instances.add(new MyClass01());
		assertionHelper.assertEquals(1, instances.getSize());
		instances.add(new MyClass01(),new MyClass01());
		assertionHelper.assertEquals(3, instances.getSize());
		instances.add(new MyClass01(),new MyClass01(),new MyClass01(),new MyClass01());
		assertionHelper.assertEquals(7, instances.getSize());
	}
	
	/**/
	
	public static class MyClass01 {
		
	}
	
	public static class MyClass02 {
		
	}
}
