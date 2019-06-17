package org.cyk.utility.bean;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class PropertyUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void whenValueIsNull_whenDerivableIsNull_whenDerivedIsNull_resultIsNull() {
		assertionHelper.assertEquals(null,__inject__(Property.class).read());
	}
	
	@Test
	public void whenValueIs12_whenDerivableIsNull_whenDerivedIsNull_resultIsNull() {
		Property property = __inject__(Property.class);
		property.setValue(12);
		assertionHelper.assertEquals(null,__inject__(PropertyValueGetter.class).setProperty(property).execute().getOutput());
	}
	
	@Test
	public void whenValueIs12_whenDerivableIsTrue_whenDerivedIsNull_resultIsNull() {
		Property property = __inject__(Property.class);
		property.setIsDerivable(Boolean.TRUE).setValue(12);
		assertionHelper.assertEquals(12,__inject__(PropertyValueGetter.class).setProperty(property).execute().getOutput());
	}
	
	@Test
	public void whenC1_P01Is3_whenDerivableIsTrue_whenDerivedIsNull_resultIs3() {
		C1 c1 = __inject__(C1.class);
		Property property = __inject__(Property.class);
		property.setIsDerivable(Boolean.TRUE).setValue(3);
		c1.setP01(property);
		assertionHelper.assertEquals(3,__inject__(PropertyValueGetter.class).setProperty(property).execute().getOutput());
	}
	
	@Test
	public void whenC2_P01Is3_whenDerivableIsTrue_whenDerivedIsNull_resultIs6() {
		Property property = __inject__(Property.class);
		property.setParent(__inject__(C2.class));
		property.setIsDerivable(Boolean.TRUE).setValue(3);
		property.addValueGetterClassQualifiers(Default.class);
		assertionHelper.assertEquals(6,property.read());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class  C1 {
		
		Property p01;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class  C2 {
		
		Property p01;
		
	}
	
	@Default
	public static class PropertyValueGetterImpl extends AbstractPropertyValueGetterImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected Object __derive__(Property property) {
			if(property.getParent() instanceof C2)
				return (Integer)property.getValue() * 2;
			return super.__derive__(property);
		}
		
	}
}
