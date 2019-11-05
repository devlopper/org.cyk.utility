package org.cyk.utility.__kernel__.instance;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.instance.InstancePropertyValueBuilder.Property;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstancePropertyValueBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		InstanceGetterImpl.clear();
		ConfigurationHelper.clear();
	}
	
	@Test
	public void build_label_identifierIsNull_codeIsNull_nameIsNull_null(){
		assertThat(InstancePropertyValueBuilder.getInstance().build(new Klass(), Property.LABEL)).isEqualTo(null);
	}
	
	@Test
	public void build_label_identifierIs1_codeIsNull_nameIsNull_1(){
		assertThat(InstancePropertyValueBuilder.getInstance().build(new Klass().setIdentifier("1"), Property.LABEL)).isEqualTo("1");
	}
	
	@Test
	public void build_label_identifierIsNull_codeIs2_nameIsNull_2(){
		assertThat(InstancePropertyValueBuilder.getInstance().build(new Klass().setCode("2"), Property.LABEL)).isEqualTo("2");
	}
	
	@Test
	public void build_label_identifierIsNull_codeIsNull_nameIs3_3(){
		assertThat(InstancePropertyValueBuilder.getInstance().build(new Klass().setName("3"), Property.LABEL)).isEqualTo("3");
	}
	
	/**/
	
	/* Persistence */
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class Klass {		
		private String identifier;
		private String code;
		private String name;
	}
	
	
}
