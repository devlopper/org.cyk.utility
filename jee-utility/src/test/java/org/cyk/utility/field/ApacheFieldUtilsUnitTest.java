package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class ApacheFieldUtilsUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getAllFieldOfMyClass01(){
		Collection<Field> fields = FieldUtils.getAllFieldsList(MyClass01.class);
		assertThat(fields).isNotEmpty().asList().hasSize(2).contains(FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE));
	}
	
	@Test
	public void getAllFieldOfMyClass01Sub01(){
		Collection<Field> fields = FieldUtils.getAllFieldsList(MyClass01Sub01.class);
		assertThat(fields).isNotEmpty().asList().hasSize(4).contains(FieldUtils.getField(MyClass01Sub01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01Sub01.class, "f01Sub01",Boolean.TRUE),FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE),FieldUtils.getField(MyClass01Sub01.class, "f01Sub02",Boolean.TRUE));
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createDeployment().addPackage(ApacheFieldUtilsUnitTest.class.getPackage());
	}
}
