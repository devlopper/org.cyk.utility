package org.cyk.utility.__kernel__.object;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class BuilderUnitTest extends AbstractWeldUnitTest {

	@Test
	public void build_notCachable() {
		MyObject o1 = Builder.build(MyObject.class);
		MyObject o2 = Builder.build(MyObject.class);
		assertThat(o1).isNotEqualTo(o2);
	}
	
	@Test
	public void build_cachable() {
		MyObject.ConfiguratorImpl configurator = new MyObject.ConfiguratorImpl();
		configurator.setIsCachable(Boolean.TRUE);
		MyObject o1 = Builder.build(MyObject.class,Map.of("identifier","1",Configurator.class,configurator));
		MyObject o2 = Builder.build(MyObject.class,Map.of("identifier","1",Configurator.class,configurator));
		assertThat(o1).isEqualTo(o2);
	}
	
	/**/
	
	public static class MyObject extends AbstractObject implements Serializable {
		
		public static class ConfiguratorImpl extends Configurator.AbstractImpl<MyObject> implements Serializable {
			@Override
			protected Class<MyObject> __getClass__() {
				return MyObject.class;
			}
		}

		static {
			Configurator.set(MyObject.class, new ConfiguratorImpl());
		}
		
	}

	
	
}
